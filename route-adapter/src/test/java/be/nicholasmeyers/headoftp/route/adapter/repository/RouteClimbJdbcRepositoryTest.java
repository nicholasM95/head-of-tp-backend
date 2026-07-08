package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.projection.RouteClimbProjection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class RouteClimbJdbcRepositoryTest {

    @Autowired
    private RouteClimbJdbcRepository routeClimbJdbcRepository;

    @Sql(value = "route.sql")
    @Nested
    class FindAllRouteClimbsByRouteId {
        @Test
        void givenRouteId_whenFindAllRouteClimbsByRouteId_thenReturnRouteClimbs() {
            // Given
            UUID routeId = UUID.fromString("e0483c47-0aa0-442d-808b-8897687f4af2");

            // When
            List<RouteClimbProjection> routeClimbs = routeClimbJdbcRepository.findAllRouteClimbsByRouteId(routeId);

            // Then
            assertThat(routeClimbs).containsExactly(new RouteClimbProjection(4000, 5500, 1500, 120, 8.0));
        }

        @Test
        void givenUnknownRouteId_whenFindAllRouteClimbsByRouteId_thenReturnEmptyList() {
            // Given
            UUID routeId = UUID.randomUUID();

            // When
            List<RouteClimbProjection> routeClimbs = routeClimbJdbcRepository.findAllRouteClimbsByRouteId(routeId);

            // Then
            assertThat(routeClimbs).isEmpty();
        }
    }
}
