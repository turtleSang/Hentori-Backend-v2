package com.thanksang.HentoriManager.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String messenger;

    public LoginDto(String messenger) {
        this.messenger = messenger;
    }
}
