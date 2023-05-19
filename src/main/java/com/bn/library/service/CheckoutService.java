package com.bn.library.service;

import com.bn.library.constant.CheckoutStatus;
import com.bn.library.dto.book.CheckoutPreview;
import com.bn.library.dto.book.CheckoutRequest;
import com.bn.library.model.Checkout;
import java.util.List;

public interface CheckoutService {
    Checkout getCheckoutById(int id);

    List<CheckoutPreview> getCheckoutPreviewsByUserId(int userId);

    int checkout(CheckoutRequest checkOutRequest);

    void cancelCheckout(int checkoutId);

    List<CheckoutPreview> getCheckoutPreviewsByUserIdAndCheckoutStatuses(int userId,
                                                                         List<CheckoutStatus> checkoutStatuses);
}
