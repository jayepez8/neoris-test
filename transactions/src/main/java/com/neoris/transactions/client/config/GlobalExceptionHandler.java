package com.neoris.transactions.client.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.neoris.transactions.client.exception.ExistException;
import com.neoris.transactions.client.exception.NotFoundException;
import com.neoris.transactions.client.exception.PersistException;
import com.neoris.transactions.vo.ErrorResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * @author jyepez on 19/7/2024
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        printMethodError(ex);
        String errorMessage = "Malformed JSON request";
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            errorMessage = "Invalid value for field: " + ((InvalidFormatException) cause).getPath().get(0).getFieldName();
        }
        return new ResponseEntity<>(
                ErrorResponseVo.builder().code(HttpStatus.BAD_REQUEST.value()).message(errorMessage).build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        printMethodError(ex);
        return new ResponseEntity<>(
                ErrorResponseVo.builder().code(HttpStatus.BAD_REQUEST.value()).message("Bad Request")
                        .errors(ex.getBindingResult().getFieldErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList())).build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseVo> handleNotFoundException(NotFoundException exception) {
        printMethodError(exception);
        return new ResponseEntity<>(
                ErrorResponseVo.builder().code(HttpStatus.NOT_FOUND.value()).message(exception.getMessage()).build(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistException.class)
    public ResponseEntity<ErrorResponseVo> handleExistException(ExistException exception) {
        printMethodError(exception);
        return new ResponseEntity<>(
                ErrorResponseVo.builder().code(HttpStatus.BAD_REQUEST.value()).message(exception.getMessage()).build(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersistException.class)
    public ResponseEntity<ErrorResponseVo> handlePersistException(PersistException exception) {
        printMethodError(exception);
        return new ResponseEntity<>(
                ErrorResponseVo.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static void printMethodError(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace.length > 1) {
            StackTraceElement caller = stackTrace[1];
            log.error("Exception thrown by method: {}.{}", caller.getClassName(), caller.getMethodName());
            log.error("Error ",e);
        }
    }
}