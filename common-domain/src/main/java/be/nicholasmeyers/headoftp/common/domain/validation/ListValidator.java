package be.nicholasmeyers.headoftp.common.domain.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListValidator {

    public static void notNull(String field, List<?> value, Notification notification) {
        ObjectValidator.notNull(field, value, notification);
    }

    public static void notEmpty(String field, List<?> value, Notification notification) {
        ObjectValidator.notNull(field, value, notification);
        if (value != null && value.isEmpty()){
            notification.addError("field.must.not.be.empty", field);
        }
    }
}
