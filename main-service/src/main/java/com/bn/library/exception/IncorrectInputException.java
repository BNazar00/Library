package com.bn.library.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IncorrectInputException extends IllegalArgumentException {
    private static final String INCORRECT_INPUT_EXCEPTION = "Incorrect data inputted";

    public IncorrectInputException(String message) {
        super(message.isEmpty() ? INCORRECT_INPUT_EXCEPTION : message);
    }

    public IncorrectInputException() {
        super(INCORRECT_INPUT_EXCEPTION);
    }
}
