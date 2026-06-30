package be.nicholasmeyers.headoftp.device.repository;

import be.nicholasmeyers.headoftp.device.projection.DeviceProjection;

import java.util.List;

public interface DeviceLocationQueryRepository {
    List<DeviceProjection> findAllDevices();
}
