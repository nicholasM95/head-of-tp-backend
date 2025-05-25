package be.nicholasmeyers.headoftp.common.domain.validation;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class StringValidatorTest {

    @Nested
    class NotBlank {
        @NullSource
        @ValueSource(strings = {"", " "})
        @ParameterizedTest
        void givenBlankFieldValue_thenAddErrorToNotification(String value) {
            // Given
            Notification notification = new Notification();
            // When
            StringValidator.notBlank("myField", value, notification);
            // Then
            assertThat(notification.hasErrors()).isTrue();
            assertThat(notification.getErrors())
                    .containsExactly(new Error("field.must.not.be.empty", "myField"));
        }

        @Test
        void givenNonBlankFieldValue_thenDoesNotAddErrorToNotification() {
            Notification notification = new Notification();
            StringValidator.notBlank("myField", "myValue", notification);

            assertThat(notification.hasErrors()).isFalse();
            assertThat(notification.getErrors()).isEmpty();
        }
    }

    @Nested
    class NotExceedingMaxLength {
        @Test
        void givenValueUnderMaxLength_thenDoNotAddErrorToNotification() {
            // Given
            Notification notification = new Notification();
            String name = RandomStringUtils.random(7);
            // When
            StringValidator.notExceedingMaxLength("myField", name, 10, notification);
            // Then
            assertThat(notification.hasErrors()).isFalse();
            assertThat(notification.getErrors()).isEmpty();
        }

        @Test
        void givenValueOfMaxLength_thenDoNotAddErrorToNotification() {
            // Given
            Notification notification = new Notification();
            String name = RandomStringUtils.random(10);
            // When
            StringValidator.notExceedingMaxLength("myField", name, 10, notification);
            // Then
            assertThat(notification.hasErrors()).isFalse();
            assertThat(notification.getErrors()).isEmpty();
        }

        @Test
        void givenValueAboveMaxLength_thenAddErrorToNotification() {
            // Given
            Notification notification = new Notification();
            String name = RandomStringUtils.random(11);
            // When
            StringValidator.notExceedingMaxLength("myField", name, 10, notification);
            // Then
            assertThat(notification.hasErrors()).isTrue();
            assertThat(notification.getErrors())
                    .containsExactly(new Error("field.must.not.exceed.the.maximum.length", "myField", "10"));
        }

        @Test
        void givenNull_thenAddErrorToNotification() {
            // Given
            Notification notification = new Notification();
            // When
            StringValidator.notExceedingMaxLength("myField", null, 10, notification);
            // Then
            assertThat(notification.hasErrors()).isFalse();
        }
    }
}
