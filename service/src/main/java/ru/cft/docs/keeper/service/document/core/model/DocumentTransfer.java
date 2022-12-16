package ru.cft.docs.keeper.service.document.core.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
public class DocumentTransfer {

    Long id;

    String title;

    JsonNode data;

    Date creationDate;

    Date expirationDate;

    String status;

    String type;
}
