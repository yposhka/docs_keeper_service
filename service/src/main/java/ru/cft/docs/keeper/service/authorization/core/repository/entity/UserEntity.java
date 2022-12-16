package ru.cft.docs.keeper.service.authorization.core.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "hash_password")
    private String hashPassword;

    @Column(name = "salt")
    private String salt;

    @Column(name = "status")
    private String status;
}
