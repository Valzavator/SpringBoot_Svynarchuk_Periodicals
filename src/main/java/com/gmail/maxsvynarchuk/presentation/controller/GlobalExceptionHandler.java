package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.presentation.exception.BadRequestException;
import com.gmail.maxsvynarchuk.presentation.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class,
            BadRequestException.class})
    public void handleParameterExceptions(Throwable throwable, HttpServletResponse response)
            throws IOException {
        log.warn(throwable.toString());
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NotFoundException.class)
    public void handlePageNotFoundException(Throwable throwable, HttpServletResponse response)
            throws IOException {
        log.warn(throwable.toString());
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Throwable.class)
    public void handleThrowable(Throwable throwable, HttpServletResponse response)
            throws IOException {
        log.error(throwable.toString());
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
