package com.thanksang.HentoriManager.payload;

import lombok.Data;

@Data
public class PaymentOrderRequest {
    private String receivableID;
    private int amount;
    private String accountID;
}
