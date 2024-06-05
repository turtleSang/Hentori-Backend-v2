package com.thanksang.HentoriManager.entity.convert;

import com.thanksang.HentoriManager.config.RoleTypeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ConvertRoleEnum implements AttributeConverter<RoleTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(RoleTypeEnum roleEnum) {
        if (roleEnum == null){
            return null;
        }

        return roleEnum.getCode();
    }

    @Override
    public RoleTypeEnum convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(RoleTypeEnum.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
