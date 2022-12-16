package ru.cft.docs.keeper.service.authorization.core.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
public class UserAuthenticationRequest {

    String login;

    String password;

    String deviceId;
}
