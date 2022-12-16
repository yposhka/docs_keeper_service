package ru.cft.docs.keeper.service.document.core.model.enumeration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;
import ru.cft.docs.keeper.service.authorization.core.exception.ServiceException;

@Slf4j
@RequiredArgsConstructor
public enum DocumentType {
    PASSPORT("passport"),
    SNILS("snils"),
    INN("inn"),
    STUDENT_CARD("studetCard"),
    DRIVER_LICENSE("driverLicense");

    private final String name;

    @JsonSerialize
    public String toJson() {
        return name;
    }

    @JsonDeserialize
    public static DocumentType fromJson(String json) {
        for (var value : values()) {
            if (value.name.equalsIgnoreCase(json)) {
                return value;
            }
        }

        String message = String.format("The value is invalid. type = %s", json);
        log.error(message);
        throw new ServiceException(ErrorCode.INCORRECT_PARAMETER_VALUE, message);
    }
}
