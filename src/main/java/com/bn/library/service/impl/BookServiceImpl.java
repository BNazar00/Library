package com.bn.library.service.impl;

import com.bn.library.dto.book.BookCheckoutRequest;
import com.bn.library.dto.book.BookCreateRequest;
import com.bn.library.dto.book.BookDto;
import com.bn.library.dto.book.BookPreview;
import com.bn.library.exception.InsufficientFundsException;
import com.bn.library.exception.NotExistException;
import com.bn.library.model.Author;
import com.bn.library.model.Book;
import com.bn.library.model.BookCopy;
import com.bn.library.model.BookRegister;
import com.bn.library.model.Publisher;
import com.bn.library.model.User;
import com.bn.library.repository.BookCopyRepository;
import com.bn.library.repository.BookRegisterRepository;
import com.bn.library.repository.BookRepository;
import com.bn.library.repository.UserRepository;
import com.bn.library.service.BookService;
import com.bn.library.util.converter.DtoConverter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BookRegisterRepository bookRegisterRepository;
    private final UserRepository userRepository;
    private final DtoConverter dtoConverter;

    public BookServiceImpl(BookRepository bookRepository, BookCopyRepository bookCopyRepository,
                           BookRegisterRepository bookRegisterRepository, UserRepository userRepository,
                           DtoConverter dtoConverter) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.bookRegisterRepository = bookRegisterRepository;
        this.userRepository = userRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public List<BookPreview> getAllBookPreviews() {
        return dtoConverter.convertToDtoList(bookRepository.findAll(), BookPreview.class);
    }

    @Override
    public List<BookPreview> getTop10BestsellersPreview() {
        return dtoConverter.convertToDtoList(bookRepository.findTop10Bestsellers(), BookPreview.class);
    }

    @Override
    public List<BookPreview> getTop10LatestArrivalsPreview() {
        return dtoConverter.convertToDtoList(bookRepository.findTop10ByOrderByIdDesc(), BookPreview.class);
    }

    @Override
    public BookDto getBookById(int id) {
        Book entity = bookRepository.findById(id).orElseThrow(NotExistException::new);
        BookDto bookDto = dtoConverter.convertToDto(entity, BookDto.class);
        bookDto.setAvailableCount(entity.getCopies().size());
        return bookDto;
    }

    @Override
    @Transactional
    public void addBook(BookCreateRequest book) {
        Book target = Book.builder()
                .title(book.getTitle())
                .author(new Author(book.getAuthorId()))
                .photoUrl(book.getPhotoUrl())
                .publisher(new Publisher(book.getPublisherId()))
                .publicationYear(book.getPublicationYear())
                .pageCount(book.getPageCount())
                .price(new BigDecimal(book.getPrice()))
                .build();
        bookRepository.saveAndFlush(target);

        List<BookCopy> copies = new ArrayList<>();
        for (int i = 0; i < book.getNumberOfCopies(); i++) {
            copies.add(BookCopy.builder().inStorage(true).book(target).build());
        }
        bookCopyRepository.saveAll(copies);
    }

    @Override
    @Transactional
    public int checkout(BookCheckoutRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BookDto book = getBookById(request.getBookId());
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
                        .build())
                .getId();
    }
}
