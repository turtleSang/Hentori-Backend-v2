package com.thanksang.HentoriManager.error;

public class OrderErrors extends RuntimeException{
    public OrderErrors(String message) {
        super(message);
    }

    public OrderErrors(String message, Throwable cause) {
        super(message, cause);
    }
}
