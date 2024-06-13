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
        @JsonSubTypes.Type(value = ShirtRequest.class, name = "shirt"),
        @JsonSubTypes.Type(value = TrousersRequest.class, name = "trousers"),
        @JsonSubTypes.Type(value = SuitRequest.class, name = "suit"),
        @JsonSubTypes.Type(value = DifferentItemRequest.class, name = "different")
})
@Data
public abstract class ItemRequest {
    private int price;
    private int amount;
    private String note;
}
