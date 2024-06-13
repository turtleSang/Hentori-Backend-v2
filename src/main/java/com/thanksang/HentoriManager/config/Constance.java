package com.thanksang.HentoriManager.config;

import java.lang.reflect.Field;

public class Constance {
    public static final String regexUsername = "^[a-zA-Z0-9]{4,12}$";
    public static final String regexPassword = "^[a-zA-Z0-9]{6,16}$";
    public static final int lengthOfClientId = 5;
    public static final String regexPhoneNumber = "^0[9|8|3][0-9]{8}$";
    public static final int pageSizeClientSearch = 5;

    public static void checkNullField(Object o) throws IllegalAccessException {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field: fields
             ) {
            field.setAccessible(true);
            if (field.get(o) == null){
                throw new RuntimeException(field.getName() + " is null");
            }
        }
    }
}
