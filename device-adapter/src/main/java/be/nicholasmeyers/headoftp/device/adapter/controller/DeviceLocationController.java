package be.nicholasmeyers.headoftp.device.adapter.controller;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.device.domain.CreateDeviceLocationRequest;
import be.nicholasmeyers.headoftp.device.usecase.CreateDeviceLocationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin(origins = {"https://headoftp.com", "http://localhost:4200"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class DeviceLocationController implements DeviceApi {

    private final CreateDeviceLocationUseCase  createDeviceLocationUseCase;

    @Override
    public ResponseEntity<Void> createDeviceLocation(String id, String timestamp, String lat, String lon, String speed, String bearing,
                                                     String altitude, String accuracy, String batt) {
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
}
