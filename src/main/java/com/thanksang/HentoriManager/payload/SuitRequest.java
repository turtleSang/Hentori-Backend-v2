package com.thanksang.HentoriManager.payload;

import lombok.Data;

@Data
public class SuitRequest extends ItemRequest{
    private String kieuAo;
    private String formAo;
    private String kieuVeAo;
    private String lotAo;
    private String kieuNut;
    private String kieuTui;
    private String fabric;
}
