package ru.cft.docs.keeper.service.authorization.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(1),
    SERVICE_UNAVAILABLE(2),
    INCORRECT_PARAMETER_VALUE(3),
    OBJECT_NOT_FOUND(4),
    SESSION_EXPIRED(5),
    TOKEN_INVALID(6);

    private final int code;
}
