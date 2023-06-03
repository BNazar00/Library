package com.bn.book.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookCreateRequest {
    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @Positive(message = "Author ID must be a positive number")
    private int authorId;

    @NotNull(message = "Photo URL cannot be null")
    private String photoUrl;

    @Positive(message = "Publisher ID must be a positive number")
    private int publisherId;

    @Min(value = 1000, message = "Publication year must be at least 1000")
    @Max(value = 3000, message = "Publication year cannot exceed 3000")
    private int publicationYear;

    @Min(value = 1, message = "Page count must be at least 1")
    @Max(value = 10000, message = "Page count cannot exceed 10000")
    private int pageCount;

    @Positive(message = "Number of copies must be a positive number")
    private int numberOfCopies;

    @Min(value = 0, message = "Price cannot be negative")
    private int price;
}
