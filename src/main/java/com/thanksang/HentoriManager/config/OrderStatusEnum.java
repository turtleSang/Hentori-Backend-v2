package com.thanksang.HentoriManager.config;

public enum OrderStatusEnum {
    CREATION("create"),
    FIRST_APPOINTMENT("first"),
    SECOND_APPOINTMENT("second"),
    FINISH("finish");

    String code;

    OrderStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}
