package ru.cft.docs.keeper.service.document.core.service;

import ru.cft.docs.keeper.service.document.core.model.DocumentFilter;
import ru.cft.docs.keeper.service.document.core.model.DocumentRequest;
import ru.cft.docs.keeper.service.document.core.model.DocumentTransfer;

import java.util.List;

public interface DocumentService {

    DocumentTransfer get(Long id);

    List<DocumentTransfer> get(DocumentFilter documentFilter);

	void creation(DocumentRequest documentRequest);
}
