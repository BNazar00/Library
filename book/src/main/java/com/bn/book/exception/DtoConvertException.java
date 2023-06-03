package com.bn.book.exception;

public class DtoConvertException extends IllegalArgumentException {
    private static final String CONVERT_EXCEPTION = "Entity convert exception";

    public DtoConvertException(String message) {
        super(message.isEmpty() ? CONVERT_EXCEPTION : message);
    }

    public DtoConvertException() {
        super(CONVERT_EXCEPTION);
    }
}
