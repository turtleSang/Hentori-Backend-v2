package com.thanksang.HentoriManager.error;

public class AccountErrors extends RuntimeException{
    public AccountErrors(String message) {
        super(message);
    }

    public AccountErrors(String message, Throwable cause) {
        super(message, cause);
    }
}
