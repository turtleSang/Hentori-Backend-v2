package com.thanksang.HentoriManager.config.EnumType;

public enum RoleType {
    MANAGER("M"),
    STAFF("S");

    String code;

    RoleType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
