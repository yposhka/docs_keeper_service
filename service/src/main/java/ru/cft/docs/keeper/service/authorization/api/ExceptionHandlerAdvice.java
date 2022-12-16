package ru.cft.docs.keeper.service.authorization.api;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;
import ru.cft.docs.keeper.service.authorization.core.exception.ServiceException;
import ru.cft.docs.keeper.service.authorization.core.model.Error;

import java.util.EnumMap;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private static final EnumMap<ErrorCode, HttpStatus> HTTP_STATUSES = new EnumMap<>(ErrorCode.class);

    static {
        HTTP_STATUSES.put(ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        HTTP_STATUSES.put(ErrorCode.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE);
        HTTP_STATUSES.put(ErrorCode.INCORRECT_PARAMETER_VALUE, HttpStatus.BAD_REQUEST);
        HTTP_STATUSES.put(ErrorCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
        HTTP_STATUSES.put(ErrorCode.SESSION_EXPIRED, HttpStatus.UNAUTHORIZED);
        HTTP_STATUSES.put(ErrorCode.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Error> handleException(ServiceException e) {
        logException(e);
        return ResponseEntity.status(HTTP_STATUSES.getOrDefault(e.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR))
                .body(new Error(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception e) {
        logException(e);
        if (e.getCause() instanceof JDBCException) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new Error(ErrorCode.SERVICE_UNAVAILABLE, "Service is unavailable"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(ErrorCode.INTERNAL_SERVER_ERROR, "Internal error"));
    }

    private void logException(Exception e) {
        log.error("Exception occurred: ", e);
    }
}
