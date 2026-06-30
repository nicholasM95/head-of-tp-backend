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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindDeviceByIdUseCaseTest {

    @InjectMocks
    private FindDeviceByIdUseCase findDeviceByIdUseCase;

    @Mock
    private DeviceLocationQueryRepository deviceLocationQueryRepository;

    @Nested
    class FindDeviceById {
        @Test
        void givenExistingDeviceId_whenFindDeviceById_thenReturnDevice() {
            // Given
            DeviceProjection projection = new DeviceProjection("234", LocalDateTime.of(2025, 5, 29, 10, 2, 0));
            when(deviceLocationQueryRepository.findDeviceById("234")).thenReturn(Optional.of(projection));

            // When
            Optional<DeviceProjection> result = findDeviceByIdUseCase.findDeviceById("234");

            // Then
            assertThat(result).contains(projection);
        }

        @Test
        void givenUnknownDeviceId_whenFindDeviceById_thenReturnEmpty() {
            // Given
            when(deviceLocationQueryRepository.findDeviceById("unknown")).thenReturn(Optional.empty());

            // When
            Optional<DeviceProjection> result = findDeviceByIdUseCase.findDeviceById("unknown");

            // Then
            assertThat(result).isEmpty();
        }
    }
}
