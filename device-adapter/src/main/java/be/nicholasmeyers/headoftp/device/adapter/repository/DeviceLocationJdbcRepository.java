package be.nicholasmeyers.headoftp.device.adapter.repository;

import be.nicholasmeyers.headoftp.device.projection.DeviceProjection;
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

    public List<DeviceProjection> findAllDevices() {
        String query = "SELECT device_id, MAX(created_date) AS last_modified_date_location FROM device_location GROUP BY device_id ORDER BY device_id";

        return template.query(query, Map.of(), (rs, rowNum) ->
                new DeviceProjection(rs.getString("device_id"), rs.getTimestamp("last_modified_date_location").toLocalDateTime()));
    }
}
