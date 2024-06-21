package com.thanksang.HentoriManager.dto;

import com.thanksang.HentoriManager.config.EnumType.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDto {
    private String id;
    private String clientName;
    private int total;
    private OrderStatus orderStatusEnum;
    private LocalDate Appointment;
    private int payment;
    private int remaining;
    private String username;
}
