package com.bn.library.controller;

import com.bn.library.dto.book.BookPreview;
import com.bn.library.dto.book.BookDto;
import com.bn.library.service.BookService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<BookPreview> getAllBooksPreview() {
        return bookService.getAllBookPreviews();
    }
}
