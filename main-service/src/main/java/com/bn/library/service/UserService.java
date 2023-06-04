package com.bn.library.service;

import com.bn.clients.book.dto.CheckoutPreview;
import com.bn.library.model.User;
import java.util.List;

public interface UserService {
    User getUserByEmail(String email);

    boolean existsUserByEmail(String email);

    List<CheckoutPreview> getLoggedInUserCurrentCheckoutBookPreviews();

    List<CheckoutPreview> getLoggedInUserAllCheckoutPreviews();
}
