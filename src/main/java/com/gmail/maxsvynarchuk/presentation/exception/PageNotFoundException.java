package com.gmail.maxsvynarchuk.presentation.exception;

public class PageNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -2097098162974144614L;

    public PageNotFoundException() {
    }

    public PageNotFoundException(String message) {
        super(message);
    }

    public PageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageNotFoundException(Throwable cause) {
        super(cause);
    }
}