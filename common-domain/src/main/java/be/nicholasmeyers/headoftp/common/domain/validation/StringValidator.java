package be.nicholasmeyers.headoftp.common.domain.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringValidator {

    public static void notBlank(String field, String value, Notification notification) {
        if (StringUtils.isBlank(value)) {
            notification.addError("field.must.not.be.empty", field);
        }
    }

    public static void notExceedingMaxLength(String field, String value, int maxLength, Notification notification) {
        if (nonNull(value) && value.length() > maxLength) {
            notification.addError("field.must.not.exceed.the.maximum.length", field, String.valueOf(maxLength));
        }
    }
}
