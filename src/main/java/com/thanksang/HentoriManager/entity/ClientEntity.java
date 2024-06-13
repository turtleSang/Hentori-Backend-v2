package com.thanksang.HentoriManager.entity;

import com.thanksang.HentoriManager.entity.generator.CustomerGenerator;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class ClientEntity {
    @Id
    @Column(length = 12)
    @GeneratedValue(generator = "client_sequence")
    @GenericGenerator(
            name = "client_sequence",
            parameters = @Parameter(name = "nameID", value = "client"),
            type = CustomerGenerator.class
    )
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(length = 10, nullable = false, unique = true)
    private String phoneNumber;

    @OneToOne(mappedBy = "clientEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UpperClientEntity upperEntity;

    @OneToOne(mappedBy = "clientEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private BelowClientEntity belowEntity;

    @OneToMany(mappedBy = "clientEntity")
    private List<OrderEntity> orderEntityList;

}
