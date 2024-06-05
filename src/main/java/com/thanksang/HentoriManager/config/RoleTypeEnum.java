package com.thanksang.HentoriManager.config;

public enum RoleTypeEnum {
    MANAGER("M"),
    STAFF("S");

    String code;

    RoleTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
