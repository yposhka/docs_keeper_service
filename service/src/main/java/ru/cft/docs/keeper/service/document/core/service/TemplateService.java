package ru.cft.docs.keeper.service.document.core.service;

import ru.cft.docs.keeper.service.document.core.model.Template;

import java.util.ArrayList;

public interface TemplateService {

    Template getTemplateByType(String type);

    ArrayList<String> getTemplates();
}
