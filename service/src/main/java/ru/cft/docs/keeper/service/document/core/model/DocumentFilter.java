package ru.cft.docs.keeper.service.document.core.model;

import lombok.Builder;
import lombok.Data;
import ru.cft.docs.keeper.service.document.core.model.enumeration.DocumentStatus;
import ru.cft.docs.keeper.service.document.core.model.enumeration.DocumentType;

import java.util.Date;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
public class DocumentFilter {

    Boolean excludeData;

    String sort;

    DocumentStatus status;

    DocumentType type;

    Date creationDateFrom;

    Date creationDateTo;

    Date expirationDateFrom;

    Date expirationDateTo;

    Long userId;
}
