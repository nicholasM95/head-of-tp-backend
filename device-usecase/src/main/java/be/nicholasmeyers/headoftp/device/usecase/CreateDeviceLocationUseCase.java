package be.nicholasmeyers.headoftp.device.usecase;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.device.domain.CreateDeviceLocationRequest;
import be.nicholasmeyers.headoftp.device.domain.DeviceLocation;
import be.nicholasmeyers.headoftp.device.domain.DeviceLocationFactory;
import be.nicholasmeyers.headoftp.device.repository.DeviceLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CreateDeviceLocationUseCase {

    private final DeviceLocationRepository deviceLocationRepository;

    public Notification createDeviceLocation(CreateDeviceLocationRequest createDeviceLocationRequest) {
        log.info("Creating device location for device id {}", createDeviceLocationRequest.deviceId());
        Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

        if (!deviceLocation.getNotification().hasErrors()) {
            deviceLocationRepository.createDeviceLocation(deviceLocation.getValue());
        }

        return deviceLocation.getNotification();
    }
}
