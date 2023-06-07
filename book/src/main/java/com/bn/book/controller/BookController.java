package com.bn.book.controller;

import com.bn.book.dto.BookCreateRequest;
import com.bn.book.dto.BookDto;
import com.bn.book.dto.BookUpdateRequest;
import com.bn.book.services.BookService;
import com.bn.book.util.annotation.AllowedRoles;
import com.bn.clients.book.dto.BookPreview;
import com.bn.clients.constant.RoleData;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public List<BookPreview> getAllBookPreviewsOrderByCheckoutCount() {
        return bookService.getAllBookPreviewsOrderByCheckoutCount();
    }

    @AllowedRoles(RoleData.ADMIN)
    @GetMapping("/admin/all")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/bestsellers")
    public List<BookPreview> getBestsellerPreviews() {
        return bookService.getTop10BestsellersPreview();
    }

    @GetMapping("/latest-arrivals")
    public List<BookPreview> getLatestArrivalPreviews() {
        return bookService.getTop10LatestArrivalsPreview();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable("id") int id) {
        return bookService.getBookDtoById(id);
    }

    @AllowedRoles(RoleData.ADMIN)
    @PostMapping
    public void addBook(@Valid @RequestBody BookCreateRequest book) {
        log.info("Add book request {}", book);
        bookService.addBook(book);
    }

    @AllowedRoles(RoleData.ADMIN)
    @PatchMapping
    public void updateBook(@Valid @RequestBody BookUpdateRequest request) {
        log.info("Book update request {}", request);
        bookService.updateBook(request);
    }
}
