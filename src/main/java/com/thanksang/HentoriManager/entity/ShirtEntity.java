package com.thanksang.HentoriManager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "shirt")
public class ShirtEntity extends ItemEntity{
    @Column(length = 20)
    private String kieuCo;

    @Column(length = 20)
    private String mangSec;

    @Column(length = 20)
    private String kieuDinh;

    @Column(length = 20)
    private String fabric;
}
