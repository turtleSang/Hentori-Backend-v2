package com.thanksang.HentoriManager.entity.convert;

import com.thanksang.HentoriManager.config.OrderStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ConvertStatusEnum implements AttributeConverter<OrderStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatusEnum orderStatusEnum) {
        return orderStatusEnum.getCode();
    }

    @Override
    public OrderStatusEnum convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return Stream.of(OrderStatusEnum.values())
                .filter(status -> status.getCode().equals(s))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
