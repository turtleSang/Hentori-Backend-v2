package com.thanksang.HentoriManager.payload;

import com.thanksang.HentoriManager.payload.ItemRequest;
import lombok.Data;

@Data
public class TrousersRequest extends ItemRequest {
    private String formQuan;
    private String kieuLung;
    private String kieuTuiTruoc;
    private String kieuTuiSau;
    private String soTui;
    private String kieuLai;
    private String fabric;
}
