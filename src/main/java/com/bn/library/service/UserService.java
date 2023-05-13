package com.bn.library.service;

import com.bn.library.dto.book.BookPreview;
import com.bn.library.model.User;
import java.util.List;

public interface UserService {
    User getUserByEmail(String email);

    boolean existsUserByEmail(String email);

    List<BookPreview> getLoggedInUserCurrentCheckoutBookPreviews();
}
