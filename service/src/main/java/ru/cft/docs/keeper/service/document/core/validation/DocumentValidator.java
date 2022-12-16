package ru.cft.docs.keeper.service.document.core.validation;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;
import ru.cft.docs.keeper.service.authorization.core.exception.ServiceException;
import ru.cft.docs.keeper.service.document.core.model.DataField;
import ru.cft.docs.keeper.service.document.core.model.TemplateElement;

import java.util.regex.Pattern;

@Component
public class DocumentValidator {

    public static void validate(String template, DataField[] dataFields) {

        TemplateElement[] templateElements =
                new Gson().fromJson(template, TemplateElement[].class);

        for (DataField currentDataField : dataFields) {
            String name = currentDataField.getName();
            String content = currentDataField.getContent();
            String regex = getRegexByName(templateElements, name);

            boolean isValid = Pattern.matches(regex, content);

            if (!isValid) {
                throw new ServiceException(
                        ErrorCode.INCORRECT_PARAMETER_VALUE,
                        "The added document does not match the specified template - data"
                );
            }
        }
    }

    private static String getRegexByName(TemplateElement[] templateElements, String name) {
        for (TemplateElement templateElement : templateElements) {
            if (templateElement.getName().equals(name)) {
                return templateElement.getRegularExpression();
            }
        }
        throw new ServiceException(
                ErrorCode.INCORRECT_PARAMETER_VALUE,
                "The added document does not match the specified template - name"
        );
    }
}
