package be.nicholasmeyers.headoftp.device.repository;

import java.util.List;

public interface DeviceLocationQueryRepository {
    List<String> findAllDeviceIds();
}
