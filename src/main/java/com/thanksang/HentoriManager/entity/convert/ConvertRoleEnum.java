package com.thanksang.HentoriManager.entity.convert;

import com.thanksang.HentoriManager.config.RoleEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ConvertRoleEnum implements AttributeConverter<RoleEnum, String> {

    @Override
    public String convertToDatabaseColumn(RoleEnum roleEnum) {
        if (roleEnum == null){
            return null;
        }

        return roleEnum.getCode();
    }

    @Override
    public RoleEnum convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(RoleEnum.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
