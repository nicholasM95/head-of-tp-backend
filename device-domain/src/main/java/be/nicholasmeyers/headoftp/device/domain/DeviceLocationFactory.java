package be.nicholasmeyers.headoftp.device.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeviceLocationFactory {

    public static Creation<DeviceLocation> createDeviceLocation(CreateDeviceLocationRequest createDeviceLocationRequest) {
        DeviceLocation deviceLocation = new DeviceLocation(createDeviceLocationRequest);
        return Creation.of(deviceLocation, deviceLocation.validate());
    }
}
