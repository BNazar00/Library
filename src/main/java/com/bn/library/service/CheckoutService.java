package com.bn.library.service;

import com.bn.library.constant.CheckoutStatus;
import com.bn.library.dto.book.CheckoutCreateRequest;
import com.bn.library.dto.book.CheckoutDto;
import com.bn.library.dto.book.CheckoutPreview;
import com.bn.library.dto.book.CheckoutUpdateRequest;
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
