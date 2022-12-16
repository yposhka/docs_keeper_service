package ru.cft.docs.keeper.service.authorization.core.validation;

import lombok.experimental.UtilityClass;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;
import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;
import ru.cft.docs.keeper.service.authorization.core.exception.ServiceException;

@UtilityClass
public class AuthenticationValidator {

    final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&!+=])(?=\\S+$).{8,}$";

    final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();

    public static void checkEmail(String parameterName, String email) {
        if (!EMAIL_VALIDATOR.isValid(email) || !StringUtils.hasText(email)) {
            createException(parameterName);
        }
    }

    public static void checkPassword(String parameterName, String password) {
        if (!password.matches(PASSWORD_PATTERN) || !StringUtils.hasText(password)) {
            createException(parameterName);
        }
    }

    public static void checkDeviceId(String parameterName, String deviceId) {
        if (!StringUtils.hasText(deviceId)) {
            createException(parameterName);
        }
    }

    private void createException(String parameterName) {
        throw new ServiceException(
                ErrorCode.INCORRECT_PARAMETER_VALUE, String.format("Parameter '%s' is invalid", parameterName)
        );
    }
}
