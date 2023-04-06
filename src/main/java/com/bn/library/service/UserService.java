package com.bn.library.service;

import com.bn.library.model.User;

public interface UserService {
    User getUserByEmail(String email);

    boolean existsUserByEmail(String email);
}
