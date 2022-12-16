package ru.cft.docs.keeper.service.document.core.converter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;
import ru.cft.docs.keeper.service.authorization.core.exception.ServiceException;
import ru.cft.docs.keeper.service.document.core.model.DataField;
import ru.cft.docs.keeper.service.document.core.model.DocumentRequest;
import ru.cft.docs.keeper.service.document.core.model.DocumentTransfer;

@Component
public class JsonConversion {

    private final ObjectMapper mapper = new ObjectMapper();

    private final JsonFactory factory = mapper.getFactory();

    public DocumentTransfer convertData(DocumentRequest documentRequest) {
        DocumentTransfer documentTransfer = DocumentTransfer.builder()
                .title(documentRequest.getTitle())
                .type(documentRequest.getType())
                .build();
        ObjectNode jsonData = JsonNodeFactory.instance.objectNode();
        for (DataField field : documentRequest.getData()) {
            jsonData.put(field.getName(), field.getContent());
        }
        documentTransfer.setData(jsonData);
        return documentTransfer;
    }
    public JsonNode parserData(String data) {
        try {
            JsonParser jsonParser = factory.createParser(data);
            return mapper.readTree(jsonParser);
        } catch (Exception e) {
            throw new ServiceException(
                    ErrorCode.INTERNAL_SERVER_ERROR, "Error converting data from string to json object"
            );
        }
    }
}