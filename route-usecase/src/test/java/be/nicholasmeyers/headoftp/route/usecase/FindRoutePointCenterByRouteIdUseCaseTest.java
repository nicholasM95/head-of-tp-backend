package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindRoutePointCenterByRouteIdUseCaseTest {

    @InjectMocks
    private FindRoutePointCenterByRouteIdUseCase findRoutePointCenterByRouteIdUseCase;

    @Mock
    private RoutePointQueryRepository routePointQueryRepository;

    @Nested
    class FindRoutePointCenterByRouteId {
        @Test
        void givenRouteId_whenFindRoutePointCenterByRouteId_thenReturnCentre() {
            // Given
            UUID routeId = UUID.randomUUID();

            RoutePointProjection routePointProjection1 =  new RoutePointProjection(50.8467, 4.3499, 12.3);
            RoutePointProjection routePointProjection2 = new RoutePointProjection(50.8798, 4.7005, 12.4);
            RoutePointProjection routePointProjection3 = new RoutePointProjection(51.0250, 4.4776, 12.7);

            when(routePointQueryRepository.findAllRoutePointsByRouteId(any(UUID.class)))
                    .thenReturn(List.of(routePointProjection1, routePointProjection2, routePointProjection3));

            // When
            Optional<RoutePointProjection> routePointCenter = findRoutePointCenterByRouteIdUseCase.findRoutePointCenterByRouteId(routeId);

            // Then
            RoutePointProjection expectedRoutePoint = new RoutePointProjection(50.91716666666667, 4.509333333333333, 0.0);

            assertThat(routePointCenter).isPresent();
            assertThat(routePointCenter.get()).isEqualTo(expectedRoutePoint);
        }
    }
}
