package com.bn.book.services.impl;

import com.bn.book.dto.BookCreateRequest;
import com.bn.book.dto.BookDto;
import com.bn.book.dto.BookUpdateRequest;
import com.bn.book.model.Book;
import com.bn.book.model.BookCopy;
import com.bn.book.repository.BookCopyRepository;
import com.bn.book.repository.BookRepository;
import com.bn.book.services.BookService;
import com.bn.clients.book.dto.BookPreview;
import com.bn.clients.exception.NotExistException;
import com.bn.clients.util.converter.DtoConverter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final DtoConverter dtoConverter;

    public BookServiceImpl(BookRepository bookRepository, BookCopyRepository bookCopyRepository,
                           DtoConverter dtoConverter) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public BookDto getBookDtoById(int id) {
        Book entity = getBookById(id);
        BookDto bookDto = dtoConverter.convertToDto(entity, BookDto.class);
        bookDto.setAvailableCount(entity.getCopies().stream().filter(BookCopy::isInStorage).toList().size());
        return bookDto;
    }

    private Book getBookById(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotExistException("The book doesn't exist"));
    }

    @Override
    public List<BookPreview> getAllBookPreviewsOrderByCheckoutCount() {
        return dtoConverter.convertToDtoList(bookRepository.findAllBookPreviewsOrderByCheckoutCount(),
                BookPreview.class);
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> BookDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .photoUrl(book.getPhotoUrl())
                        .publicationYear(book.getPublicationYear())
                        .pageCount(book.getPageCount())
                        .availableCount(book.getCopies().stream().filter(BookCopy::isInStorage).toList().size())
                        .copiesCount(book.getCopies().size())
                        .price(book.getPrice())
                        .build())
                .toList();
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
    @Transactional
    public void addBook(BookCreateRequest book) {
        //todo auhorId and publisherId
        Book target = Book.builder()
                .title(book.getTitle())
                .photoUrl(book.getPhotoUrl())
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
    public void updateBook(BookUpdateRequest request) {
        Book book = getBookById(request.getId());
        log.info("Book update from {}", book);

        book.setPublicationYear(request.getPublicationYear());
        book.setPageCount(request.getPageCount());
        book.setPrice(request.getPrice());

        bookRepository.save(book);
    }
}
