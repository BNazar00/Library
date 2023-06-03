package com.bn.book.dto;

import com.bn.book.util.validation.ReturnDateConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CheckoutCreateRequest {
    @Positive(message = "Book ID must be a positive number")
    private int bookId;

    @ReturnDateConstraint
    @NotNull(message = "Return date cannot be null")
    private LocalDate returnDate;
}
