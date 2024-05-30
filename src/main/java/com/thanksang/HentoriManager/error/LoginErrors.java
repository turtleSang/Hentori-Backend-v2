package com.thanksang.HentoriManager.error;

public class LoginErrors extends RuntimeException{
    public LoginErrors(String message) {
        super(message);
    }
}
