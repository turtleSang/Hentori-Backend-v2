package com.thanksang.HentoriManager.entity;

import com.thanksang.HentoriManager.config.EnumType.OrderStatus;
import com.thanksang.HentoriManager.entity.generator.OrderIDGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(generator = "order_generator")
    @GenericGenerator(
            name = "order_generator",
            parameters = @Parameter(name = "nameID", value = "order"),
            type = OrderIDGenerator.class
    )
    private String id;

    @Column(length = 12)
    private LocalDate appointment;

    @Column
    private LocalDate createAt;

    @Column
    private int total;

    @Column
    private OrderStatus orderStatusEnum;

    @OneToOne(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ReceivableEntity receivableEntity;

    @OneToMany(mappedBy = "orderEntity")
    private List<ItemEntity> itemEntities;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity clientEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
