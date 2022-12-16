package ru.cft.docs.keeper.service.document.core.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cft.docs.keeper.service.document.core.repository.entity.TemplateEntity;

public interface TemplateRepository extends CrudRepository<TemplateEntity, Long> {

    TemplateEntity findByType(String type);
}
