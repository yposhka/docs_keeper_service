package ru.cft.docs.keeper.service.document.core.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "document")
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "data")
    private String data;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;
}
