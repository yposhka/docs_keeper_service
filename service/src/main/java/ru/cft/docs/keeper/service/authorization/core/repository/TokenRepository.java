package ru.cft.docs.keeper.service.authorization.core.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cft.docs.keeper.service.authorization.core.repository.entity.TokenEntity;

public interface TokenRepository extends CrudRepository<TokenEntity, String> {
    TokenEntity findByToken(String token);
}
