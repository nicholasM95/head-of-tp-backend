package be.nicholasmeyers.headoftp.common.domain.validation;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ObjectValidator {

    public static Boolean notNull(String field, Object value, Notification notification) {
        if (value == null) {
            notification.addError("field.must.not.be.null", field);
            return false;
        }
        return true;
    }
}
