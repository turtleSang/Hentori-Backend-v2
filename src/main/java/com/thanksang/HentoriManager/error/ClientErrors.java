package com.thanksang.HentoriManager.error;

public class ClientErrors extends RuntimeException {

    public ClientErrors(String message) {
        super(message);
    }

    public ClientErrors(String message, Throwable cause) {
        super(message, cause);
    }
}
