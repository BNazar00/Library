package com.bn.book.dto;

import com.bn.clients.book.constant.CheckoutStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CheckoutUpdateRequest {
    @Positive(message = "ID must be a positive number")
    private int id;
    @NotNull(message = "Issue date cannot be null")
    private LocalDate issueDate;
    @NotNull(message = "Return date cannot be null")
    private LocalDate returnDate;
    @NotNull(message = "Status cannot be null")
    private CheckoutStatus status;
}
