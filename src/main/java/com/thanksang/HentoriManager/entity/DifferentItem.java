package com.thanksang.HentoriManager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "different_item")
public class DifferentItem extends ItemEntity{
    @Column
    private String name;
}
