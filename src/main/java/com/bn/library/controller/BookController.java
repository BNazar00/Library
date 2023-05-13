package com.bn.library.controller;

import com.bn.library.constant.RoleData;
import com.bn.library.dto.book.BookCheckOutRequest;
import com.bn.library.dto.book.BookCreateRequest;
import com.bn.library.dto.book.BookDto;
import com.bn.library.dto.book.BookPreview;
import com.bn.library.service.BookService;
import com.bn.library.util.annotation.AllowedRoles;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book")
@Slf4j
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public List<BookPreview> getAllBooksPreview() {
        return bookService.getAllBookPreviews();
    }

    @GetMapping("/bestsellers")
    public List<BookPreview> getBestsellersPreview() {
        return bookService.getTop10BestsellersPreview();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable("id") int id) {
        return bookService.getBookById(id);
    }

    @AllowedRoles(RoleData.ADMIN)
    @PostMapping
    public void addBook(@Valid @RequestBody BookCreateRequest book) {
        bookService.addBook(book);
    }

    @AllowedRoles(RoleData.READER)
    @PostMapping("/checkout")
    public void checkOut(@RequestBody BookCheckOutRequest request) {
        bookService.checkOut(request);
    }
}
