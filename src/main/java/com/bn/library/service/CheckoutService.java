package com.bn.library.service;

import com.bn.library.constant.CheckoutStatus;
import com.bn.library.dto.book.CheckoutDto;
import com.bn.library.dto.book.CheckoutPreview;
import com.bn.library.dto.book.CheckoutRequest;
import java.util.List;

public interface CheckoutService {
    CheckoutDto getCheckoutDtoById(int id);

    List<CheckoutPreview> getCheckoutPreviewsByUserId(int userId);

    int addCheckout(CheckoutRequest checkOutRequest);

    void cancelCheckout(int checkoutId);

    List<CheckoutPreview> getCheckoutPreviewsByUserIdAndCheckoutStatuses(int userId,
                                                                         List<CheckoutStatus> checkoutStatuses);
}
