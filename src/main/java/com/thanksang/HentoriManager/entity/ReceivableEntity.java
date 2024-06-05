package com.thanksang.HentoriManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ReceivableEntity {
    @Id
    @Column(name = "order_id")
    private int id;

    @Column
    private int amount;

    @Column
    private int payment;

    @Column
    private int remaining;

    @Column
    private boolean status;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;



}
