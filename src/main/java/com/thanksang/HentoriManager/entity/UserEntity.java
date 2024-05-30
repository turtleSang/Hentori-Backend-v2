package com.thanksang.HentoriManager.entity;

import com.thanksang.HentoriManager.config.Constance;
import com.thanksang.HentoriManager.config.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

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
    private RoleEnum roleEnum;
}
