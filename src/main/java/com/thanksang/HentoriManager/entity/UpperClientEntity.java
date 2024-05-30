package com.thanksang.HentoriManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "upper")
public class UpperClientEntity {
    @Id
    @Column(name = "client_id", length = 12)
    private String id;

    @Column(length = 10)
    private String doXuoiVai;

    @Column(length = 10)
    private String dangVai;

    @Column(length = 10)
    private String rongVai;

    @Column(length = 10)
    private String haCaiKhuy;

    @Column(length = 10)
    private String daiTay;

    @Column(length = 10)
    private String haNguc;

    @Column(length = 10)
    private String daiAo;

    @Column(length = 10)
    private String haEo;

    @Column(length = 10)
    private String bapTayKhuyTay;

    @Column(length = 10)
    private String haBung;

    @Column(length = 10)
    private String mangSec;

    @Column(length = 10)
    private String vongCo;

    @Column(length = 10)
    private String vongNach;

    @Column(length = 10)
    private String haKhuy;

    @Column(length = 10)
    private String vongNguc;

    @Column(length = 10)
    private String ngangNguc;

    @Column(length = 10)
    private String vongEoBung;

    @Column(length = 10)
    private String ngangBungTS;

    @Column(length = 10)
    private String mongAo;

    @Column(length = 10)
    private String haHomLung;

    @Column(length = 10)
    private String haChomBung;


    @OneToOne
    @MapsId
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity;


}
