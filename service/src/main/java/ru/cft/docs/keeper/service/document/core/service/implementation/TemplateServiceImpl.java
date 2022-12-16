package ru.cft.docs.keeper.service.document.core.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;
import ru.cft.docs.keeper.service.authorization.core.exception.ServiceException;
import ru.cft.docs.keeper.service.document.core.converter.TemplateConverter;
import ru.cft.docs.keeper.service.document.core.model.Template;
import ru.cft.docs.keeper.service.document.core.repository.TemplateRepository;
import ru.cft.docs.keeper.service.document.core.repository.entity.TemplateEntity;
import ru.cft.docs.keeper.service.document.core.service.TemplateService;

import java.util.ArrayList;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    private final TemplateConverter templateConverter;

    @Override
    public ArrayList<String> getTemplates() {
        ArrayList<String> templates = new ArrayList<>();
        Iterable<TemplateEntity> templateEntities = templateRepository.findAll();

        if (templateEntities.equals(Collections.emptyList())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, "Types not found");
        }
        for (TemplateEntity templateEntity : templateEntities) {
            try {
                Template convertedTemplate = templateConverter.convert(templateEntity);
                templates.add(convertedTemplate != null ? convertedTemplate.getName() : null);
            } catch (NullPointerException e) {
                throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "Service is unavailable");
            }
        }
        return templates;
    }

    @Override
    public Template getTemplateByType(String type) {
        if (!StringUtils.hasText(type)) {
            throw new ServiceException(ErrorCode.INCORRECT_PARAMETER_VALUE, "Type is null");
        }

        TemplateEntity templateEntity = templateRepository.findByType(type);
        if (templateEntity == null) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, "Template not found");
        }
        return templateConverter.convert(templateEntity);
    }
}