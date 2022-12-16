package ru.cft.docs.keeper.service.document.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.cft.docs.keeper.service.document.core.converter.Parser;
import ru.cft.docs.keeper.service.document.core.model.DocumentFilter;
import ru.cft.docs.keeper.service.document.core.model.DocumentRequest;
import ru.cft.docs.keeper.service.document.core.model.DocumentTransfer;
import ru.cft.docs.keeper.service.document.core.model.Template;
import ru.cft.docs.keeper.service.document.core.model.enumeration.DocumentStatus;
import ru.cft.docs.keeper.service.document.core.model.enumeration.DocumentType;
import ru.cft.docs.keeper.service.document.core.service.DocumentService;
import ru.cft.docs.keeper.service.document.core.service.TemplateService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = Paths.DOCUMENTS)
@RequiredArgsConstructor
public class DocumentController {

	private final TemplateService templateService;

    private final DocumentService documentService;

    private static final Parser parser = new Parser();

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void create(DocumentRequest documentRequest) {
        DocumentType.fromJson(documentRequest.getType());
        documentService.creation(documentRequest);
    }

    @GetMapping(path = "/types")
    public List<String> getDocumentTemplates() {
        return templateService.getTemplates();
    }

    @GetMapping(path = "/types/{type}/template")//
    public Template getDocumentTemplateById(@PathVariable(value = "type") String type) {
        return templateService.getTemplateByType(type);
    }

    @GetMapping(path = Paths.ID)
    public DocumentTransfer getDocumentById(@PathVariable Long id) {
        return documentService.get(id);
    }

    @GetMapping()
    public List<DocumentTransfer> getDocuments(
            @RequestParam(value = "excludeData", defaultValue = "false") Boolean excludeData,
            @RequestParam(value = "sort", defaultValue = "asc") String sort,
            @RequestParam(value = "status", defaultValue = "active") String status,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "creationDateFrom", required = false) String creationDateFrom,
            @RequestParam(value = "creationDateTo", required = false) String creationDateTo,
            @RequestParam(value = "expirationDateFrom", required = false) String expirationDateFrom,
            @RequestParam(value = "expirationDateTo", required = false) String expirationDateTo) {

        return documentService.get(
                DocumentFilter.builder()
                        .excludeData(excludeData)
                        .sort(sort)
                        .status(DocumentStatus.fromJson(status))
                        .type(DocumentType.fromJson(type))
                        .creationDateFrom(parser.parserDate("creationDateFrom",creationDateFrom))
                        .creationDateTo(parser.parserDate("creationDateTo",creationDateTo))
                        .expirationDateFrom(parser.parserDate("expirationDateFrom",expirationDateFrom))
                        .expirationDateTo(parser.parserDate("expirationDateTo",expirationDateTo))
                        .build()
        );
    }
}
