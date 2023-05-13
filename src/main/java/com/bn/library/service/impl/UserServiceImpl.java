package com.bn.library.service.impl;

import com.bn.library.dto.book.BookPreview;
import com.bn.library.model.User;
import com.bn.library.repository.UserRepository;
import com.bn.library.service.BookService;
import com.bn.library.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import static com.bn.library.constant.CheckoutStatus.IN_PROGRESS;
import static com.bn.library.constant.CheckoutStatus.WAITING;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookService bookService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BookService bookService) {
        this.userRepository = userRepository;
        this.bookService = bookService;
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
    public List<BookPreview> getLoggedInUserCurrentCheckoutBookPreviews() {
        int userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return bookService.getUserBookPreviewsByUserIdAndCheckoutStatuses(userId, List.of(WAITING, IN_PROGRESS));
    }
}
