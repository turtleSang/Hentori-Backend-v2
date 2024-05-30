package com.thanksang.HentoriManager.error;

public class JwtErrors extends RuntimeException {
    public JwtErrors(String message) {
        super(message);
    }

    public JwtErrors(String message, Throwable cause) {
        super(message, cause);
    }
}
