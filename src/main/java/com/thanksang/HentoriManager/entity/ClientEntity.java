package com.thanksang.HentoriManager.entity;

import com.thanksang.HentoriManager.entity.generator.CustomGenerator;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

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
            type = CustomGenerator.class
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

}
