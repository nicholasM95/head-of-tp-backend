package be.nicholasmeyers.headoftp.device.adapter.repository;

import be.nicholasmeyers.headoftp.device.projection.DeviceProjection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class DeviceLocationJdbcRepositoryTest {

    @Autowired
    private DeviceLocationJdbcRepository deviceLocationJdbcRepository;

    @Sql(value = "device.sql")
    @Nested
    class FindAllDevices {
        @Test
        void given_whenFindAllDevices_thenReturnListOfDevices() {
            // Given

            // When
            List<DeviceProjection> devices = deviceLocationJdbcRepository.findAllDevices();

            // Then
            assertThat(devices).containsExactly(
                    new DeviceProjection("device001", LocalDateTime.of(2025, 5, 29, 10, 2, 0)),
                    new DeviceProjection("device002", LocalDateTime.of(2025, 5, 29, 10, 1, 0))
            );
        }
    }

    @Sql(value = "device.sql")
    @Nested
    class FindDeviceById {
        @Test
        void givenExistingDeviceId_whenFindDeviceById_thenReturnDevice() {
            // Given

            // When
            Optional<DeviceProjection> device = deviceLocationJdbcRepository.findDeviceById("device001");

            // Then
            assertThat(device).contains(new DeviceProjection("device001", LocalDateTime.of(2025, 5, 29, 10, 2, 0)));
        }

        @Test
        void givenUnknownDeviceId_whenFindDeviceById_thenReturnEmpty() {
            // Given

            // When
            Optional<DeviceProjection> device = deviceLocationJdbcRepository.findDeviceById("unknown");

            // Then
            assertThat(device).isEmpty();
        }
    }
}
