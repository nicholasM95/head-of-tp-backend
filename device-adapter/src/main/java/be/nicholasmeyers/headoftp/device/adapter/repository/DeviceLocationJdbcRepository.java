package be.nicholasmeyers.headoftp.device.adapter.repository;

import be.nicholasmeyers.headoftp.device.repository.DeviceLocationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class DeviceLocationJdbcRepository implements DeviceLocationQueryRepository {

    private final NamedParameterJdbcTemplate template;

    public List<String> findAllDeviceIds() {
        String query = "SELECT DISTINCT device_id FROM device_location ORDER BY device_id ASC";

        return template.queryForList(query, Map.of(), String.class);
    }
}
