package com.bn.book.services.impl;

import com.bn.book.dto.BookDto;
import com.bn.book.dto.CheckoutCreateRequest;
import com.bn.book.dto.CheckoutDto;
import com.bn.book.dto.CheckoutUpdateRequest;
import com.bn.book.model.BookCopy;
import com.bn.book.model.Checkout;
import com.bn.book.repository.BookCopyRepository;
import com.bn.book.repository.CheckoutRepository;
import com.bn.book.services.BookService;
import com.bn.book.services.CheckoutService;
import com.bn.clients.book.constant.CheckoutStatus;
import com.bn.clients.book.dto.CheckoutPreview;
import com.bn.clients.exception.NotExistException;
import com.bn.clients.util.converter.DtoConverter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.bn.clients.book.constant.CheckoutStatus.CANCELED;
import static com.bn.clients.book.constant.CheckoutStatus.DONE;
import static com.bn.clients.book.constant.CheckoutStatus.WAITING;

@Service
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {
    private final BookService bookService;
    private final BookCopyRepository bookCopyRepository;
    private final CheckoutRepository checkoutRepository;
    private final DtoConverter dtoConverter;

    public CheckoutServiceImpl(BookService bookService, BookCopyRepository bookCopyRepository,
                               CheckoutRepository checkoutRepository,
                               DtoConverter dtoConverter) {
        this.bookService = bookService;
        this.bookCopyRepository = bookCopyRepository;
        this.checkoutRepository = checkoutRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public CheckoutDto getCheckoutDtoById(int id) {
        return dtoConverter.convertToDto(getCheckoutById(id), CheckoutDto.class);
    }

    private Checkout getCheckoutById(int id) {
        return checkoutRepository.findById(id).orElseThrow(NotExistException::new);
    }

    @Override
    public List<CheckoutDto> getAllCheckoutsOrderByIdDesc() {
        return dtoConverter.convertToDtoList(checkoutRepository.findAll(Sort.by(Sort.Direction.DESC, "id")),
                CheckoutDto.class);
    }

    @Override
    public List<CheckoutPreview> getCheckoutPreviewsByUserId(int userId) {
        return dtoConverter.convertToDtoList(checkoutRepository.findAllByUserId(userId), CheckoutPreview.class);
    }

    @Override
    public List<CheckoutPreview> getCheckoutPreviewsByUserIdAndCheckoutStatuses(int userId,
                                                                                List<CheckoutStatus> checkoutStatuses) {
        return dtoConverter.convertToDtoList(
                checkoutRepository.findAllByUserIdAndStatusIn(userId, checkoutStatuses),
                CheckoutPreview.class);
    }

    @Override
    @Transactional
    public int addCheckout(CheckoutCreateRequest request) {
        //todo
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BookDto book = bookService.getBookDtoById(request.getBookId());
        LocalDate issueDate = LocalDate.now();
        long daysDifference = ChronoUnit.DAYS.between(issueDate, request.getReturnDate()) + 1;

        //if (book.getPrice().multiply(new BigDecimal(daysDifference)).compareTo(user.getBalance()) > 0) {
        //    throw new InsufficientFundsException("Insufficient funds to checkout the book.");
        //}
//todo
//        user.setBalance(user.getBalance().subtract(
//                book.getPrice().multiply(new BigDecimal(daysDifference))));
//        userRepository.save(user);

        BookCopy bookCopy = bookCopyRepository.findFirstByInStorageOrderById(true)
                .orElseThrow(() -> new NotExistException("There are no books in the storage"));
        bookCopy.setInStorage(false);
        bookCopyRepository.save(bookCopy);

        return checkoutRepository.saveAndFlush(Checkout.builder()
                        .bookCopy(bookCopy)
                        .issueDate(issueDate)
                        .returnDate(request.getReturnDate())
                        .status(WAITING)
                        .build())
                .getId();
    }

    @Override
    public void updateCheckout(CheckoutUpdateRequest request) {
        Checkout checkout = getCheckoutById(request.getId());
        log.info("Checkout update from {}", checkout);

        if (checkout.getStatus() == CANCELED || checkout.getStatus() == DONE
                || !updateCheckoutStatus(checkout, request.getStatus())) {
            log.error("Error updating checkout");
            throw new IllegalArgumentException("Illegal data changing");
        }

        checkout.setIssueDate(request.getIssueDate());
        checkout.setReturnDate(request.getReturnDate());

        checkoutRepository.save(checkout);
    }

    private boolean updateCheckoutStatus(Checkout checkout, CheckoutStatus targetStatus) {
        if (targetStatus == checkout.getStatus()) {
            return true;
        } else if (targetStatus.getWeight() - checkout.getStatus().getWeight() < 10) {
            return false;
        } else if (targetStatus == CANCELED || targetStatus == DONE) {
            checkout.getBookCopy().setInStorage(true);
        }
        checkout.setStatus(targetStatus);
        return true;
    }

    @Override
    public void cancelCheckout(int checkoutId) {
        //todo
        //var userRoles = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
        Checkout checkoutData = getCheckoutById(checkoutId);

        if (CANCELED == checkoutData.getStatus()) {
            throw new NotExistException();
        }
        //else if (WAITING != checkoutData.getStatus()
        //        && userRoles.contains(new SimpleGrantedAuthority(RoleData.USER.getDBRoleName()))) {
        //    throw new UserPermissionException();
        //}

        checkoutData.getBookCopy().setInStorage(true);
        checkoutData.setStatus(CANCELED);
        checkoutRepository.save(checkoutData);
    }
}
