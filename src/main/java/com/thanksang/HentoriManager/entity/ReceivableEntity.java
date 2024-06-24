package com.thanksang.HentoriManager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "receivable")
@EqualsAndHashCode(exclude = "orderEntity")
@ToString(exclude = "orderEntity")
public class ReceivableEntity {

    @Id
    @Column(name = "order_id")
    private String id;

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

    @OneToMany(mappedBy = "receivableEntity")
    private Set<RevenueEntity> revenueEntity;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity;

}
