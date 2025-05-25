package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class RoutePointJdbcRepositoryTest {

    @Autowired
    private RoutePointJdbcRepository routePointJdbcRepository;

    @Sql(value = "route.sql")
    @Nested
    class FindAllRoutePointsByRouteId {
        @Test
        void givenRouteId_whenFindAllRoutePointsByRouteId_thenReturnRoutePoints() {
            // Given
            UUID routeId = UUID.fromString("e0483c47-0aa0-442d-808b-8897687f4af2");

            // When
            List<RoutePointProjection> routePoints = routePointJdbcRepository.findAllRoutePointsByRouteId(routeId);

            // Then
            assertThat(routePoints).containsExactly(new RoutePointProjection(34.4, 55.9, 90.5));

        }
    }
}
