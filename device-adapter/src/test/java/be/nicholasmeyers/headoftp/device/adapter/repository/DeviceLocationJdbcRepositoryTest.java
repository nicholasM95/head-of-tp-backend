package be.nicholasmeyers.headoftp.device.adapter.repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class DeviceLocationJdbcRepositoryTest {

    @Autowired
    private DeviceLocationJdbcRepository deviceLocationJdbcRepository;

    @Sql(value = "device.sql")
    @Nested
    class FindAllDeviceIds {
        @Test
        void given_whenFindAllDeviceIds_thenReturnListOfDevices() {
            // Given

            // When
            List<String> devices = deviceLocationJdbcRepository.findAllDeviceIds();

            // Then
            assertThat(devices).containsExactly("device001", "device002");
        }
    }
}
