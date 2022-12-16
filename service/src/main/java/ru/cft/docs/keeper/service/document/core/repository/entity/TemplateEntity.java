package ru.cft.docs.keeper.service.document.core.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "document_type")
@NoArgsConstructor
public class TemplateEntity {

    @Id
    @Column(name = "type")
    private String type;

    @Column(name = "structure")
    private String structure;
}
