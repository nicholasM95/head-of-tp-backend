package be.nicholasmeyers.headoftp.route.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import be.nicholasmeyers.headoftp.common.domain.validation.Error;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoutePointFactoryTest {

    @Nested
    class CreateRoutePoint {
        @Test
        void givenCreateRoutePointRequest_whenCreateRoutePoint_thenReturnCreationOfRoutePointAndEmptyNotification() {
            // Given
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(3.3, 4.4, 5.5);

            // When
            Creation<RoutePoint> routePoint = RoutePointFactory.createRoutePoint(createRoutePointRequest);

            // Then
            assertThat(routePoint).isNotNull();
            assertThat(routePoint.getNotification().hasErrors()).isFalse();
            assertThat(routePoint.getValue()).isNotNull();
            assertThat(routePoint.getValue().getLatitude()).isEqualTo(3.3);
            assertThat(routePoint.getValue().getLongitude()).isEqualTo(4.4);
            assertThat(routePoint.getValue().getAltitude()).isEqualTo(5.5);
        }

        @Test
        void givenCreateRoutePointRequestWithoutLatitude_whenCreateRoutePoint_thenReturnCreationOfRoutePointAndNotificationError() {
            // Given
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(null, 4.4, 5.5);

            // When
            Creation<RoutePoint> routePoint = RoutePointFactory.createRoutePoint(createRoutePointRequest);

            // Then
            assertThat(routePoint).isNotNull();
            assertThat(routePoint.getNotification().hasErrors()).isTrue();
            assertThat(routePoint.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "latitude"));
            assertThat(routePoint.getValue()).isNotNull();
            assertThat(routePoint.getValue().getLatitude()).isNull();
            assertThat(routePoint.getValue().getLongitude()).isEqualTo(4.4);
            assertThat(routePoint.getValue().getAltitude()).isEqualTo(5.5);
        }

        @Test
        void givenCreateRoutePointRequestWithoutLongitude_whenCreateRoutePoint_thenReturnCreationOfRoutePointAndNotificationError() {
            // Given
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(3.3, null, 5.5);

            // When
            Creation<RoutePoint> routePoint = RoutePointFactory.createRoutePoint(createRoutePointRequest);

            // Then
            assertThat(routePoint).isNotNull();
            assertThat(routePoint.getNotification().hasErrors()).isTrue();
            assertThat(routePoint.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "longitude"));
            assertThat(routePoint.getValue()).isNotNull();
            assertThat(routePoint.getValue().getLatitude()).isEqualTo(3.3);
            assertThat(routePoint.getValue().getLongitude()).isNull();
            assertThat(routePoint.getValue().getAltitude()).isEqualTo(5.5);
        }

        @Test
        void givenCreateRoutePointRequestWithoutAltitude_whenCreateRoutePoint_thenReturnCreationOfRoutePointAndNotificationError() {
            // Given
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(3.3, 4.4, null);

            // When
            Creation<RoutePoint> routePoint = RoutePointFactory.createRoutePoint(createRoutePointRequest);

            // Then
            assertThat(routePoint).isNotNull();
            assertThat(routePoint.getNotification().hasErrors()).isTrue();
            assertThat(routePoint.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "altitude"));
            assertThat(routePoint.getValue()).isNotNull();
            assertThat(routePoint.getValue().getLatitude()).isEqualTo(3.3);
            assertThat(routePoint.getValue().getLongitude()).isEqualTo(4.4);
            assertThat(routePoint.getValue().getAltitude()).isNull();
        }

        @Test
        void givenCreateRoutePointRequestWithInvalidLatitude_whenCreateRoutePoint_thenReturnCreationOfRoutePointAndNotificationError() {
            // Given
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(0.0, 4.4, 5.5);

            // When
            Creation<RoutePoint> routePoint = RoutePointFactory.createRoutePoint(createRoutePointRequest);

            // Then
            assertThat(routePoint).isNotNull();
            assertThat(routePoint.getNotification().hasErrors()).isTrue();
            assertThat(routePoint.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.zero", "latitude"));
            assertThat(routePoint.getValue()).isNotNull();
            assertThat(routePoint.getValue().getLatitude()).isEqualTo(0.0);
            assertThat(routePoint.getValue().getLongitude()).isEqualTo(4.4);
            assertThat(routePoint.getValue().getAltitude()).isEqualTo(5.5);
        }

        @Test
        void givenCreateRoutePointRequestWithInvalidLongitude_whenCreateRoutePoint_thenReturnCreationOfRoutePointAndNotificationError() {
            // Given
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(3.3, 0.0, 5.5);

            // When
            Creation<RoutePoint> routePoint = RoutePointFactory.createRoutePoint(createRoutePointRequest);

            // Then
            assertThat(routePoint).isNotNull();
            assertThat(routePoint.getNotification().hasErrors()).isTrue();
            assertThat(routePoint.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.zero", "longitude"));
            assertThat(routePoint.getValue()).isNotNull();
            assertThat(routePoint.getValue().getLatitude()).isEqualTo(3.3);
            assertThat(routePoint.getValue().getLongitude()).isEqualTo(0.0);
            assertThat(routePoint.getValue().getAltitude()).isEqualTo(5.5);
        }

        @Test
        void givenCreateRoutePointRequestWithInvalidAltitude_whenCreateRoutePoint_thenReturnCreationOfRoutePointAndNotificationError() {
            // Given
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(3.3, 4.4, 0.0);

            // When
            Creation<RoutePoint> routePoint = RoutePointFactory.createRoutePoint(createRoutePointRequest);

            // Then
            assertThat(routePoint).isNotNull();
            assertThat(routePoint.getNotification().hasErrors()).isTrue();
            assertThat(routePoint.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.zero", "altitude"));
            assertThat(routePoint.getValue()).isNotNull();
            assertThat(routePoint.getValue().getLatitude()).isEqualTo(3.3);
            assertThat(routePoint.getValue().getLongitude()).isEqualTo(4.4);
            assertThat(routePoint.getValue().getAltitude()).isEqualTo(0.0);
        }
    }
}
