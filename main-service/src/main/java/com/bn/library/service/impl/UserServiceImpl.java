package com.bn.library.service.impl;

import static com.bn.library.constant.CheckoutStatus.IN_PROGRESS;
import static com.bn.library.constant.CheckoutStatus.WAITING;
import com.bn.library.dto.book.CheckoutPreview;
import com.bn.library.model.User;
import com.bn.library.repository.UserRepository;
import com.bn.library.service.CheckoutService;
import com.bn.library.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CheckoutService checkoutService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CheckoutService checkoutService) {
        this.userRepository = userRepository;
        this.checkoutService = checkoutService;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public List<CheckoutPreview> getLoggedInUserCurrentCheckoutBookPreviews() {
        int userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return checkoutService.getCheckoutPreviewsByUserIdAndCheckoutStatuses(userId, List.of(WAITING, IN_PROGRESS));
    }

    @Override
    public List<CheckoutPreview> getLoggedInUserAllCheckoutPreviews() {
        int userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return checkoutService.getCheckoutPreviewsByUserId(userId);
    }
}
