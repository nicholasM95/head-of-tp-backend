package be.nicholasmeyers.headoftp.route.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

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

    @Nested
    class CalculateClimbs {
        @Test
        void givenSteadyClimb_whenCreateRoute_thenRouteHasClimb() {
            // Given
            double earthRadiusInMeters = 6371000.0;
            double stepInMeters = 100.0;
            double stepInLatitudeDegrees = Math.toDegrees(stepInMeters / earthRadiusInMeters);

            List<RoutePoint> points = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                double latitude = 50.0 + i * stepInLatitudeDegrees;
                double altitude = 100.0 + i * 5.0;
                points.add(new RoutePoint(new CreateRoutePointRequest(latitude, 4.0, altitude)));
            }

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", points);

            // When
            Route route = new Route(createRouteRequest);

            // Then
            assertThat(route.getClimbs()).hasSize(1);
            RouteClimb climb = route.getClimbs().getFirst();
            assertThat(climb.startDistanceInMeter()).isEqualTo(0);
            assertThat(climb.endDistanceInMeter()).isCloseTo(900, within(5));
            assertThat(climb.lengthInMeter()).isCloseTo(900, within(5));
            assertThat(climb.elevationGainInMeter()).isEqualTo(45);
            assertThat(climb.averageGradient()).isCloseTo(5.0, within(0.2));
        }

        @Test
        void givenFlatRoute_whenCreateRoute_thenRouteHasNoClimb() {
            // Given
            List<RoutePoint> points = new ArrayList<>();
            points.add(new RoutePoint(new CreateRoutePointRequest(55.3, 4.5, 10.1)));
            points.add(new RoutePoint(new CreateRoutePointRequest(55.4, 4.7, 11.5)));
            points.add(new RoutePoint(new CreateRoutePointRequest(55.5, 4.9, 12.8)));

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", points);

            // When
            Route route = new Route(createRouteRequest);

            // Then
            assertThat(route.getClimbs()).isEmpty();
        }
    }
}
