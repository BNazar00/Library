package com.bn.library.exception;

public class UserPermissionException extends RuntimeException {
    private static final String PERMIT_EXCEPTION = "You have no necessary permissions (role)";

    public UserPermissionException(String message) {
        super(message.isEmpty() ? PERMIT_EXCEPTION : message);
    }

    public UserPermissionException() {
        super(PERMIT_EXCEPTION);
    }
}
