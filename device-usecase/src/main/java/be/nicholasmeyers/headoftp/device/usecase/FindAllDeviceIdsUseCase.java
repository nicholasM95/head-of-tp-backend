package be.nicholasmeyers.headoftp.device.usecase;

import be.nicholasmeyers.headoftp.device.repository.DeviceLocationQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class FindAllDeviceIdsUseCase {

    private DeviceLocationQueryRepository deviceLocationQueryRepository;

    public List<String> findAllDeviceIds() {
        log.info("Finding all device ids");
        return deviceLocationQueryRepository.findAllDeviceIds();
    }
}
