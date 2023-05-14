package com.bn.library.service;

import com.bn.library.constant.CheckoutStatus;
import com.bn.library.dto.book.BookCreateRequest;
import com.bn.library.dto.book.BookDto;
import com.bn.library.dto.book.BookPreview;
import java.util.List;

public interface BookService {
    List<BookPreview> getAllBookPreviews();

    List<BookPreview> getTop10BestsellersPreview();

    List<BookPreview> getTop10LatestArrivalsPreview();

    BookDto getBookById(int id);

    void addBook(BookCreateRequest book);

    List<BookPreview> getUserBookPreviewsByUserIdAndCheckoutStatuses(int userId, List<CheckoutStatus> checkoutStatuses);
}
