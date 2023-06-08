package com.bn.clients.book.dto;

import com.bn.clients.book.constant.CheckoutStatus;
import lombok.Data;

@Data
public class CheckoutPreview {
    private int id;
    private BookCopyPreview bookCopy;
    private CheckoutStatus status;
}
