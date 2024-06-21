package com.thanksang.HentoriManager.entity.convert;

import com.thanksang.HentoriManager.config.EnumType.RoleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ConvertRoleEnum implements AttributeConverter<RoleType, String> {

    @Override
    public String convertToDatabaseColumn(RoleType roleEnum) {
        if (roleEnum == null){
            return null;
        }

        return roleEnum.getCode();
    }

    @Override
    public RoleType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(RoleType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
