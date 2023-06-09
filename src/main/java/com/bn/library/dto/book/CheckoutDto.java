package com.bn.library.dto.book;

import com.bn.library.constant.CheckoutStatus;
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
