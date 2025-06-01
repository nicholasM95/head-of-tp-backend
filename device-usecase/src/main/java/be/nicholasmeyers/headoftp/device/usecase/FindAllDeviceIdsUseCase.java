package be.nicholasmeyers.headoftp.device.usecase;

import be.nicholasmeyers.headoftp.device.repository.DeviceLocationQueryRepository;

import java.util.List;

public class FindAllDeviceIdsUseCase {

    private DeviceLocationQueryRepository deviceLocationQueryRepository;

    public List<String> findAllDeviceIds() {
        return deviceLocationQueryRepository.findAllDeviceIds();
    }
}
