package com.thanksang.HentoriManager.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateOrderRequest {
    private String status;
    private LocalDate appointment;
}
