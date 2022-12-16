package ru.cft.docs.keeper.service.document.core.service.implementation;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;
import ru.cft.docs.keeper.service.authorization.core.exception.ServiceException;
import ru.cft.docs.keeper.service.document.core.model.Document;
import ru.cft.docs.keeper.service.document.core.model.DocumentFilter;
import ru.cft.docs.keeper.service.document.core.model.DocumentRequest;
import ru.cft.docs.keeper.service.document.core.model.DocumentTransfer;
import ru.cft.docs.keeper.service.document.core.model.enumeration.DocumentStatus;
import ru.cft.docs.keeper.service.document.core.model.enumeration.DocumentType;
import ru.cft.docs.keeper.service.document.core.repository.DocumentRepository;
import ru.cft.docs.keeper.service.document.core.repository.TemplateRepository;
import ru.cft.docs.keeper.service.document.core.repository.entity.DocumentEntity;
import ru.cft.docs.keeper.service.document.core.service.DocumentService;
import ru.cft.docs.keeper.service.document.core.validation.DocumentValidator;
import ru.cft.docs.keeper.service.document.core.validation.FilterValidator;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    
    private final TemplateRepository templateRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final JsonFactory factory = mapper.getFactory();

    @Override
    public DocumentTransfer get(Long id) {
        //TODO
        Long userId = 1L;
        //TODO
        Optional<DocumentEntity> documentEntity = documentRepository.findByIdAndUserId(id, userId);
        if (documentEntity.isEmpty()) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, "The document was not found");
        }
        Document document = convert(documentEntity.get());
        return DocumentTransfer.builder()
                .id(document.getId())
                .title(document.getTitle())
                .data(parserData(document.getData()))
                .creationDate(document.getCreationDate())
                .expirationDate(document.getExpirationDate())
                .status(document.getStatus().toJson())
                .type(document.getType().toJson())
                .build();
    }

    @Override
    public List<DocumentTransfer> get(DocumentFilter documentFilter) {
        FilterValidator.checkSort("sort", documentFilter.getSort());
        FilterValidator.checkCreationDate("creationDateFrom", documentFilter.getCreationDateFrom());
        FilterValidator.checkCreationDate("creationDateTo", documentFilter.getCreationDateTo());
        FilterValidator.checkExpirationDate("expirationDateFrom", documentFilter.getExpirationDateFrom());
        FilterValidator.checkExpirationDate("expirationDateTo", documentFilter.getExpirationDateTo());
        //TODO
        documentFilter.setUserId(1L);
        //TODO
        List<DocumentEntity> documentEntities;
        try {
            if (documentFilter.getSort().equals("asc")) {
                documentEntities = documentRepository.getListAsc(documentFilter);

            } else if (documentFilter.getSort().equals("desc")) {
                documentEntities = documentRepository.getListDesc(documentFilter);

            } else
                throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, "Documents was not found");

        } catch (Exception e) {
            throw new ServiceException(
                    ErrorCode.INTERNAL_SERVER_ERROR, "Documents was not found");
        }
        List<DocumentTransfer> documents = Arrays.asList(new DocumentTransfer[documentEntities.size()]);

        for (int i = 0; i < documentEntities.size(); i++) {

            DocumentTransfer documentTransfer = DocumentTransfer.builder()
                    .id(documentEntities.get(i).getId())
                    .title(documentEntities.get(i).getTitle())
                    .data(parserData(documentEntities.get(i).getData()))
                    .creationDate(documentEntities.get(i).getCreationDate())
                    .expirationDate(documentEntities.get(i).getExpirationDate())
                    .status(documentEntities.get(i).getStatus())
                    .type(documentEntities.get(i).getType())
                    .build();

            documents.set(i, documentTransfer);
        }
        return documents;
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

    private Document convert(DocumentEntity documentEntity) {
        return Document.builder()
                .id(documentEntity.getId())
                .title(documentEntity.getTitle())
                .data(documentEntity.getData())
                .creationDate(documentEntity.getCreationDate())
                .expirationDate(documentEntity.getExpirationDate())
                .type(DocumentType.fromJson(documentEntity.getType()))
                .status(DocumentStatus.fromJson(documentEntity.getStatus()))
                .build();
    }
    
    // TODO
    @SneakyThrows
    private Date calculateExpirationDate(String type, DocumentRequest documentRequest) {
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = Date.from(Instant.now());
        Calendar calendar = Calendar.getInstance();

        switch (type) {
            case "passport":
                Date birth = dateFormat.parse(new JsonConversion().convertData(documentRequest).getData().get("birthDate").asText());
                long diffInYears = TimeUnit.MILLISECONDS.toDays(now.getTime() - birth.getTime()) / 365L;
                calendar.setTime(birth);
                calendar.add(Calendar.DATE, +1);

                if (diffInYears >= 14 && diffInYears <= 20) {
                    calendar.add(Calendar.YEAR, +20);


                    return calendar.getTime();
                } else if (diffInYears >= 21 && diffInYears <= 45) {
                    calendar.add(Calendar.YEAR, +45);


                    return calendar.getTime();
                }
                return null;

            case "studetCard":
                Date issued = dateFormat.parse(new JsonConversion().convertData(documentRequest).getData().get("issueDate").asText());
                calendar.setTime(issued);
                calendar.add(Calendar.YEAR, +1);
                return calendar.getTime();

            case "driverLicense":
                calendar.setTime(dateFormat.parse(new JsonConversion().convertData(documentRequest).getData().get("expirationDate").asText()));
                return calendar.getTime();

            default:
                return null;
        }*/
        return null;
    }

    @Override
    public void creation(DocumentRequest documentRequest) {
        Long userId = 1L;

        DocumentValidator.validate(
                templateRepository.findByType(documentRequest.getType()).getStructure(),
                documentRequest.getData()
        );

        DocumentEntity documentEntity = new DocumentEntity();

        documentEntity.setTitle(documentRequest.getTitle());
        documentEntity.setData(documentRequest.toString());
        documentEntity.setCreationDate(Date.from(Instant.now()));
        documentEntity.setExpirationDate(calculateExpirationDate(documentRequest.getType(), documentRequest));
        documentEntity.setUserId(userId);
        documentEntity.setType(documentRequest.getType());
        documentEntity.setStatus("ACTIVE");
        documentRepository.save(documentEntity);
    }
}
