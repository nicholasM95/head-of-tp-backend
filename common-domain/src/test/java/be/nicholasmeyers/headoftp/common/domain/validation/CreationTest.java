package be.nicholasmeyers.headoftp.common.domain.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreationTest {

    @Test
    void givenCreatedValueAndNotification_whenOf_thenACreationIsConstructedWithValueAndNotification() {
        Notification notification = new Notification();
        String value = "myValue";

        assertThat(Creation.of(value, notification))
                .extracting(Creation::getValue, Creation::getNotification)
                .containsExactly(value, notification);
    }

}
