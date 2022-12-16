package ru.cft.docs.keeper.service.authorization.core.repository.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "token")
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
public class TokenEntity {
    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "user_id")
    private Long userId;
}
