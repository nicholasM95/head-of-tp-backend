package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.event.LocationEventSender;
import be.nicholasmeyers.headoftp.route.projection.RouteMarkerProjection;
import be.nicholasmeyers.headoftp.route.projection.RoutePointDeviceProjection;
import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.projection.Vehicle;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import be.nicholasmeyers.headoftp.route.repository.RouteQueryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.route.projection.MarkerType.CAR;
import static be.nicholasmeyers.headoftp.route.projection.MarkerType.GHOST;
import static be.nicholasmeyers.headoftp.route.projection.RouteProjectionMother.createRouteProjection1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SendRouteMarkerUseCaseTest {

    @InjectMocks
    private SendRouteMarkerUseCase sendRouteMarkerUseCase;

    @Mock
    private RouteQueryRepository routeQueryRepository;

    @Mock
    private RoutePointQueryRepository routePointQueryRepository;

    @Mock
    private LocationEventSender locationEventSender;

    @Nested
    class SendRouteMarkers {
        @Test
        void givenRoutes_whenSendRouteMarkers_thenVerifyLocationEventSenderIsCalled() {
            // Given
            when(routeQueryRepository.findAllActiveRoutes()).thenReturn(List.of(createRouteProjection1()));

            RoutePointProjection routePointProjection = new RoutePointProjection(10.0, 20.0, 0.0, 0);
            when(routePointQueryRepository.findAllRoutePointsByRouteId(any(UUID.class))).thenReturn(List.of(routePointProjection));

            RoutePointDeviceProjection routePointDeviceProjection = new RoutePointDeviceProjection("", 44.3, 55.1, Vehicle.CAR);
            when(routePointQueryRepository.findLastRoutePointOfEveryDevice())
                    .thenReturn(List.of(routePointDeviceProjection));

            // When
            sendRouteMarkerUseCase.sendRouteMarkers();

            // Then
            ArgumentCaptor<List<RouteMarkerProjection>> captor = ArgumentCaptor.forClass(List.class);
            verify(locationEventSender).sendLocationEvent(captor.capture());
            verify(routeQueryRepository).findAllActiveRoutes();
            verify(routePointQueryRepository).findAllRoutePointsByRouteId(UUID.fromString("e0483c47-0aa0-442d-808b-8897687f4af2"));
            verify(routePointQueryRepository).findLastRoutePointOfEveryDevice();

            assertThat(captor.getValue()).containsExactly(
                    new RouteMarkerProjection(UUID.fromString("e0483c47-0aa0-442d-808b-8897687f4af2"), 10.0, 20.0, GHOST),
                    new RouteMarkerProjection(UUID.fromString("e0483c47-0aa0-442d-808b-8897687f4af2"), 44.3, 55.1, CAR)
            );

        }
    }
}
