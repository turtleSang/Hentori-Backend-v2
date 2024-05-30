package com.thanksang.HentoriManager.payload;

import lombok.Data;

@Data
public class AdminRequest {
    private String username;
    private String password;
    private String roleCode;
}
