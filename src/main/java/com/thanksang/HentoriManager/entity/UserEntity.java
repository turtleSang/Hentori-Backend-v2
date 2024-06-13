package com.thanksang.HentoriManager.entity;

import com.thanksang.HentoriManager.config.RoleTypeEnum;
import com.thanksang.HentoriManager.entity.generator.UserGenerator;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "user_generate")
    @GenericGenerator(
            name = "user_generate",
            parameters =@org.hibernate.annotations.Parameter(name = "nameID", value = "user"),
            type = UserGenerator.class

    )
    private String id;

    @Column(name = "username", length = 12, nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(length = 1)
    private RoleTypeEnum roleEnum;

    @OneToMany(mappedBy = "userEntity")
    private List<OrderEntity> orderEntityList;
}
