package com.thanksang.HentoriManager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "suit")
public class SuitEntity extends ItemEntity {
    @Column(length = 20)
    private String kieuAo;

    @Column(length = 20)
    private String formAo;

    @Column(length = 20)
    private String kieuVeAo;

    @Column(length = 20)
    private String kieuXe;

    @Column(length = 20)
    private String lotAo;

    @Column(length = 20)
    private String kieuNut;

    @Column(length = 20)
    private String kieuTui;

    @Column(length = 20)
    private String fabric;
}
