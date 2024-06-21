package com.thanksang.HentoriManager.entity.convert;

import com.thanksang.HentoriManager.config.EnumType.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ConvertStatusEnum implements AttributeConverter<OrderStatus, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatusEnum) {
        return orderStatusEnum.getCode();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return Stream.of(OrderStatus.values())
                .filter(status -> status.getCode().equals(s))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
