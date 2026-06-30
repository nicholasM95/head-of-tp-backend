package be.nicholasmeyers.headoftp.device.repository;

import be.nicholasmeyers.headoftp.device.projection.DeviceProjection;

import java.util.List;
import java.util.Optional;

public interface DeviceLocationQueryRepository {
    List<DeviceProjection> findAllDevices();

    Optional<DeviceProjection> findDeviceById(String deviceId);
}
