package com.bn.library.service;

import com.bn.library.dto.book.BookCreateRequest;
import com.bn.library.dto.book.BookDto;
import com.bn.library.dto.book.BookPreview;
import java.util.List;

public interface BookService {
    List<BookPreview> getAllBookPreviews();

    BookDto getBookById(int id);

    void addBook(BookCreateRequest book);
}
