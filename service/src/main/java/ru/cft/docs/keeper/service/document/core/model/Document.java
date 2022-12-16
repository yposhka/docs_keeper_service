package ru.cft.docs.keeper.service.document.core.model;

import lombok.Builder;
import lombok.Value;
import ru.cft.docs.keeper.service.document.core.model.enumeration.DocumentStatus;
import ru.cft.docs.keeper.service.document.core.model.enumeration.DocumentType;

import java.util.Date;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
public class Document {

    Long id;

    String title;

    String data;

    Date creationDate;

    Date expirationDate;

    DocumentStatus status;

    DocumentType type;
}
