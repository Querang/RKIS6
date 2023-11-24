package ru.sfu.querang.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Сущность пользователя
 */
@Entity
@Table(name = "users")
@Data
public class ShopUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NotEmpty
    @Size(min = 2, max = 100)
    private String username;

    @NotEmpty
    @Column(name = "u_password")
    private String password;

    @Column(name = "u_role")
    private String role;

}
