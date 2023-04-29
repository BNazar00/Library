package com.bn.library.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUploadException extends IllegalArgumentException {
    private static final String FILE_UPLOAD_EXCEPTION = "File upload exception";

    public FileUploadException(String message) {
        super(message.isEmpty() ? FILE_UPLOAD_EXCEPTION : message);
    }

    public FileUploadException() {
        super(FILE_UPLOAD_EXCEPTION);
    }
}
