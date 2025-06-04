package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class RoutePointJdbcRepository implements RoutePointQueryRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<RoutePointProjection> findAllRoutePointsByRouteId(UUID routeId) {
        String query = """
                SELECT latitude, longitude, altitude, distance_from_start_in_meter
                FROM route_point WHERE route_id = :routeId
                ORDER BY distance_from_start_in_meter
                """;

        return template.query(query, Map.of("routeId", routeId), this::map);
    }

    @Override
    public Optional<RoutePointProjection> findRoutePointByRouteIdAndMetersOnRoute(UUID routeId, Integer metersOnRoute) {
        String query = """
                SELECT latitude, longitude, altitude, distance_from_start_in_meter
                FROM route_point WHERE route_id = :routeId
                AND distance_from_start_in_meter > :metersOnRoute
                ORDER BY distance_from_start_in_meter
                LIMIT 1
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("routeId", routeId);
        params.put("metersOnRoute", metersOnRoute);
        return template.query(query, params, this::map).stream().findFirst();
    }

    @Override
    public Optional<RoutePointProjection> findLastRoutePointByParticipantId(UUID participantId) {
        String query = """
                SELECT latitude, longitude, altitude, 0 AS distance_from_start_in_meter
                FROM device_location
                WHERE device_id = (SELECT UPPER(device_id) AS device_id FROM participant WHERE id = :participantId)
                ORDER BY location_timestamp DESC
                LIMIT 1
                """;
        return template.query(query, Map.of("participantId", participantId), this::map).stream().findFirst();
    }

    private RoutePointProjection map(ResultSet resultSet, int i) throws SQLException {
        return new RoutePointProjection(
                resultSet.getDouble("latitude"),
                resultSet.getDouble("longitude"),
                resultSet.getDouble("altitude"),
                resultSet.getInt("distance_from_start_in_meter"));
    }
}
