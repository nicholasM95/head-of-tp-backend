package be.nicholasmeyers.headoftp.device.usecase;

import be.nicholasmeyers.headoftp.device.projection.DeviceProjection;
import be.nicholasmeyers.headoftp.device.repository.DeviceLocationQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class FindAllDeviceUseCase {

    private final DeviceLocationQueryRepository deviceLocationQueryRepository;

    public List<DeviceProjection> findAllDevice() {
        log.info("Finding all device ids");
        return deviceLocationQueryRepository.findAllDevices();
    }
}
