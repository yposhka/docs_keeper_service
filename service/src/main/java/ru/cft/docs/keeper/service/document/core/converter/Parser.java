package ru.cft.docs.keeper.service.document.core.converter;

import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;
import ru.cft.docs.keeper.service.authorization.core.exception.ServiceException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parser {

    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public Date parserDate(String parameterName,String date) {
        try {
            if (date != null) {
                return formatter.parse(date);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ServiceException(
                    ErrorCode.INTERNAL_SERVER_ERROR, String.format("Error in formatting a date from string to Date - %s",parameterName)
            );
        }
    }
}
