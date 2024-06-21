package com.thanksang.HentoriManager.payload;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ShirtRequest.class, name = "SHIRT"),
        @JsonSubTypes.Type(value = TrousersRequest.class, name = "TROUSERS"),
        @JsonSubTypes.Type(value = SuitRequest.class, name = "SUIT"),
        @JsonSubTypes.Type(value = DifferentItemRequest.class, name = "DIFFERENT")
})
@Data
public abstract class ItemRequest {
    private int price;
    private int amount;
    private String note;
}
