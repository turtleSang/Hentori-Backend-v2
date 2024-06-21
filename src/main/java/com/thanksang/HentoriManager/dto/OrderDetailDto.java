package com.thanksang.HentoriManager.dto;

import com.thanksang.HentoriManager.config.EnumType.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderDetailDto {
    private String id;
    private LocalDate appointment;
    private LocalDate createAt;
    private String username;
    private String clientName;
    private OrderStatus orderStatusEnum;
    private int total;
    private int payment;
    private int remaining;
    private List<ItemDto> itemDtoList;

}
