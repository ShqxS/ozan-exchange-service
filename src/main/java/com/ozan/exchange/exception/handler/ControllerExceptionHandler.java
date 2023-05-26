package com.ozan.exchange.exception.handler;

import com.ozan.exchange.exception.ExpectedError;
import com.ozan.exchange.exception.NotExpectedError;
import com.ozan.exchange.model.api.ApiException;
import com.ozan.exchange.model.api.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleError(Exception e) {
        log.error(String.format("Error Message: %s", e.getMessage()), e);
        return ApiResponse.failed(
                500, e.getClass().getName(), e.getLocalizedMessage() + " " + e.getCause());
    }

    @ExceptionHandler(NotExpectedError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleNotExpectedError(NotExpectedError e) {
        log.error(String.format("Error Message: %s", e.getMessage()), e);
        return ApiResponse.failed(e.getCode(), e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleError(MethodArgumentNotValidException e) {
        log.error(String.format("Error Message: %s", e.getMessage()), e);
        final List<ApiException> errors =
                e.getBindingResult().getFieldErrors().stream()
                        .map(
                                fieldError ->
                                        new ApiException(
                                                400,
                                                e.getClass().getName(),
                                                "Invalid value submitted for field: "
                                                        + fieldError.getField()
                                                        + ", error message is: "
                                                        + fieldError.getDefaultMessage()))
                        .collect(Collectors.toList());

        return ApiResponse.failed(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> onConstraintValidationException(ConstraintViolationException e) {
        log.error(String.format("Error Message: %s", e.getMessage()), e);
        final List<ApiException> errors = e.getConstraintViolations().stream()
                .map(constraintViolation -> new ApiException(400, constraintViolation.getClass().getName(), "Invalid value submitted for field: "
                        + constraintViolation.getPropertyPath()
                        + ", error message is: "
                        + constraintViolation.getMessage()))
                .collect(Collectors.toList());

        return ApiResponse.failed(errors);
    }

    @ExceptionHandler(ExpectedError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleError(ExpectedError e) {
        log.error(String.format("Error Message: %s", e.getMessage()), e);
        return ApiResponse.failed(e.getCode(), e.getClass().getName(), e.getMessage());
    }
}
