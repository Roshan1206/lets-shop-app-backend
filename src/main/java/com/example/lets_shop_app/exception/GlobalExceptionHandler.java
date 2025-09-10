package com.example.lets_shop_app.exception;

import com.example.lets_shop_app.dto.response.ErrorResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


/**
 * Handling exception globally
 *
 * @author Roshan
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Will be thrown if user tries to create account with duplicate email
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException exception, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        if(exception.getMessage().contains("email")){
            errorResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
            errorResponseDto.setStatusReason(HttpStatus.BAD_REQUEST.getReasonPhrase());
            errorResponseDto.setErrorMessage("Email Already exist. Please use different email.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleResponseStatusException(ResponseStatusException exception, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                exception.getStatusCode().value(),
                exception.getReason(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(exception.getStatusCode()).body(errorResponseDto);
    }
}
