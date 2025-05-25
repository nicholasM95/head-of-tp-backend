package be.nicholasmeyers.headoftp.common.domain.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LongValidatorTest {

    @Test
    void givenNullFieldValue_whenNotBlank_thenAddErrorToNotification() {
        Notification notification = new Notification();
        LongValidator.notZero("myField", null, notification);

        assertThat(notification.hasErrors()).isTrue();
        assertThat(notification.getErrors())
                .containsExactly(new Error("field.must.not.be.null", "myField"));
    }

    @Test
    void givenZeroFieldValue_whenNotBlank_thenAddErrorToNotification() {
        Notification notification = new Notification();
        LongValidator.notZero("myField", 0L, notification);

        assertThat(notification.hasErrors()).isTrue();
        assertThat(notification.getErrors())
                .containsExactly(new Error("field.must.not.be.zero", "myField"));
    }

    @Test
    void givenNonZeroFieldValue_whenNotBlank_thenDoesNotAddErrorToNotification() {
        Notification notification = new Notification();
        LongValidator.notZero("myField", 1L, notification);

        assertThat(notification.hasErrors()).isFalse();
        assertThat(notification.getErrors()).isEmpty();
    }
}
