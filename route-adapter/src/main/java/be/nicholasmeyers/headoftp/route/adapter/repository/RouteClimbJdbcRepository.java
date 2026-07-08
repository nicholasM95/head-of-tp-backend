package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.projection.RouteClimbProjection;
import be.nicholasmeyers.headoftp.route.repository.RouteClimbQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class RouteClimbJdbcRepository implements RouteClimbQueryRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<RouteClimbProjection> findAllRouteClimbsByRouteId(UUID routeId) {
        String query = """
                SELECT start_distance_in_meter, end_distance_in_meter, length_in_meter, elevation_gain_in_meter, average_gradient
                FROM route_climb WHERE route_id = :routeId
                ORDER BY start_distance_in_meter
                """;

        return template.query(query, Map.of("routeId", routeId), this::mapRouteClimbProjection);
    }

    private RouteClimbProjection mapRouteClimbProjection(ResultSet resultSet, int i) throws SQLException {
        return new RouteClimbProjection(
                resultSet.getInt("start_distance_in_meter"),
                resultSet.getInt("end_distance_in_meter"),
                resultSet.getInt("length_in_meter"),
                resultSet.getInt("elevation_gain_in_meter"),
                resultSet.getDouble("average_gradient"));
    }
}
