package com.potato.ecommerce.global.exception;

import com.potato.ecommerce.global.exception.custom.AuthenticationFailedException;
import com.potato.ecommerce.global.exception.custom.OutOfStockException;
import com.potato.ecommerce.global.exception.dto.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import java.util.Objects;
import javax.security.auth.login.LoginException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> validExceptionHandler(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ExceptionDto(HttpStatus.BAD_REQUEST, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()));
    }

    @ExceptionHandler({
        EntityNotFoundException.class,
        AuthenticationFailedException.class,
        ValidationException.class,
        OutOfStockException.class,
        LoginException.class,
        BadRequestException.class
    })
    public ResponseEntity<ExceptionDto> badRequestExceptionHandler(RuntimeException e) {
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<ExceptionDto> createResponse(HttpStatus status, String message) {
        return ResponseEntity
            .status(status)
            .body(new ExceptionDto(status, message));
    }
}
