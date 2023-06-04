package com.bn.library.service.impl;

import com.bn.clients.book.dto.CheckoutPreview;
import com.bn.library.model.User;
import com.bn.library.repository.UserRepository;
import com.bn.library.service.UserService;
import java.util.List;
import org.hibernate.NotYetImplementedFor6Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        //return checkoutService.getCheckoutPreviewsByUserIdAndCheckoutStatuses(userId, List.of(WAITING, IN_PROGRESS));
        //todo
        throw new NotYetImplementedFor6Exception();
    }

    @Override
    public List<CheckoutPreview> getLoggedInUserAllCheckoutPreviews() {
        int userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        //todo
        throw new NotYetImplementedFor6Exception();
        //return checkoutService.getCheckoutPreviewsByUserId(userId);
    }
}
