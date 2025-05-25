package be.nicholasmeyers.headoftp.device.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import be.nicholasmeyers.headoftp.common.domain.validation.Error;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceLocationFactoryTest {

    @Nested
    class CreateDeviceLocation {
        @Test
        void givenCreateDeviceLocationRequest_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndEmptyNotification() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("728 923 ", 34335L, 45.5, 43.8,
                    90.1, 34.4, 13.9999, 88.0, 100.0);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("728923");
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(34335L);
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(45.5);
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(43.8);
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(90.1);
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(34.4);
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(13.9999);
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(88.0);
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(100.0);
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isFalse();
        }

        @Test
        void givenCreateDeviceLocationRequestWithoutDeviceId_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndNotificationError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest(null, 34335L, 45.5, 43.8,
                    90.1, 34.4, 13.9999, 88.0, 100.0);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isNull();
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(34335L);
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(45.5);
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(43.8);
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(90.1);
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(34.4);
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(13.9999);
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(88.0);
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(100.0);
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isTrue();
            assertThat(deviceLocation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.empty", "deviceId"));
        }

        @Test
        void givenCreateDeviceLocationRequestWithInvalideDeviceId_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndNotificationError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("67891231273182931231293", 34335L, 45.5, 43.8,
                    90.1, 34.4, 13.9999, 88.0, 100.0);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("67891231273182931231293");
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(34335L);
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(45.5);
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(43.8);
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(90.1);
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(34.4);
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(13.9999);
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(88.0);
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(100.0);
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isTrue();
            assertThat(deviceLocation.getNotification().getErrors()).containsExactly(new Error("field.must.not.exceed.the.maximum.length", "deviceId", "20"));
        }

        @Test
        void givenCreateDeviceLocationRequestWithoutTimestamp_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndNotificationError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("728923", null, 45.5, 43.8,
                    90.1, 34.4, 13.9999, 88.0, 100.0);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("728923");
            assertThat(deviceLocation.getValue().getTimestamp()).isNull();
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(45.5);
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(43.8);
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(90.1);
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(34.4);
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(13.9999);
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(88.0);
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(100.0);
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isTrue();
            assertThat(deviceLocation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "timestamp"));
        }

        @Test
        void givenCreateDeviceLocationRequestWithoutLatitude_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndNotificationError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("728923", 34335L, null, 43.8,
                    90.1, 34.4, 13.9999, 88.0, 100.0);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("728923");
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(34335L);
            assertThat(deviceLocation.getValue().getLatitude()).isNull();
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(43.8);
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(90.1);
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(34.4);
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(13.9999);
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(88.0);
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(100.0);
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isTrue();
            assertThat(deviceLocation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "latitude"));
        }

        @Test
        void givenCreateDeviceLocationRequestWithoutLongitude_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndNotificationError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("728923", 34335L, 45.5, null,
                    90.1, 34.4, 13.9999, 88.0, 100.0);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("728923");
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(34335L);
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(45.5);
            assertThat(deviceLocation.getValue().getLongitude()).isNull();
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(90.1);
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(34.4);
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(13.9999);
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(88.0);
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(100.0);
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isTrue();
            assertThat(deviceLocation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "longitude"));
        }

        @Test
        void givenCreateDeviceLocationRequestWithoutSpeed_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndNotificationError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("728923", 34335L, 45.5, 43.8,
                    null, 34.4, 13.9999, 88.0, 100.0);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("728923");
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(34335L);
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(45.5);
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(43.8);
            assertThat(deviceLocation.getValue().getSpeed()).isNull();
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(34.4);
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(13.9999);
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(88.0);
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(100.0);
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isTrue();
            assertThat(deviceLocation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "speed"));
        }

        @Test
        void givenCreateDeviceLocationRequestWithoutBearing_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndNotificationError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("728923", 34335L, 45.5, 43.8,
                    90.1, null, 13.9999, 88.0, 100.0);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("728923");
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(34335L);
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(45.5);
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(43.8);
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(90.1);
            assertThat(deviceLocation.getValue().getBearing()).isNull();
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(13.9999);
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(88.0);
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(100.0);
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isTrue();
            assertThat(deviceLocation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "bearing"));
        }

        @Test
        void givenCreateDeviceLocationRequestWithoutAltitude_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndNotificationError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("728923", 34335L, 45.5, 43.8,
                    90.1, 34.4, null, 88.0, 100.0);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("728923");
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(34335L);
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(45.5);
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(43.8);
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(90.1);
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(34.4);
            assertThat(deviceLocation.getValue().getAltitude()).isNull();
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(88.0);
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(100.0);
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isTrue();
            assertThat(deviceLocation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "altitude"));
        }

        @Test
        void givenCreateDeviceLocationRequestWithoutAccuracy_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndNotificationError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("728923", 34335L, 45.5, 43.8,
                    90.1, 34.4, 13.9999, null, 100.0);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("728923");
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(34335L);
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(45.5);
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(43.8);
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(90.1);
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(34.4);
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(13.9999);
            assertThat(deviceLocation.getValue().getAccuracy()).isNull();
            assertThat(deviceLocation.getValue().getBattery()).isEqualTo(100.0);
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isTrue();
            assertThat(deviceLocation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "accuracy"));
        }

        @Test
        void givenCreateDeviceLocationRequestWithoutBattery_whenCreateDeviceLocation_thenReturnCreationOfDeviceLocationAndNotificationError() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("728923", 34335L, 45.5, 43.8,
                    90.1, 34.4, 13.9999, 88.0, null);

            // When
            Creation<DeviceLocation> deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest);

            // Then
            assertThat(deviceLocation).isNotNull();
            assertThat(deviceLocation.getValue()).isNotNull();
            assertThat(deviceLocation.getValue().getDeviceId()).isEqualTo("728923");
            assertThat(deviceLocation.getValue().getTimestamp()).isEqualTo(34335L);
            assertThat(deviceLocation.getValue().getLatitude()).isEqualTo(45.5);
            assertThat(deviceLocation.getValue().getLongitude()).isEqualTo(43.8);
            assertThat(deviceLocation.getValue().getSpeed()).isEqualTo(90.1);
            assertThat(deviceLocation.getValue().getBearing()).isEqualTo(34.4);
            assertThat(deviceLocation.getValue().getAltitude()).isEqualTo(13.9999);
            assertThat(deviceLocation.getValue().getAccuracy()).isEqualTo(88.0);
            assertThat(deviceLocation.getValue().getBattery()).isNull();
            assertThat(deviceLocation.getNotification()).isNotNull();
            assertThat(deviceLocation.getNotification().hasErrors()).isTrue();
            assertThat(deviceLocation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "battery"));
        }
    }
}
