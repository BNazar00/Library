package com.bn.library.service.impl;

import com.bn.library.dto.book.BookDto;
import com.bn.library.dto.book.BookPreview;
import com.bn.library.exception.NotExistException;
import com.bn.library.repository.BookRepository;
import com.bn.library.service.BookService;
import com.bn.library.util.converter.DtoConverter;
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
}
