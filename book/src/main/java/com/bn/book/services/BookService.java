package com.bn.book.services;

import com.bn.book.dto.BookCreateRequest;
import com.bn.book.dto.BookDto;
import com.bn.book.dto.BookPreview;
import com.bn.book.dto.BookUpdateRequest;
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
