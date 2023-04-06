package com.bn.library.exception;

public class JsonWriteException extends RuntimeException {
    private static final String JSON_WRITE_EXCEPTION = "Json can't be created";

    public JsonWriteException(String message) {
        super(message.isEmpty() ? JSON_WRITE_EXCEPTION : message);
    }

    public JsonWriteException() {
        super(JSON_WRITE_EXCEPTION);
    }
}
