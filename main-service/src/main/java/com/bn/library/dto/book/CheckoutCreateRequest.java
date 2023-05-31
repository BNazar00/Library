package com.bn.library.dto.book;

import com.bn.library.util.validation.ReturnDateConstraint;
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
