package be.nicholasmeyers.headoftp.common.domain.validation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Creation<T> {

    private final T value;
    private final Notification notification;

    public static <T> Creation<T> of(T value, Notification notification) {
        return new Creation<>(value, notification);
    }

    /*public static <T> Creation<T> of(Notification notification) {
        return new Creation<>(null, notification);
    }*/
}
