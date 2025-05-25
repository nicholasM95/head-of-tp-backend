package be.nicholasmeyers.headoftp.route.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RouteTest {

    @Nested
    class SetEstimatedAverageSpeed {
        @Test
        void givenRoute_whenSetEstimatedAverageSpeed_thenRouteIsUpdated() {
            // Given
            List<RoutePoint> points = new ArrayList<>();
            points.add(new RoutePoint(new CreateRoutePointRequest(55.3, 4.5, 10.1)));
            points.add(new RoutePoint(new CreateRoutePointRequest(55.4, 4.7, 11.5)));
            points.add(new RoutePoint(new CreateRoutePointRequest(55.5, 4.9, 12.8)));

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", points);
            Route route = new Route(createRouteRequest);

            // When
            route.setEstimatedAverageSpeed(32.3);

            // Then
            assertThat(route.getEstimatedAverageSpeed()).isEqualTo(32.3);
            assertThat(route.getDurationInMinutes()).isEqualTo(63);
            assertThat(route.getEstimatedEndTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 3)));
        }
    }

    @Nested
    class SetEstimatedStartTime {
        @Test
        void givenRoute_whenSetEstimatedStartTime_thenRouteIsUpdated() {
            // Given
            List<RoutePoint> points = new ArrayList<>();
            points.add(new RoutePoint(new CreateRoutePointRequest(55.3, 4.5, 10.1)));
            points.add(new RoutePoint(new CreateRoutePointRequest(55.4, 4.7, 11.5)));
            points.add(new RoutePoint(new CreateRoutePointRequest(55.5, 4.9, 12.8)));

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", points);
            Route route = new Route(createRouteRequest);

            // When
            route.setEstimatedStartTime(route.getEstimatedStartTime().plusMinutes(30));

            // Then
            assertThat(route.getEstimatedAverageSpeed()).isEqualTo(28);
            assertThat(route.getDurationInMinutes()).isEqualTo(72);
            assertThat(route.getEstimatedEndTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 42)));
        }
    }

    @Nested
    class SetPauseInMinutes {
        @Test
        void givenRoute_whenSetPauseInMinutese_thenRouteIsUpdated() {
            // Given
            List<RoutePoint> points = new ArrayList<>();
            points.add(new RoutePoint(new CreateRoutePointRequest(55.3, 4.5, 10.1)));
            points.add(new RoutePoint(new CreateRoutePointRequest(55.4, 4.7, 11.5)));
            points.add(new RoutePoint(new CreateRoutePointRequest(55.5, 4.9, 12.8)));

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", points);
            Route route = new Route(createRouteRequest);

            // When
            route.setPauseInMinutes(45);

            // Then
            assertThat(route.getEstimatedAverageSpeed()).isEqualTo(28);
            assertThat(route.getDurationInMinutes()).isEqualTo(72);
            assertThat(route.getEstimatedEndTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 57)));
        }
    }
}
