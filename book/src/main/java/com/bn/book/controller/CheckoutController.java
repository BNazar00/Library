package com.bn.book.controller;

import com.bn.book.dto.CheckoutCreateRequest;
import com.bn.book.dto.CheckoutDto;
import com.bn.book.dto.CheckoutUpdateRequest;
import com.bn.book.services.CheckoutService;
import com.bn.book.util.annotation.AllowedRoles;
import com.bn.clients.constant.RoleData;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book/checkout")
@Slf4j
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @AllowedRoles({RoleData.ADMIN, RoleData.READER})
    @GetMapping("/{id}")
    public CheckoutDto getCheckout(@PathVariable("id") int id) {
        return checkoutService.getCheckoutDtoById(id);
    }

    @AllowedRoles(RoleData.ADMIN)
    @GetMapping("/all")
    public List<CheckoutDto> getAllCheckoutsOrderByIdDesc() {
        return checkoutService.getAllCheckoutsOrderByIdDesc();
    }

    @AllowedRoles(RoleData.READER)
    @PostMapping
    public int addCheckout(@Valid @RequestBody CheckoutCreateRequest request) {
        log.info("Book checkout request {}", request);
        return checkoutService.addCheckout(request);
    }

    @AllowedRoles(RoleData.ADMIN)
    @PatchMapping
    public void updateCheckout(@Valid @RequestBody CheckoutUpdateRequest request) {
        log.info("Checkout update request {}", request);
        checkoutService.updateCheckout(request);
    }

    @AllowedRoles({RoleData.ADMIN, RoleData.READER})
    @DeleteMapping("/{id}")
    public void cancelCheckout(@PathVariable("id") int id) {
        log.info("Checkout cancel request {}", id);
        checkoutService.cancelCheckout(id);
    }
}
