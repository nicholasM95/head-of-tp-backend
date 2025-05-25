package be.nicholasmeyers.headoftp.common.domain.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DoubleValidator {

    public static void notZero(String field, Double value, Notification notification) {
        ObjectValidator.notNull(field, value, notification);
        if (value != null && value == 0) {
            notification.addError("field.must.not.be.zero", field);
        }
    }
}
