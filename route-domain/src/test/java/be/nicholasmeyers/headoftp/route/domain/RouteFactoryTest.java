package be.nicholasmeyers.headoftp.route.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import be.nicholasmeyers.headoftp.common.domain.validation.Error;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RouteFactoryTest {

    @Nested
    class CreateRoute {
        @Test
        void givenCreateRouteRequest_whenCreateRoute_thenReturnCreationOfRouteAndEmptyNotification() {
            // Given
            CreateRoutePointRequest createRoutePointRequest1 = new CreateRoutePointRequest(3.3, 4.4, 5.5);
            CreateRoutePointRequest createRoutePointRequest2 = new CreateRoutePointRequest(3.4, 4.5, 5.9);
            CreateRoutePointRequest createRoutePointRequest3 = new CreateRoutePointRequest(3.5, 4.8, 7.5);
            RoutePoint routePoint1 = RoutePointFactory.createRoutePoint(createRoutePointRequest1).getValue();
            RoutePoint routePoint2 = RoutePointFactory.createRoutePoint(createRoutePointRequest2).getValue();
            RoutePoint routePoint3 = RoutePointFactory.createRoutePoint(createRoutePointRequest3).getValue();

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("name", List.of(routePoint1, routePoint2, routePoint3));

            // When
            Creation<Route> route = RouteFactory.createRoute(createRouteRequest);

            // Then
            assertThat(route).isNotNull();
            assertThat(route.getNotification().hasErrors()).isFalse();
            assertThat(route.getValue()).isNotNull();
            assertThat(route.getValue().getName()).isEqualTo("name");
            assertThat(route.getValue().getPoints()).containsExactly(routePoint1, routePoint2, routePoint3);
            assertThat(route.getValue().getElevationGain()).isEqualTo(2);
            assertThat(route.getValue().getEstimatedAverageSpeed()).isEqualTo(28.0);
            assertThat(route.getValue().getDistanceInMeters()).isEqualTo(50817);
            assertThat(route.getValue().getDurationInMinutes()).isEqualTo(109);
            assertThat(route.getValue().getEstimatedStartTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0)));
            assertThat(route.getValue().getEstimatedEndTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 49, 0)));
            assertThat(route.getValue().getPauseInMinutes()).isEqualTo(0);
            assertThat(route.getValue().getStartTime()).isNull();
            assertThat(route.getValue().getAverageSpeed()).isNull();
        }

        @Test
        void givenCreateRouteRequestWithoutName_whenCreateRoute_thenReturnCreationOfRoutePointAndNotificationError() {
            // Given
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(3.3, 4.4, 5.5);
            RoutePoint routePoint = RoutePointFactory.createRoutePoint(createRoutePointRequest).getValue();

            CreateRouteRequest createRouteRequest = new CreateRouteRequest(null, List.of(routePoint));

            // When
            Creation<Route> route = RouteFactory.createRoute(createRouteRequest);

            // Then
            assertThat(route).isNotNull();
            assertThat(route.getNotification().hasErrors()).isTrue();
            assertThat(route.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.empty", "name"));
            assertThat(route.getValue()).isNotNull();
            assertThat(route.getValue().getName()).isNull();
            assertThat(route.getValue().getPoints()).containsExactly(routePoint);
            assertThat(route.getValue().getElevationGain()).isEqualTo(0);
            assertThat(route.getValue().getEstimatedAverageSpeed()).isEqualTo(28.0);
            assertThat(route.getValue().getDistanceInMeters()).isEqualTo(0);
            assertThat(route.getValue().getDurationInMinutes()).isEqualTo(0);
            assertThat(route.getValue().getEstimatedStartTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0)));
            assertThat(route.getValue().getEstimatedEndTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0)));
            assertThat(route.getValue().getPauseInMinutes()).isEqualTo(0);
            assertThat(route.getValue().getStartTime()).isNull();
            assertThat(route.getValue().getAverageSpeed()).isNull();
        }

        @Test
        void givenCreateRouteRequestWithoutRoutePoints_whenCreateRoute_thenReturnCreationOfRoutePointAndNotificationError() {
            // Given
            CreateRouteRequest createRouteRequest = new CreateRouteRequest("name", null);

            // When
            Creation<Route> route = RouteFactory.createRoute(createRouteRequest);

            // Then
            assertThat(route).isNotNull();
            assertThat(route.getNotification().hasErrors()).isTrue();
            assertThat(route.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "points"));
            assertThat(route.getValue()).isNotNull();
            assertThat(route.getValue().getName()).isEqualTo("name");
            assertThat(route.getValue().getPoints()).isNull();
            assertThat(route.getValue().getElevationGain()).isEqualTo(0);
            assertThat(route.getValue().getEstimatedAverageSpeed()).isEqualTo(28.0);
            assertThat(route.getValue().getDistanceInMeters()).isEqualTo(0);
            assertThat(route.getValue().getDurationInMinutes()).isEqualTo(0);
            assertThat(route.getValue().getEstimatedStartTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0)));
            assertThat(route.getValue().getEstimatedEndTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0)));
            assertThat(route.getValue().getPauseInMinutes()).isEqualTo(0);
            assertThat(route.getValue().getStartTime()).isNull();
            assertThat(route.getValue().getAverageSpeed()).isNull();
        }

        @Test
        void givenCreateRouteRequestWithEmptyName_whenCreateRoute_thenReturnCreationOfRoutePointAndNotificationError() {
            // Given
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(3.3, 4.4, 5.5);
            RoutePoint routePoint = RoutePointFactory.createRoutePoint(createRoutePointRequest).getValue();

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("", List.of(routePoint));

            // When
            Creation<Route> route = RouteFactory.createRoute(createRouteRequest);

            // Then
            assertThat(route).isNotNull();
            assertThat(route.getNotification().hasErrors()).isTrue();
            assertThat(route.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.empty", "name"));
            assertThat(route.getValue()).isNotNull();
            assertThat(route.getValue().getName()).isEqualTo("");
            assertThat(route.getValue().getPoints()).containsExactly(routePoint);
            assertThat(route.getValue().getElevationGain()).isEqualTo(0);
            assertThat(route.getValue().getEstimatedAverageSpeed()).isEqualTo(28.0);
            assertThat(route.getValue().getDistanceInMeters()).isEqualTo(0);
            assertThat(route.getValue().getDurationInMinutes()).isEqualTo(0);
            assertThat(route.getValue().getEstimatedStartTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0)));
            assertThat(route.getValue().getEstimatedEndTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0)));
            assertThat(route.getValue().getPauseInMinutes()).isEqualTo(0);
            assertThat(route.getValue().getStartTime()).isNull();
            assertThat(route.getValue().getAverageSpeed()).isNull();
        }

        @Test
        void givenCreateRouteRequestWithEmptyRoutePoints_whenCreateRoute_thenReturnCreationOfRoutePointAndNotificationError() {
            // Given
            CreateRouteRequest createRouteRequest = new CreateRouteRequest("name", List.of());

            // When
            Creation<Route> route = RouteFactory.createRoute(createRouteRequest);

            // Then
            assertThat(route).isNotNull();
            assertThat(route.getNotification().hasErrors()).isTrue();
            assertThat(route.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.empty", "points"));
            assertThat(route.getValue()).isNotNull();
            assertThat(route.getValue().getName()).isEqualTo("name");
            assertThat(route.getValue().getPoints()).isEmpty();
            assertThat(route.getValue().getElevationGain()).isEqualTo(0);
            assertThat(route.getValue().getEstimatedAverageSpeed()).isEqualTo(28.0);
            assertThat(route.getValue().getDistanceInMeters()).isEqualTo(0);
            assertThat(route.getValue().getDurationInMinutes()).isEqualTo(0);
            assertThat(route.getValue().getEstimatedStartTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0)));
            assertThat(route.getValue().getEstimatedEndTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0)));
            assertThat(route.getValue().getPauseInMinutes()).isEqualTo(0);
            assertThat(route.getValue().getStartTime()).isNull();
            assertThat(route.getValue().getAverageSpeed()).isNull();
        }
    }
}
