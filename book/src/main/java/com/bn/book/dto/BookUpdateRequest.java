package com.bn.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookUpdateRequest {
    @Positive(message = "ID must be a positive number")
    private int id;

    @Positive(message = "Publication year must be a positive number")
    private int publicationYear;

    @Positive(message = "Page count must be a positive number")
    private int pageCount;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price cannot be negative")
    private BigDecimal price;
}
