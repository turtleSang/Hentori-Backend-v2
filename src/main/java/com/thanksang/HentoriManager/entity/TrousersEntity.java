package com.thanksang.HentoriManager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "trousers")
public class TrousersEntity extends ItemEntity{
    @Column(length = 20)
    private String formQuan;

    @Column(length = 20)
    private String kieuLung;

    @Column(length = 20)
    private String kieuTuiTruoc;

    @Column(length = 20)
    private String kieuTuiSau;

    @Column(length = 20)
    private String soTui;

    @Column(length = 20)
    private String kieuLai;

    @Column(length = 20)
    private String fabric;
}
