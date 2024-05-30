package com.thanksang.HentoriManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "below")
public class BelowClientEntity {
    @Id
    @Column(name = "client_id", length = 12)
    private String id;

    @Column(length = 10)
    private String vongLung;

    @Column(length = 10)
    private String ngangBung;

    @Column(length = 10)
    private String vongDay;

    @Column(length = 10)
    private String vongMong;

    @Column(length = 10)
    private String vongDui;

    @Column(length = 10)
    private String duiGiua;

    @Column(length = 10)
    private String vongGoi;

    @Column(length = 10)
    private String vongBapChan;

    @Column(length = 10)
    private String ongQuan;

    @Column(length = 10)
    private String daiQuan;

    @OneToOne
    @MapsId
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity;
}
