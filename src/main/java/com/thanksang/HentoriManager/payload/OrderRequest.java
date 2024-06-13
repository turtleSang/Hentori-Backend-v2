package com.thanksang.HentoriManager.payload;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderRequest {
    private LocalDate appointment;
    private String status;
    private String clientID;
    private List<ItemRequest> itemRequestList;
}
