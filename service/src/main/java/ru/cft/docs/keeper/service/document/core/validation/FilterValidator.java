package ru.cft.docs.keeper.service.document.core.validation;

import lombok.experimental.UtilityClass;
import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;
import ru.cft.docs.keeper.service.authorization.core.exception.ServiceException;

import java.time.Instant;
import java.util.Date;

@UtilityClass
public class FilterValidator {

    private static final Long BORDER_DATE = 7258118400000L;

    public static void checkSort(String parameterName, String sort) {
        if (!sort.equals("asc") && !sort.equals("desc")) {
            createException(sort ,parameterName);
        }
    }

    public static void checkCreationDate(String parameterName, Date date) {
        if (date != null && (date.getTime() > Date.from(Instant.now()).getTime() || date.getTime() < 0)) {
            createException(String.valueOf(date), parameterName);
        }
    }

    public static void checkExpirationDate(String parameterName, Date date) {
        if (date != null && (date.getTime() > BORDER_DATE || date.getTime() < 0)) {
            createException(String.valueOf(date), parameterName);
        }
    }

    private static void createException(String value, String parameterName) {
        throw new ServiceException(
                ErrorCode.INCORRECT_PARAMETER_VALUE, String.format("Parameter '%s' is invalid - %s", parameterName, value)
        );
    }
}
