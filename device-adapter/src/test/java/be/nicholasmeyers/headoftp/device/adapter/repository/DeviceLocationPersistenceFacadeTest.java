package be.nicholasmeyers.headoftp.device.adapter.repository;

import be.nicholasmeyers.headoftp.device.domain.CreateDeviceLocationRequest;
import be.nicholasmeyers.headoftp.device.domain.DeviceLocation;
import be.nicholasmeyers.headoftp.device.domain.DeviceLocationFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class DeviceLocationPersistenceFacadeTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DeviceLocationPersistenceFacade deviceLocationPersistenceFacade;


    @Nested
    class CreateDeviceLocation {
        @Test
        void givenDeviceLocation_whenCreateDeviceLocation_thenDeviceLocationCreated() {
            // Given
            CreateDeviceLocationRequest createDeviceLocationRequest = new CreateDeviceLocationRequest("151675", 849486L, 55.5, 66.6, 99.9,
                    56.3, 10.0, 15.2, 40.0);
            DeviceLocation deviceLocation = DeviceLocationFactory.createDeviceLocation(createDeviceLocationRequest).getValue();

            // When
            deviceLocationPersistenceFacade.createDeviceLocation(deviceLocation);

            // Then
            String sql = """
                    SELECT id, device_id, location_timestamp, latitude, longitude, speed, bearing, altitude, accuracy, battery, created_date, last_modified_date
                    FROM device_location WHERE device_id = ?
                    """;
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, "151675");

            assertThat(result).isNotNull();
            assertThat(result.get("id")).isNotNull();
            assertThat(result.get("device_id")).isEqualTo("151675");
            assertThat(result.get("location_timestamp")).isEqualTo(849486L);
            assertThat(result.get("latitude")).isEqualTo(55.5);
            assertThat(result.get("longitude")).isEqualTo(66.6);
            assertThat(result.get("speed")).isEqualTo(99.9);
            assertThat(result.get("bearing")).isEqualTo(56.3);
            assertThat(result.get("altitude")).isEqualTo(10.0);
            assertThat(result.get("accuracy")).isEqualTo(15.2);
            assertThat(result.get("battery")).isEqualTo(40.0);
            assertThat(result.get("created_date")).isNotNull();
            assertThat(result.get("last_modified_date")).isNotNull();
        }
    }
}
