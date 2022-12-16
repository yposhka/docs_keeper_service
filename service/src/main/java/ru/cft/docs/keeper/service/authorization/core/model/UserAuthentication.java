package ru.cft.docs.keeper.service.authorization.core.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Date;

@Value
@RequiredArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
public class UserAuthentication {

    String token;

    Date expirationDate;
}
