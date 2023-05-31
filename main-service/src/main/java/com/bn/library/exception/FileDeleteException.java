package com.bn.library.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileDeleteException extends IllegalArgumentException {
    private static final String FILE_DELETE_EXCEPTION = "File delete exception";

    public FileDeleteException(String message) {
        super(message.isEmpty() ? FILE_DELETE_EXCEPTION : message);
    }

    public FileDeleteException() {
        super(FILE_DELETE_EXCEPTION);
    }
}
