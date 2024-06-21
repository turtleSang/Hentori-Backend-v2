package com.thanksang.HentoriManager.entity;

import com.thanksang.HentoriManager.config.EnumType.RoleType;
import com.thanksang.HentoriManager.entity.generator.UserGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


import java.util.List;

@Getter
@Setter
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
    private RoleType roleEnum;

    @OneToMany(mappedBy = "userEntity")
    private List<OrderEntity> orderEntityList;
}
