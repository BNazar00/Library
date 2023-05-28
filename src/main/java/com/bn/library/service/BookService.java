package com.bn.library.service;

import com.bn.library.dto.book.BookCreateRequest;
import com.bn.library.dto.book.BookDto;
import com.bn.library.dto.book.BookPreview;
import com.bn.library.dto.book.BookUpdateRequest;
import java.util.List;

public interface BookService {
    List<BookPreview> getAllBookPreviewsOrderByCheckoutCount();

    List<BookDto> getAllBooks();

    List<BookPreview> getTop10BestsellersPreview();

    List<BookPreview> getTop10LatestArrivalsPreview();

    BookDto getBookDtoById(int id);

    void addBook(BookCreateRequest book);

    void updateBook(BookUpdateRequest request);
}
