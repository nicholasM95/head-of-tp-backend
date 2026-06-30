package be.nicholasmeyers.headoftp.device.usecase;

import be.nicholasmeyers.headoftp.device.projection.DeviceProjection;
import be.nicholasmeyers.headoftp.device.repository.DeviceLocationQueryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllDeviceIdsUseCaseTest {

    @InjectMocks
    private FindAllDeviceIdsUseCase findAllDeviceIdsUseCase;

    @Mock
    private DeviceLocationQueryRepository deviceLocationQueryRepository;

    @Nested
    class FindAllDeviceIds {
        @Test
        void given_whenFindAllDeviceIds_thenReturnListOfDevices() {
            // Given
            List<DeviceProjection> projections = List.of(new DeviceProjection("234", LocalDateTime.now()), new DeviceProjection("654", LocalDateTime.now()));
            when(deviceLocationQueryRepository.findAllDevices()).thenReturn(projections);

            // When
            List<DeviceProjection> result = findAllDeviceIdsUseCase.findAllDeviceIds();

            // Then
            assertThat(result).containsExactlyElementsOf(projections);
        }
    }
}
