package com.bn.library.dto.book;

import com.bn.library.constant.CheckoutStatus;
import lombok.Data;

@Data
public class CheckoutPreview {
    private int id;
    private BookCopyPreview bookCopy;
    private CheckoutStatus status;
}
