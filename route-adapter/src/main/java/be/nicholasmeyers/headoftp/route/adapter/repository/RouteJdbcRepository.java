package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.projection.RouteProjection;
import be.nicholasmeyers.headoftp.route.repository.RouteQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class RouteJdbcRepository implements RouteQueryRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public Optional<RouteProjection> findById(UUID id) {
        String query = """
                SELECT id, route_name, elevation_gain, estimated_average_speed, distance_in_meters, duration_in_minutes,
                       estimated_start_time, estimated_end_time, pause_in_minutes, start_time, average_speed, created_date, last_modified_date
                FROM route
                WHERE id = :id
                """;

        return template.query(query, Map.of("id", id), this::map).stream().findFirst();
    }

    @Override
    public List<RouteProjection> findAllRoutes() {
        String query = """
                SELECT id, route_name, elevation_gain, estimated_average_speed, distance_in_meters, duration_in_minutes,
                       estimated_start_time, estimated_end_time, pause_in_minutes, start_time, average_speed, created_date, last_modified_date
                FROM route
                ORDER BY estimated_start_time
                """;

        return template.query(query, Map.of(), this::map);
    }

    @Override
    public List<RouteProjection> findAllActiveRoutes() {
        String query = """
                SELECT id, route_name, elevation_gain, estimated_average_speed, distance_in_meters, duration_in_minutes,
                       estimated_start_time, estimated_end_time, pause_in_minutes, start_time, average_speed, created_date, last_modified_date
                FROM route
                WHERE estimated_start_time <= NOW() AND estimated_end_time >= NOW()
                ORDER BY estimated_start_time
                """;

        return template.query(query, Map.of(), this::map);
    }

    private RouteProjection map(ResultSet resultSet, int i) throws SQLException {
        return new RouteProjection(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("route_name"),
                resultSet.getInt("elevation_gain"),
                resultSet.getDouble("estimated_average_speed"),
                resultSet.getInt("distance_in_meters"),
                resultSet.getInt("duration_in_minutes"),
                resultSet.getTimestamp("estimated_start_time").toLocalDateTime(),
                resultSet.getTimestamp("estimated_end_time").toLocalDateTime(),
                resultSet.getInt("pause_in_minutes"),
                Optional.ofNullable(resultSet.getTimestamp("start_time")).map(Timestamp::toLocalDateTime).orElse(null),
                resultSet.getDouble("average_speed"),
                resultSet.getTimestamp("created_date").toLocalDateTime(),
                resultSet.getTimestamp("last_modified_date").toLocalDateTime()
        );
    }
}
