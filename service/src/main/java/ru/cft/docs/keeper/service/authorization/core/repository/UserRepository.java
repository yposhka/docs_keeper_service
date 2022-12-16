package ru.cft.docs.keeper.service.authorization.core.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cft.docs.keeper.service.authorization.core.repository.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);
}
