package com.bn.library.service.impl.book;

import com.bn.library.constant.CheckoutStatus;
import com.bn.library.constant.RoleData;
import com.bn.library.dto.book.BookDto;
import com.bn.library.dto.book.CheckoutRequest;
import com.bn.library.exception.InsufficientFundsException;
import com.bn.library.exception.NotExistException;
import com.bn.library.exception.UserPermissionException;
import com.bn.library.model.BookCopy;
import com.bn.library.model.BookRegister;
import com.bn.library.model.User;
import com.bn.library.repository.BookCopyRepository;
import com.bn.library.repository.BookRegisterRepository;
import com.bn.library.repository.UserRepository;
import com.bn.library.service.BookRegisterService;
import com.bn.library.service.BookService;
import com.bn.library.service.CheckoutService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final BookService bookService;
    private final BookRegisterService bookRegisterService;
    private final BookCopyRepository bookCopyRepository;
    private final BookRegisterRepository bookRegisterRepository;
    private final UserRepository userRepository;

    public CheckoutServiceImpl(BookService bookService, BookRegisterService bookRegisterService,
                               BookCopyRepository bookCopyRepository,
                               BookRegisterRepository bookRegisterRepository,
                               UserRepository userRepository) {
        this.bookService = bookService;
        this.bookRegisterService = bookRegisterService;
        this.bookCopyRepository = bookCopyRepository;
        this.bookRegisterRepository = bookRegisterRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public int checkout(CheckoutRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BookDto book = bookService.getBookById(request.getBookId());
        LocalDate issueDate = LocalDate.now();
        long daysDifference = ChronoUnit.DAYS.between(issueDate, request.getReturnDate()) + 1;

        if (book.getPrice().multiply(new BigDecimal(daysDifference)).compareTo(user.getBalance()) > 0) {
            throw new InsufficientFundsException("Insufficient funds to checkout the book.");
        }

        user.setBalance(user.getBalance().subtract(
                book.getPrice().multiply(new BigDecimal(daysDifference))));
        userRepository.save(user);

        BookCopy bookCopy = bookCopyRepository.findFirstByInStorageOrderById(true)
                .orElseThrow(() -> new NotExistException("There are no books in the storage"));
        bookCopy.setInStorage(false);
        bookCopyRepository.save(bookCopy);

        return bookRegisterRepository.saveAndFlush(BookRegister.builder()
                        .bookCopy(bookCopy)
                        .user(user)
                        .issueDate(issueDate)
                        .returnDate(request.getReturnDate())
                        .status(CheckoutStatus.WAITING)
                        .build())
                .getId();
    }

    @Override
    public void cancelCheckout(int checkoutId) {
        var userRoles = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
        BookRegister checkoutData = bookRegisterService.getBookRegisterById(checkoutId);

        if (CheckoutStatus.CANCELED.equals(checkoutData.getStatus())) {
            throw new NotExistException();
        } else if (!CheckoutStatus.WAITING.equals(checkoutData.getStatus())
                && userRoles.contains(new SimpleGrantedAuthority(RoleData.USER.getDBRoleName()))) {
            throw new UserPermissionException();
        }
        
        checkoutData.getBookCopy().setInStorage(true);
        checkoutData.setStatus(CheckoutStatus.CANCELED);
        bookRegisterRepository.save(checkoutData);
    }
}
