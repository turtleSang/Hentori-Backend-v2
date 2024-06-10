package com.thanksang.HentoriManager.entity;

import com.thanksang.HentoriManager.config.RoleTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", length = 12, nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(length = 1)
    private RoleTypeEnum roleEnum;

    @OneToMany(mappedBy = "userEntity")
    private List<OrderEntity> orderEntityList;
}
