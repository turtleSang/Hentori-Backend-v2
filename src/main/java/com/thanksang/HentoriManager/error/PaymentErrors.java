package com.thanksang.HentoriManager.error;

public class PaymentErrors extends RuntimeException{
    public PaymentErrors(String message) {
        super(message);
    }

    public PaymentErrors(String message, Throwable cause) {
        super(message, cause);
    }
}
