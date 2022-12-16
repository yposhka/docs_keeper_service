package ru.cft.docs.keeper.service.authorization.core.service;

import ru.cft.docs.keeper.service.authorization.core.model.UserAuthentication;
import ru.cft.docs.keeper.service.authorization.core.model.UserAuthenticationRequest;

public interface UserAuthenticationService {

    UserAuthentication authentication(UserAuthenticationRequest userAuthenticationRequest);
}
