package com.bn.library.service.impl;

import com.bn.library.dto.book.BookCreationRequestDto;
import com.bn.library.dto.book.BookDto;
import com.bn.library.dto.book.BookPreview;
import com.bn.library.exception.NotExistException;
import com.bn.library.model.Author;
import com.bn.library.model.Book;
import com.bn.library.model.Publisher;
import com.bn.library.repository.BookRepository;
import com.bn.library.service.BookService;
import com.bn.library.util.converter.DtoConverter;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final DtoConverter dtoConverter;

    public BookServiceImpl(BookRepository bookRepository, DtoConverter dtoConverter) {
        this.bookRepository = bookRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public List<BookPreview> getAllBookPreviews() {
        return dtoConverter.convertToDtoList(bookRepository.findAll(), BookPreview.class);
    }

    @Override
    public BookDto getBookById(int id) {
        return dtoConverter.convertToDto(
                bookRepository.findById(id).orElseThrow(NotExistException::new),
                BookDto.class);
    }

    @Override
    public void addBook(BookCreationRequestDto book) {
        Book target = Book.builder()
                .title(book.getTitle())
                .author(new Author(book.getAuthorId()))
                .photoUrl(book.getPhotoUrl())
                .publisher(new Publisher(book.getPublisherId()))
                .publicationYear(book.getPublicationYear())
                .pageCount(book.getPageCount())
                .price(new BigDecimal(book.getPrice()))
                .build();
        bookRepository.save(target);
    }
}
