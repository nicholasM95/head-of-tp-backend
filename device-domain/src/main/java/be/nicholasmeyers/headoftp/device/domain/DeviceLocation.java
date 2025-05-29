package be.nicholasmeyers.headoftp.device.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.DoubleValidator;
import be.nicholasmeyers.headoftp.common.domain.validation.LongValidator;
import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.common.domain.validation.ObjectValidator;
import be.nicholasmeyers.headoftp.common.domain.validation.StringValidator;
import lombok.Getter;

import java.util.Optional;

@Getter
public class DeviceLocation {

    private final String deviceId;
    private final Long timestamp;
    private final Double latitude;
    private final Double longitude;
    private final Double speed;
    private final Double bearing;
    private final Double altitude;
    private final Double accuracy;
    private final Double battery;

    protected DeviceLocation(CreateDeviceLocationRequest createDeviceLocationRequest) {
        this.deviceId = Optional.ofNullable(createDeviceLocationRequest.deviceId())
                .map(s -> s.replaceAll(" ", "").toUpperCase())
                .orElse(null);
        this.timestamp = createDeviceLocationRequest.timestamp();
        this.latitude = createDeviceLocationRequest.latitude();
        this.longitude = createDeviceLocationRequest.longitude();
        this.speed = createDeviceLocationRequest.speed();
        this.bearing = createDeviceLocationRequest.bearing();
        this.altitude = createDeviceLocationRequest.altitude();
        this.accuracy = createDeviceLocationRequest.accuracy();
        this.battery = createDeviceLocationRequest.battery();
    }

    protected Notification validate() {
        Notification notification = new Notification();
        StringValidator.notBlank("deviceId", deviceId, notification);
        StringValidator.notExceedingMaxLength("deviceId", deviceId, 20, notification);
        LongValidator.notZero("timestamp", timestamp, notification);
        DoubleValidator.notZero("latitude", latitude, notification);
        DoubleValidator.notZero("longitude", longitude, notification);
        ObjectValidator.notNull("speed", speed, notification);
        ObjectValidator.notNull("bearing", bearing, notification);
        DoubleValidator.notZero("altitude", altitude, notification);
        DoubleValidator.notZero("accuracy", accuracy, notification);
        DoubleValidator.notZero("battery", battery, notification);

        return notification;
    }
}