package be.nicholasmeyers.headoftp.device.adapter.controller;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.device.adapter.resource.CreateDeviceLocationRequestResource;
import be.nicholasmeyers.headoftp.device.adapter.resource.CreateDeviceLocationV2RequestResource;
import be.nicholasmeyers.headoftp.device.adapter.resource.DeviceResponseResource;
import be.nicholasmeyers.headoftp.device.domain.CreateDeviceLocationRequest;
import be.nicholasmeyers.headoftp.device.projection.DeviceProjection;
import be.nicholasmeyers.headoftp.device.usecase.CreateDeviceLocationUseCase;
import be.nicholasmeyers.headoftp.device.usecase.FindAllDeviceIdsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"https://headoftp.com", "http://localhost:5173"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class DeviceController implements DeviceApi {

    private static final int MAX_ID_LENGTH = 20;
    private static final ZoneId ZONE_ID_BRUSSELS = ZoneId.of("Europe/Brussels");

    private final CreateDeviceLocationUseCase  createDeviceLocationUseCase;
    private final FindAllDeviceIdsUseCase findAllDeviceIdsUseCase;

    @PostMapping(value = "/device", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Void> createDeviceLocationNotificationToken(@RequestParam("id") String id,
                                                                      @RequestParam("notificationToken") String notificationToken) {
        log.info("received request to create device location notification token: {}", id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createDeviceLocation(CreateDeviceLocationRequestResource createDeviceLocationRequestResource) {
        log.info("Received device location request");
        String id = createDeviceLocationRequestResource.getDeviceId();
        if (id != null && id.length() > MAX_ID_LENGTH) {
            id = id.substring(0, MAX_ID_LENGTH);
        }

        Instant instant = Instant.parse(createDeviceLocationRequestResource.getLocation().getTimestamp());
        Long timestampLong = instant.toEpochMilli();
        Double latitude = createDeviceLocationRequestResource.getLocation().getCoords().getLatitude();
        Double longitude = createDeviceLocationRequestResource.getLocation().getCoords().getLongitude();
        Double speedDouble = createDeviceLocationRequestResource.getLocation().getCoords().getSpeed();
        Double bearingDouble = 0.0;
        Double altitudeDouble = createDeviceLocationRequestResource.getLocation().getCoords().getAltitude();
        Double accuracyDouble = createDeviceLocationRequestResource.getLocation().getCoords().getAccuracy();
        Double batteryDouble = createDeviceLocationRequestResource.getLocation().getBattery().getLevel();

        CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest(id, timestampLong, latitude, longitude, speedDouble,
                bearingDouble, altitudeDouble, accuracyDouble, batteryDouble);

        Notification notification = createDeviceLocationUseCase.createDeviceLocation(createDeviceLocationRequest);
        if (notification.hasErrors()) {
            log.error(notification.getErrors().toString());
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createDeviceLocationOld(String id, String timestamp, String lat, String lon, String speed, String bearing,
                                                        String altitude, String accuracy, String batt) {

        log.warn("Received old device location request");
        if (id != null && id.length() > MAX_ID_LENGTH) {
            id = id.substring(0, MAX_ID_LENGTH);
        }
        Long timestampLong = Optional.ofNullable(timestamp)
                .map(Long::valueOf)
                .orElse(null);
        Double latitude = Optional.ofNullable(lat)
                .map(Double::valueOf)
                .orElse(null);
        Double longitude = Optional.ofNullable(lon)
                .map(Double::valueOf)
                .orElse(null);
        Double speedDouble = Optional.ofNullable(speed)
                .map(Double::valueOf)
                .orElse(null);
        Double bearingDouble = Optional.ofNullable(bearing)
                .map(Double::valueOf)
                .orElse(null);
        Double altitudeDouble = Optional.ofNullable(altitude)
                .map(Double::valueOf)
                .orElse(null);
        Double accuracyDouble = Optional.ofNullable(accuracy)
                .map(Double::valueOf)
                .orElse(null);
        Double batteryDouble = Optional.ofNullable(batt)
                .map(Double::valueOf)
                .orElse(null);

        CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest(id, timestampLong, latitude, longitude, speedDouble,
                bearingDouble, altitudeDouble, accuracyDouble, batteryDouble);

        Notification notification = createDeviceLocationUseCase.createDeviceLocation(createDeviceLocationRequest);
        if (notification.hasErrors()) {
            log.error(notification.getErrors().toString());
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createDeviceLocationV2(CreateDeviceLocationV2RequestResource createDeviceLocationV2RequestResource) {
        String deviceId = createDeviceLocationV2RequestResource.getDeviceId();
        Instant instant = Instant.parse(createDeviceLocationV2RequestResource.getLocation().getTimestamp());
        Long timestampLong = instant.toEpochMilli();
        Double latitude = createDeviceLocationV2RequestResource.getLocation().getCoords().getLatitude();
        Double longitude = createDeviceLocationV2RequestResource.getLocation().getCoords().getLongitude();
        Double speed = createDeviceLocationV2RequestResource.getLocation().getCoords().getSpeed();
        Double heading = createDeviceLocationV2RequestResource.getLocation().getCoords().getBearing();
        Double altitude = createDeviceLocationV2RequestResource.getLocation().getCoords().getAltitude();
        Double accuracy = createDeviceLocationV2RequestResource.getLocation().getCoords().getAccuracy();
        Double battery = createDeviceLocationV2RequestResource.getBattery();

        CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest(deviceId, timestampLong, latitude, longitude, speed,
                heading, altitude, accuracy, battery);

        Notification notification = createDeviceLocationUseCase.createDeviceLocation(createDeviceLocationRequest);
        if (notification.hasErrors()) {
            log.error(notification.getErrors().toString());
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<DeviceResponseResource>> findAllDevices() {
        return ResponseEntity.ok(findAllDeviceIdsUseCase.findAllDeviceIds().stream().map(this::toDeviceResponseResource).toList());
    }

    private DeviceResponseResource toDeviceResponseResource(DeviceProjection projection) {
        OffsetDateTime lastModifiedDateLocation = projection.lastModifiedDateLocation().atZone(ZONE_ID_BRUSSELS).toOffsetDateTime();
        return new DeviceResponseResource(projection.deviceId(), lastModifiedDateLocation.toLocalDateTime());
    }
}
