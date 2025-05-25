package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
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
public class RoutePointJdbcRepository implements RoutePointQueryRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<RoutePointProjection> findAllRoutePointsByRouteId(UUID routeId) {
        String query = """
                SELECT id, latitude, longitude, altitude
                FROM route_point WHERE route_id = :routeId
                """;

        return template.query(query, Map.of("routeId", routeId), this::map);
    }

    private RoutePointProjection map(ResultSet resultSet, int i) throws SQLException {
        return new RoutePointProjection(resultSet.getDouble("latitude"), resultSet.getDouble("longitude"), resultSet.getDouble("altitude"));
    }
}
