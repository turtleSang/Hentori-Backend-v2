package com.thanksang.HentoriManager.config.EnumType;

public enum OrderStatus {
    CREATION("create"),
    FIRST_APPOINTMENT("first"),
    SECOND_APPOINTMENT("second"),
    FINISH("finish");

    String code;

    OrderStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}
