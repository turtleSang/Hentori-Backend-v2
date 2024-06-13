package com.thanksang.HentoriManager.payload;

import lombok.Data;

@Data
public class ShirtRequest extends ItemRequest{
    private String kieuCo;
    private String mangSec;
    private String kieuDinh;
    private String fabric;

}
