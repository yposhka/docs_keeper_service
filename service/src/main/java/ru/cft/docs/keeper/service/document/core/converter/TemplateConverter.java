package ru.cft.docs.keeper.service.document.core.converter;

import com.google.gson.Gson;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.cft.docs.keeper.service.document.core.model.Template;
import ru.cft.docs.keeper.service.document.core.model.TemplateElement;
import ru.cft.docs.keeper.service.document.core.repository.entity.TemplateEntity;

@Component
public class TemplateConverter implements Converter<TemplateEntity, Template> {
    @Override
    public Template convert(TemplateEntity templateEntity) {

        String jsonStringStructure = templateEntity.getStructure();

        TemplateElement[] elements = new Gson().fromJson(jsonStringStructure, TemplateElement[].class);

        return Template.builder()
                .name(templateEntity.getType())
                .structure(elements)
                .build();
    }
}
