package be.nicholasmeyers.headoftp.common.domain.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectValidatorTest {

    @Test
    void givenNullFieldValue_whenNotNull_thenAddErrorToNotification() {
        Notification notification = new Notification();
        ObjectValidator.notNull("myField", null, notification);

        assertThat(notification.hasErrors()).isTrue();
        assertThat(notification.getErrors())
                .containsExactly(new Error("field.must.not.be.null", "myField"));
    }

    @Test
    void givenNonNullFieldValue_whenNotNull_thenDoesNotAddErrorToNotification() {
        Notification notification = new Notification();
        ObjectValidator.notNull("myField", "myValue", notification);

        assertThat(notification.hasErrors()).isFalse();
        assertThat(notification.getErrors()).isEmpty();
    }
}
