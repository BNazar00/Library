package com.bn.book.dto;

import com.bn.clients.book.constant.CheckoutStatus;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CheckoutDto {
    private int id;
    private String userEmail;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private BookCopyDto bookCopy;
    private CheckoutStatus status;
}
