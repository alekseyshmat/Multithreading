package com.epam.multithreading.exception;

public class ResourсeException extends Exception {

    public ResourсeException(String message) {
        super(message);
    }

    public ResourсeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourсeException(Throwable cause) {
        super(cause);
    }
}
