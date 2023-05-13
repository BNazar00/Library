package com.bn.library.controller;

import com.bn.library.dto.book.BookPreview;
import com.bn.library.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(params = {"email"})
    public void existsUserByEmail(@RequestParam("email") String email) {
        if (!userService.existsUserByEmail(email)) {
            throw new IllegalArgumentException("Email not found");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/books")
    public List<BookPreview> getLoggedInUserCurrentCheckoutBookPreviews() {
        return userService.getLoggedInUserCurrentCheckoutBookPreviews();
    }
}
