package com.bn.book.dto;

import com.bn.book.constant.CheckoutStatus;
import lombok.Data;

@Data
public class CheckoutPreview {
    private int id;
    private BookCopyPreview bookCopy;
    private CheckoutStatus status;
}
