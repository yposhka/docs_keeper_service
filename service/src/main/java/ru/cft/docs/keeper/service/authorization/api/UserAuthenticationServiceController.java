package ru.cft.docs.keeper.service.authorization.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.docs.keeper.service.authorization.core.model.UserAuthentication;
import ru.cft.docs.keeper.service.authorization.core.model.UserAuthenticationRequest;
import ru.cft.docs.keeper.service.authorization.core.service.UserAuthenticationService;

@RestController
@RequestMapping(path = Paths.USERS + Paths.AUTHENTICATION)
@RequiredArgsConstructor
public class UserAuthenticationServiceController {

    private final UserAuthenticationService userAuthenticationService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public UserAuthentication authenticate(UserAuthenticationRequest userAuthenticationRequest) {
        return userAuthenticationService.authentication(userAuthenticationRequest);
    }
}
