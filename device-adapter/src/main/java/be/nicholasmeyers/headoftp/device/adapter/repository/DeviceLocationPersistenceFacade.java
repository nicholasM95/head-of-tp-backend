package be.nicholasmeyers.headoftp.device.adapter.repository;

import be.nicholasmeyers.headoftp.device.domain.DeviceLocation;
import be.nicholasmeyers.headoftp.device.repository.DeviceLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeviceLocationPersistenceFacade implements DeviceLocationRepository {

    private final DeviceLocationJpaRepository deviceLocationJpaRepository;

    @Override
    public void createDeviceLocation(DeviceLocation deviceLocation) {
        deviceLocationJpaRepository.saveAndFlush(new DeviceLocationJpaEntity(deviceLocation));
    }
}
