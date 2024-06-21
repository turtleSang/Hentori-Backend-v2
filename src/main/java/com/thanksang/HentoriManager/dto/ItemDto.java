package com.thanksang.HentoriManager.dto;

import com.thanksang.HentoriManager.config.EnumType.TypeItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ItemDto {
    private int id;
    private int price;
    private int amount;
    private int total;
    private String note;
    private TypeItem type;
}
