package com.bn.book.exception;

public class InsufficientFundsException extends RuntimeException {
    private static final String INSUFFICIENT_FUNDS_EXCEPTION = "User doesn't have enough money.";

    public InsufficientFundsException(String message) {
        super(message.isEmpty() ? INSUFFICIENT_FUNDS_EXCEPTION : message);
    }

    public InsufficientFundsException() {
        super(INSUFFICIENT_FUNDS_EXCEPTION);
    }
}
