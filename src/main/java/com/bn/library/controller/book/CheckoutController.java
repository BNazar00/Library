package com.bn.library.controller.book;

import com.bn.library.constant.RoleData;
import com.bn.library.dto.book.CheckoutDto;
import com.bn.library.dto.book.CheckoutRequest;
import com.bn.library.service.CheckoutService;
import com.bn.library.util.annotation.AllowedRoles;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<CheckoutDto> getAllCheckouts() {
        return checkoutService.getAllCheckouts();
    }

    @AllowedRoles(RoleData.READER)
    @PostMapping
    public int addCheckout(@Valid @RequestBody CheckoutRequest request) {
        log.info("Book checkout request {}", request);
        return checkoutService.addCheckout(request);
    }

    @AllowedRoles({RoleData.ADMIN, RoleData.READER})
    @DeleteMapping("/{id}")
    public void cancelCheckout(@PathVariable("id") int id) {
        log.info("Checkout cancel request {}", id);
        checkoutService.cancelCheckout(id);
    }
}
