package be.nicholasmeyers.headoftp.common.domain.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListValidatorTest {

    @Test
    public void givenFieldAndListOfStringsAndNotification_whenNotNull_thenNotificationIsEmpty() {
        // Given
        String field = "field";
        List<String> value = List.of("a", "b", "c");
        Notification notification = new Notification();

        // When
        ListValidator.notNull(field, value, notification);

        // Then
        assertThat(notification).isNotNull();
        assertThat(notification.getErrors()).isEmpty();
    }

    @Test
    public void givenFieldAndAndNotification_whenNotNull_thenNotificationHasErrors() {
        // Given
        String field = "field";
        List<String> value = null;
        Notification notification = new Notification();

        // When
        ListValidator.notNull(field, value, notification);

        // Then
        assertThat(notification).isNotNull();
        assertThat(notification.getErrors()).containsExactly(new Error("field.must.not.be.null", "field"));
    }

    @Test
    public void givenFieldAndListOfStringsAndNotification_whenNotEmpty_thenNotificationIsEmpty() {
        // Given
        String field = "field";
        List<String> value = List.of("a", "b", "c");
        Notification notification = new Notification();

        // When
        ListValidator.notEmpty(field, value, notification);

        // Then
        assertThat(notification).isNotNull();
        assertThat(notification.getErrors()).isEmpty();
    }

    @Test
    public void givenFieldAndEmptyListOfStringsAndNotification_whenNotEmpty_thenNotificationHasErrors() {
        // Given
        String field = "field";
        List<String> value = List.of();
        Notification notification = new Notification();

        // When
        ListValidator.notEmpty(field, value, notification);

        // Then
        assertThat(notification).isNotNull();
        assertThat(notification.getErrors()).containsExactly(new Error("field.must.not.be.empty", "field"));
    }
}
