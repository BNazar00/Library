package com.bn.library.controller;

import com.bn.library.constant.RoleData;
import com.bn.library.dto.book.CheckoutPreview;
import com.bn.library.service.UserService;
import com.bn.library.util.annotation.AllowedRoles;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @AllowedRoles(RoleData.READER)
    @GetMapping("/checkout/current")
    public List<CheckoutPreview> getLoggedInUserCurrentCheckoutBookPreviews() {
        return userService.getLoggedInUserCurrentCheckoutBookPreviews();
    }

    @AllowedRoles(RoleData.READER)
    @GetMapping("/checkout/all")
    public List<CheckoutPreview> getLoggedInUserAllCheckoutBookPreviews() {
        return userService.getLoggedInUserAllCheckoutPreviews();
    }
}
