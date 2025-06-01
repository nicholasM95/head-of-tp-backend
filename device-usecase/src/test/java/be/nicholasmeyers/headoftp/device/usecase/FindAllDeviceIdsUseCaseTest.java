package be.nicholasmeyers.headoftp.device.usecase;

import be.nicholasmeyers.headoftp.device.repository.DeviceLocationQueryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        void given_whenFindAllDeviceIds_thenReturnListOfDeviceIds() {
            // Given
            when(deviceLocationQueryRepository.findAllDeviceIds()).thenReturn(List.of("234", "654"));

            // When
            List<String> deviceIds = findAllDeviceIdsUseCase.findAllDeviceIds();

            // Then
            assertThat(deviceIds).containsExactly("234", "654");
        }
    }
}
