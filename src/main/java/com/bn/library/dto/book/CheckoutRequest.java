package com.bn.library.dto.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CheckoutRequest {
    @Positive(message = "Book ID must be a positive number")
    private int bookId;

    @NotNull(message = "Return date cannot be null")
    private LocalDate returnDate;
}
