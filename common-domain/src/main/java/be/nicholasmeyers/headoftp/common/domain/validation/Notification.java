package be.nicholasmeyers.headoftp.common.domain.validation;

import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
public class Notification {

    private final Set<Error> errors = new LinkedHashSet<>();

    public void addError(String error, String... parameters) {
        this.errors.add(new Error(error, parameters));
    }

    public Notification addNotification(Notification notification) {
        if (notification.hasErrors()) {
            errors.addAll(notification.getErrors());
        }
        return this;
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public static Notification empty() {
        return new Notification();
    }

    public static Notification of(String error) {
        Notification notification = empty();
        notification.addError(error);
        return notification;
    }

    public static Notification of(String error, Object ... parameters) {
        Notification notification = empty();
        notification.addError(error, Arrays.stream(parameters).map(String::valueOf).toArray(String[]::new));
        return notification;
    }


    public static Notification merge(Notification ... notifications) {
        Notification newNotification = empty();
        Arrays.stream(notifications).forEach(notification -> newNotification.getErrors().addAll(notification.getErrors()));
        return newNotification;
    }

    public static Notification merge(List<Notification> notifications) {
        Notification newNotification = empty();
        notifications.forEach(notification -> newNotification.getErrors().addAll(notification.getErrors()));
        return newNotification;
    }
}

