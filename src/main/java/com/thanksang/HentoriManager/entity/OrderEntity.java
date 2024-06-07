package com.thanksang.HentoriManager.entity;

import com.thanksang.HentoriManager.config.OrderStatusEnum;
import com.thanksang.HentoriManager.entity.generator.OrderIDGenerator;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDate;

@Data
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
    private int total;

    @Column
    private OrderStatusEnum orderStatusEnum;

    @OneToOne(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ReceivableEntity receivableEntity;
}
