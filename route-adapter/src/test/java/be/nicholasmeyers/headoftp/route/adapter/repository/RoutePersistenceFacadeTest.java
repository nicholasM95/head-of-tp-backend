package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.domain.CreateRoutePointRequest;
import be.nicholasmeyers.headoftp.route.domain.CreateRouteRequest;
import be.nicholasmeyers.headoftp.route.domain.Route;
import be.nicholasmeyers.headoftp.route.domain.RouteFactory;
import be.nicholasmeyers.headoftp.route.domain.RoutePoint;
import be.nicholasmeyers.headoftp.route.domain.RoutePointFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class RoutePersistenceFacadeTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoutePersistenceFacade routePersistenceFacade;

    @Sql(value = "route.sql")
    @Nested
    class FindRouteByRouteId {
        @Test
        void givenRouteId_whenFindRouteByRouteId_thenReturnRoute() {
            // Given
            UUID routeId = UUID.fromString("cc335f92-d2e8-483c-b0ae-aa7e75677353");

            // When
            Optional<Route> route = routePersistenceFacade.findRouteByRouteId(routeId);

            // Then
            assertThat(route.isPresent()).isTrue();
            assertThat(route).isNotNull();
            assertThat(route.get().getId()).isEqualTo(UUID.fromString("cc335f92-d2e8-483c-b0ae-aa7e75677353"));
            assertThat(route.get().getName()).isEqualTo("route-name 4");
            assertThat(route.get().getElevationGain()).isEqualTo(2000);
            assertThat(route.get().getEstimatedAverageSpeed()).isEqualTo(21.4);
            assertThat(route.get().getDistanceInMeters()).isEqualTo(1000);
            assertThat(route.get().getDurationInMinutes()).isEqualTo(120);
            assertThat(route.get().getEstimatedStartTime()).isEqualTo(LocalDateTime.of(2025, 5, 20, 12, 0));
            assertThat(route.get().getEstimatedEndTime()).isEqualTo(LocalDateTime.of(2025, 5, 20, 14, 0));
            assertThat(route.get().getPauseInMinutes()).isEqualTo(50);
            assertThat(route.get().getStartTime()).isNull();
            assertThat(route.get().getAverageSpeed()).isNull();
        }
    }

    @Nested
    class CreateRoute {
        @Test
        void givenRoute_whenCreateRoute_thenRouteIsCreated() {
            // Given
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(1.1, 2.2, 4.3);

            List<RoutePoint> routePoints = new ArrayList<>();
            routePoints.add(RoutePointFactory.createRoutePoint(createRoutePointRequest).getValue());

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", routePoints);
            Route route = RouteFactory.createRoute(createRouteRequest).getValue();

            // When
            routePersistenceFacade.createRoute(route);

            // Then
            String sqlRoute = "SELECT id, route_name, elevation_gain, estimated_average_speed, distance_in_meters, duration_in_minutes, " +
                              "estimated_start_time, estimated_end_time, pause_in_minutes, start_time, average_speed, created_date, " +
                              "last_modified_date FROM route";
            Map<String, Object> resultRoute = jdbcTemplate.queryForMap(sqlRoute);
            assertThat(resultRoute).isNotNull();
            assertThat(resultRoute.get("id")).isNotNull();
            assertThat(resultRoute.get("route_name")).isEqualTo("route");
            assertThat(resultRoute.get("elevation_gain")).isEqualTo(0);
            assertThat(resultRoute.get("estimated_average_speed")).isEqualTo(28.0);
            assertThat(resultRoute.get("distance_in_meters")).isEqualTo(0);
            assertThat(resultRoute.get("duration_in_minutes")).isEqualTo(0);
            assertThat(resultRoute.get("estimated_start_time")).isNotNull();
            assertThat(resultRoute.get("estimated_end_time")).isNotNull();
            assertThat(resultRoute.get("pause_in_minutes")).isEqualTo(0);
            assertThat(resultRoute.get("start_time")).isNull();
            assertThat(resultRoute.get("average_speed")).isNull();
            assertThat(resultRoute.get("created_date")).isNotNull();
            assertThat(resultRoute.get("last_modified_date")).isNotNull();

            String sqlRoutePoint = "SELECT id, route_id, latitude, longitude, altitude, distance_from_start_in_meter, created_date, last_modified_date FROM route_point";
            Map<String, Object> resultRoutePoint = jdbcTemplate.queryForMap(sqlRoutePoint);
            assertThat(resultRoute).isNotNull();
            assertThat(resultRoutePoint.get("id")).isNotNull();
            assertThat(resultRoutePoint.get("route_id")).isNotNull();
            assertThat(resultRoutePoint.get("latitude")).isEqualTo(1.1);
            assertThat(resultRoutePoint.get("longitude")).isEqualTo(2.2);
            assertThat(resultRoutePoint.get("altitude")).isEqualTo(4.3);
            assertThat(resultRoutePoint.get("distance_from_start_in_meter")).isEqualTo(0);
            assertThat(resultRoutePoint.get("created_date")).isNotNull();
            assertThat(resultRoutePoint.get("last_modified_date")).isNotNull();
        }
    }

    @Sql(value = "route.sql")
    @Nested
    class DeleteRouteByRouteId {
        @Test
        void givenRouteId_whenDeleteRoute_thenRouteIsDeleted() {
            // Given
            UUID routeId = UUID.fromString("e0483c47-0aa0-442d-808b-8897687f4af2");

            // When
            routePersistenceFacade.deleteRouteByRouteId(routeId);

            // Then
            String sqlRoute = "SELECT COUNT(id) FROM route WHERE id = 'e0483c47-0aa0-442d-808b-8897687f4af2'";
            Long resultRoute = jdbcTemplate.queryForObject(sqlRoute, Long.class);
            assertThat(resultRoute).isNotNull();
            assertThat(resultRoute).isEqualTo(0L);

            String sqlRoutePoint = "SELECT COUNT(id) FROM route_point WHERE route_id = 'e0483c47-0aa0-442d-808b-8897687f4af2'";
            Long resultRoutePoint = jdbcTemplate.queryForObject(sqlRoutePoint, Long.class);
            assertThat(resultRoutePoint).isNotNull();
            assertThat(resultRoutePoint).isEqualTo(0L);
        }
    }

    @Sql(value = "route.sql")
    @Nested
    class UpdateRoute {
        @Test
        void givenRouteId_whenUpdateRoute_thenRouteIsUpdated() {
            // Given
            UUID routeId = UUID.fromString("cc335f92-d2e8-483c-b0ae-aa7e75677353");
            Route route = routePersistenceFacade.findRouteByRouteId(routeId).get();

            route.setEstimatedAverageSpeed(30.5);
            route.setEstimatedStartTime(route.getEstimatedStartTime().plusMinutes(30));
            route.setPauseInMinutes(30);

            // When
            routePersistenceFacade.updateRoute(route);

            // Given
            Route actualRoute = routePersistenceFacade.findRouteByRouteId(routeId).get();
            assertThat(actualRoute).isNotNull();
            assertThat(actualRoute.getId()).isEqualTo(UUID.fromString("cc335f92-d2e8-483c-b0ae-aa7e75677353"));
            assertThat(actualRoute.getName()).isEqualTo("route-name 4");
            assertThat(actualRoute.getElevationGain()).isEqualTo(2000);
            assertThat(actualRoute.getEstimatedAverageSpeed()).isEqualTo(30.5);
            assertThat(actualRoute.getDistanceInMeters()).isEqualTo(1000);
            assertThat(actualRoute.getDurationInMinutes()).isEqualTo(2);
            assertThat(actualRoute.getEstimatedStartTime()).isEqualTo(LocalDateTime.of(2025, 5, 20, 12, 30));
            assertThat(actualRoute.getEstimatedEndTime()).isEqualTo(LocalDateTime.of(2025, 5, 20, 13, 2));
            assertThat(actualRoute.getPauseInMinutes()).isEqualTo(30);
            assertThat(actualRoute.getStartTime()).isNull();
            assertThat(actualRoute.getAverageSpeed()).isNull();
        }
    }
}
