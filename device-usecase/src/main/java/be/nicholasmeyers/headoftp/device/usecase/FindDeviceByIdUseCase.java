package be.nicholasmeyers.headoftp.device.usecase;

import be.nicholasmeyers.headoftp.device.projection.DeviceProjection;
import be.nicholasmeyers.headoftp.device.repository.DeviceLocationQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class FindDeviceByIdUseCase {

    private final DeviceLocationQueryRepository deviceLocationQueryRepository;

    public Optional<DeviceProjection> findDeviceById(String deviceId) {
        log.info("Finding device by id: {}", deviceId);
        return deviceLocationQueryRepository.findDeviceById(deviceId);
    }
}
