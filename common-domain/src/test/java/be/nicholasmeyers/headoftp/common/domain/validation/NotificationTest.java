package be.nicholasmeyers.headoftp.common.domain.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationTest {
    @Test
    void givenMultipleNotifications_whenMerge_returnMergedNotification() {
        Notification notification1 = Notification.of("veld.moet.true.zijn", "veld1");
        notification1.addError("veld.moet.false.zijn", "veld2");
        Notification notification2 = Notification.of("veld.mag.niet.leeg.zijn", "veld3");
        notification2.addError("lijst.mag.niet.leeg.zijn", "veld4");
        Notification notification3 = Notification.of("veld.mag.niet.zero.zijn", "veld5");

        Notification merged = Notification.merge(notification1, notification2, notification3);

        assertThat(merged.getErrors()).containsExactly(
                new Error("veld.moet.true.zijn", "veld1"),
                new Error("veld.moet.false.zijn", "veld2"),
                new Error("veld.mag.niet.leeg.zijn", "veld3"),
                new Error("lijst.mag.niet.leeg.zijn", "veld4"),
                new Error("veld.mag.niet.zero.zijn", "veld5")
        );
    }

    @Test
    void givenListOfNotifications_whenMerge_returnMergedNotification() {
        Notification notification1 = Notification.of("veld.moet.true.zijn", "veld1");
        notification1.addError("veld.moet.false.zijn", "veld2");
        Notification notification2 = Notification.of("veld.mag.niet.leeg.zijn", "veld3");
        notification2.addError("lijst.mag.niet.leeg.zijn", "veld4");
        Notification notification3 = Notification.of("veld.mag.niet.zero.zijn", "veld5");

        Notification merged = Notification.merge(List.of(notification1, notification2, notification3));

        assertThat(merged.getErrors()).containsExactly(
                new Error("veld.moet.true.zijn", "veld1"),
                new Error("veld.moet.false.zijn", "veld2"),
                new Error("veld.mag.niet.leeg.zijn", "veld3"),
                new Error("lijst.mag.niet.leeg.zijn", "veld4"),
                new Error("veld.mag.niet.zero.zijn", "veld5")
        );
    }
}
