package com.bettingApp.exceptions.customExceptions;

public class PaymentCannotProcessedException extends RuntimeException {
    public PaymentCannotProcessedException(String message) {
        super(message);
    }
}