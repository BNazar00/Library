package com.bn.library.service;

import com.bn.library.dto.book.CheckoutRequest;

public interface CheckoutService {
    int checkout(CheckoutRequest checkOutRequest);

    void cancelCheckout(int checkoutId);
}
