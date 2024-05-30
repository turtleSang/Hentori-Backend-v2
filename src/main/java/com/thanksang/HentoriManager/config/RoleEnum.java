package com.thanksang.HentoriManager.config;

public enum RoleEnum {
    MANAGER("M"),
    STAFF("S");

    String code;

    RoleEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
