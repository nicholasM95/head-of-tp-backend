package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.projection.RouteProjection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.route.projection.RouteProjectionMother.createRouteProjection1;
import static be.nicholasmeyers.headoftp.route.projection.RouteProjectionMother.createRouteProjection3;
import static be.nicholasmeyers.headoftp.route.projection.RouteProjectionMother.createRouteProjection4;
import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class RouteJdbcRepositoryTest {

    @Autowired
    private RouteJdbcRepository routeJdbcRepository;

    @Sql(value = "route.sql")
    @Nested
    class FindById {
        @Test
        void givenId_whenFindById_thenReturnRoute() {
            // Given
            UUID id = UUID.fromString("e0483c47-0aa0-442d-808b-8897687f4af2");

            // When
            Optional<RouteProjection> routeProjection = routeJdbcRepository.findById(id);

            // Then
            assertThat(routeProjection).contains(createRouteProjection1());
        }

        @Test
        void givenId_whenFindById_thenReturnEmptyOptional() {
            // Given
            UUID id = UUID.randomUUID();

            // When
            Optional<RouteProjection> routeProjection = routeJdbcRepository.findById(id);

            // Then
            assertThat(routeProjection).isEmpty();
        }
    }

    @Sql(value = "route.sql")
    @Nested
    class FindAllRoutes {
        @Test
        void given_whenFindAllRoutes_thenReturnRoutes() {
            // Given

            // When
            List<RouteProjection> routes = routeJdbcRepository.findAllRoutes();

            // Then
            assertThat(routes).containsExactly(createRouteProjection1(), createRouteProjection3(), createRouteProjection4());
        }
    }

    @Sql(value = "route.sql")
    @Nested
    class FindAllActiveRoutes {
        @Test
        void given_whenFindAllActiveRoutes_thenReturnRoutes() {
            // Given

            // When
            List<RouteProjection> routes = routeJdbcRepository.findAllActiveRoutes();

            // Then
            assertThat(routes).containsExactly(createRouteProjection3());
        }
    }
}
