package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.common.domain.validation.Error;
import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.route.domain.CreateRoutePointRequest;
import be.nicholasmeyers.headoftp.route.domain.Route;
import be.nicholasmeyers.headoftp.route.repository.RouteRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class CreateRouteUseCaseTest {

    @InjectMocks
    private CreateRouteUseCase createRouteUseCase;

    @Mock
    private RouteRepository routeRepository;

    @Nested
    class CreateRoute {
        @Test
        void givenNameAndListOfCreateRoutePoints_whenCreateRoute_thenReturnNotificationWithoutError() {
            // Given
            String name = "name";
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(5.5, 6.7, 8.5);
            List<CreateRoutePointRequest> createRoutePointRequests = List.of(createRoutePointRequest);

            // When
            Notification notification = createRouteUseCase.createRoute(name, createRoutePointRequests);

            // Then
            assertThat(notification).isNotNull();
            assertThat(notification.hasErrors()).isFalse();

            ArgumentCaptor<Route> captor = ArgumentCaptor.forClass(Route.class);
            verify(routeRepository).createRoute(captor.capture());

            Route actual = captor.getValue();
            assertThat(actual.getName()).isEqualTo("name");
            assertThat(actual.getPoints().size()).isEqualTo(1);
            assertThat(actual.getPoints().getFirst().getLatitude()).isEqualTo(5.5);
            assertThat(actual.getPoints().getFirst().getLongitude()).isEqualTo(6.7);
            assertThat(actual.getPoints().getFirst().getAltitude()).isEqualTo(8.5);
        }

        @Test
        void givenInvalidNameAndListOfCreateRoutePoints_whenCreateRoute_thenReturnNotificationWithError() {
            // Given
            String name = "";
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(5.5, 6.7, 8.5);
            List<CreateRoutePointRequest> createRoutePointRequests = List.of(createRoutePointRequest);

            // When
            Notification notification = createRouteUseCase.createRoute(name, createRoutePointRequests);

            // Then
            assertThat(notification).isNotNull();
            assertThat(notification.hasErrors()).isTrue();
            assertThat(notification.getErrors()).containsExactly(new Error("field.must.not.be.empty", "name"));

            verifyNoInteractions(routeRepository);
        }

        @Test
        void givenNameAndInvalidListOfCreateRoutePoints_whenCreateRoute_thenReturnNotificationWithError() {
            // Given
            String name = "name";
            CreateRoutePointRequest createRoutePointRequest = new CreateRoutePointRequest(null, 6.7, 8.5);
            List<CreateRoutePointRequest> createRoutePointRequests = List.of(createRoutePointRequest);

            // When
            Notification notification = createRouteUseCase.createRoute(name, createRoutePointRequests);

            // Then
            assertThat(notification).isNotNull();
            assertThat(notification.hasErrors()).isTrue();
            assertThat(notification.getErrors()).containsExactly(new Error("field.must.not.be.null", "latitude"));

            verifyNoInteractions(routeRepository);
        }
    }
}
