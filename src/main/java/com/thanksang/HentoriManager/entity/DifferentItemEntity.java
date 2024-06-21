package com.thanksang.HentoriManager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "different_item")
public class DifferentItemEntity extends ItemEntity{
    @Column
    private String name;
}
