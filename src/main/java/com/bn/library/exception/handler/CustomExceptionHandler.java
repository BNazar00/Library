package com.bn.library.exception.handler;

import com.bn.library.dto.exception.ExceptionResponse;
import com.bn.library.exception.JsonWriteException;
import com.bn.library.exception.UserAuthenticationException;
import com.bn.library.exception.UserPermissionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@AllArgsConstructor
@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildExceptionBody(Exception exception, HttpStatus httpStatus) {
        ExceptionResponse exceptionResponse =
                ExceptionResponse.builder().status(httpStatus.value()).message(exception.getMessage()).build();
        log.debug(exception.getMessage());
        return ResponseEntity.status(httpStatus).body(exceptionResponse);
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public final ResponseEntity<Object> handleUserAuthenticationException(UserAuthenticationException exception) {
        return buildExceptionBody(exception, FORBIDDEN);
    }

    @ExceptionHandler(UserPermissionException.class)
    public final ResponseEntity<Object> handleUserPermissionException(UserPermissionException exception) {
        return buildExceptionBody(exception, FORBIDDEN);
    }

    @ExceptionHandler(JsonWriteException.class)
    public final ResponseEntity<Object> handleJsonWriteException(JsonWriteException exception) {
        return buildExceptionBody(exception, BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
        return buildExceptionBody(exception, BAD_REQUEST);
    }
}
