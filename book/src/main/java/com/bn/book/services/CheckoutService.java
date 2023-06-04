package com.bn.book.services;

import com.bn.clients.book.constant.CheckoutStatus;
import com.bn.book.dto.CheckoutCreateRequest;
import com.bn.book.dto.CheckoutDto;
import com.bn.clients.book.dto.CheckoutPreview;
import com.bn.book.dto.CheckoutUpdateRequest;
import java.util.List;

public interface CheckoutService {
    CheckoutDto getCheckoutDtoById(int id);

    List<CheckoutDto> getAllCheckoutsOrderByIdDesc();

    List<CheckoutPreview> getCheckoutPreviewsByUserId(int userId);

    List<CheckoutPreview> getCheckoutPreviewsByUserIdAndCheckoutStatuses(int userId,
                                                                         List<CheckoutStatus> checkoutStatuses);

    int addCheckout(CheckoutCreateRequest checkoutCreateRequest);

    void cancelCheckout(int checkoutId);

    void updateCheckout(CheckoutUpdateRequest request);
}
