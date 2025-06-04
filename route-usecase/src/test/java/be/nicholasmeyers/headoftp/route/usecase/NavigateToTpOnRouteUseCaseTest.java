package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.gateway.RoutePlannerGateway;
import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import be.nicholasmeyers.headoftp.route.repository.RouteQueryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.route.projection.RouteProjectionMother.createRouteProjection1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NavigateToTpOnRouteUseCaseTest {

    @InjectMocks
    private NavigateToTpOnRouteUseCase navigateToTpOnRouteUseCase;

    @Mock
    private RouteQueryRepository routeQueryRepository;

    @Mock
    private RoutePointQueryRepository routePointQueryRepository;

    @Mock
    private RoutePlannerGateway routePlannerGateway;

    @Nested
    class WhenRoutePointQueryRepository {
        @Test
        void shouldReturnRoutePointQueryRepository() {
            // Given
            UUID routeId = UUID.fromString("e0483c47-0aa0-442d-808b-8897687f4af2");
            UUID tp = UUID.fromString("af09374a-83d2-4eba-b3fe-6ea4fa2f8a64");

            RoutePointProjection routePointProjection1 = new RoutePointProjection(6.2, 3.5, 0.0, 0);
            RoutePointProjection routePointProjection2 = new RoutePointProjection(12.4, 34.6, 0.0, 0);
            RoutePointProjection routePointProjection3 = new RoutePointProjection(10.4, 30.6, 0.0, 0);
            RoutePointProjection routePointProjection4 = new RoutePointProjection(8.4, 30.6, 0.0, 0);

            when(routeQueryRepository.findById(any(UUID.class))).thenReturn(Optional.of(createRouteProjection1()));
            when(routePointQueryRepository.findAllRoutePointsByRouteId(any(UUID.class)))
                    .thenReturn(List.of(routePointProjection1, routePointProjection2));

            when(routePointQueryRepository.findLastRoutePointByParticipantId(any(UUID.class)))
                    .thenReturn(Optional.of(routePointProjection3));

            when(routePointQueryRepository.findRoutePointByRouteIdAndMetersOnRoute(any(UUID.class), anyInt()))
                    .thenReturn(Optional.of(routePointProjection4));

            when(routePlannerGateway.calculateDuration(any(RoutePointProjection.class), any(RoutePointProjection.class)))
                    .thenReturn(600);

            // When
            Optional<String> url = navigateToTpOnRouteUseCase.navigateToTpOnRoute(routeId, tp);

            // Then
            assertThat(url).isEmpty();

            verify(routeQueryRepository).findById(routeId);
            verify(routePointQueryRepository).findAllRoutePointsByRouteId(routeId);
            verify(routePointQueryRepository).findLastRoutePointByParticipantId(tp);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 1000);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 2000);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 3000);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 4000);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 5000);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 6000);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 7000);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 8000);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 9000);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 10000);
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(routeId, 11000);
            verify(routePlannerGateway, times(11)).calculateDuration(routePointProjection3, routePointProjection4);
        }
    }
}
