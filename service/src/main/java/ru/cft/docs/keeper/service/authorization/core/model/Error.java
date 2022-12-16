package ru.cft.docs.keeper.service.authorization.core.model;

import lombok.Value;
import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;

@Value
public class Error {

    int code;

    String message;

    public Error(ErrorCode code, String message) {
        this.code = code.getCode();
        this.message = message;
    }
}