package ru.cft.docs.keeper.service.document.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.cft.docs.keeper.service.document.core.model.DocumentFilter;
import ru.cft.docs.keeper.service.document.core.repository.entity.DocumentEntity;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends CrudRepository<DocumentEntity, Long> {

    @Query(
            value = "select new DocumentEntity (d.id, d.userId, d.title, case when :#{#documentFilter.excludeData} = false then '' else d.data end, d.creationDate, d.expirationDate, d.type, d.status) " +
                    "from DocumentEntity d " +
                    "where d.userId = :#{#documentFilter.userId} " +
                    "and d.status = :#{#documentFilter.status.toJson()} " +
                    "and d.type = :#{#documentFilter.type.toJson()} OR :#{#documentFilter.type.toJson()} is null " +
                    "and :#{#documentFilter.creationDateFrom == null} = true or d.creationDate <= :#{#documentFilter.creationDateFrom} " +
                    "and :#{#documentFilter.creationDateTo == null} = true or d.creationDate <= :#{#documentFilter.creationDateTo} " +
                    "and :#{#documentFilter.expirationDateFrom == null} = true or d.creationDate <= :#{#documentFilter.expirationDateFrom} " +
                    "and :#{#documentFilter.expirationDateTo == null} = true or d.creationDate <= :#{#documentFilter.expirationDateTo} " +
                    "order by d.title desc"
    )
    List<DocumentEntity> getListDesc(DocumentFilter documentFilter);

    @Query(
            value = "select new DocumentEntity (d.id, d.userId, d.title, case when :#{#documentFilter.excludeData} = false then '' else d.data end, d.creationDate, d.expirationDate, d.type, d.status) " +
                    "from DocumentEntity d " +
                    "where d.userId = :#{#documentFilter.userId} " +
                    "and d.status = :#{#documentFilter.status.toJson()} " +
                    "and :#{#documentFilter.type.toJson()} is null OR d.type = :#{#documentFilter.type.toJson()} " +
                    "and :#{#documentFilter.creationDateFrom == null} = true or d.creationDate <= :#{#documentFilter.creationDateFrom} " +
                    "and :#{#documentFilter.creationDateTo == null} = true or d.creationDate <= :#{#documentFilter.creationDateTo} " +
                    "and :#{#documentFilter.expirationDateFrom == null} = true or d.creationDate <= :#{#documentFilter.expirationDateFrom} " +
                    "and :#{#documentFilter.expirationDateTo == null} = true or d.creationDate <= :#{#documentFilter.expirationDateTo} " +
                    "order by d.title asc"
    )
    List<DocumentEntity> getListAsc(DocumentFilter documentFilter);

    Optional<DocumentEntity> findByIdAndUserId(Long id, Long userId);
}
