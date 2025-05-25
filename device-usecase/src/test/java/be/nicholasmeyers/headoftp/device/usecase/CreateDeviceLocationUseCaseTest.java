package be.nicholasmeyers.headoftp.device.usecase;

import be.nicholasmeyers.headoftp.common.domain.validation.Error;
import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.device.domain.CreateDeviceLocationRequest;
import be.nicholasmeyers.headoftp.device.domain.DeviceLocation;
import be.nicholasmeyers.headoftp.device.repository.DeviceLocationRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class CreateDeviceLocationUseCaseTest {

    @InjectMocks
    private CreateDeviceLocationUseCase createDeviceLocationUseCase;

    @Mock
    private DeviceLocationRepository deviceLocationRepository;

    @Nested
    class CreateDeviceLocation {
        @Test
        void givenCreateDeviceLocationRequest_whenCreateDeviceLocation_thenReturnNotificationWithoutError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("8946", 952558L, 58.0, 99.3,
                    88.5, 10.3, 11.6, 99.2, 99.0);

            // When
            Notification notification = createDeviceLocationUseCase.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(notification.hasErrors()).isFalse();
            ArgumentCaptor<DeviceLocation> deviceLocation = ArgumentCaptor.forClass(DeviceLocation.class);
            verify(deviceLocationRepository).createDeviceLocation(deviceLocation.capture());

            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("8946");
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(952558L);
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(58.0);
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(99.3);
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(88.5);
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(10.3);
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(11.6);
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(99.2);
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(99.0);

        }

        @Test
        void givenInvalidCreateDeviceLocationRequest_whenCreateDeviceLocation_thenReturnNotificationWithError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("", 952558L, 58.0, 99.3,
                    88.5, 10.3, 11.6, 99.2, 99.0);

            // When
            Notification notification = createDeviceLocationUseCase.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(notification.hasErrors()).isTrue();
            assertThat(notification.getErrors()).containsExactly(new Error("field.must.not.be.empty", "deviceId"));
            verifyNoInteractions(deviceLocationRepository);
        }
    }
}
