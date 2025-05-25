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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllRoutePointsByRouteIdUseCaseTest {
    @InjectMocks
    private FindAllRoutePointsByRouteIdUseCase findAllRoutePointsByRouteIdUseCase;

    @Mock
    private RoutePointQueryRepository routePointQueryRepository;

    @Nested
    class FindAllRoutePointsByRouteId {
        @Test
        void givenRouteId_whenFindAllRoutePointsByRouteId_thenReturnListOfRoutePoints() {
            // Given
            UUID routeId = UUID.randomUUID();

            RoutePointProjection routePointProjection = new RoutePointProjection(44.5, 78.1, 12.3);
            when(routePointQueryRepository.findAllRoutePointsByRouteId(any(UUID.class))).thenReturn(List.of(routePointProjection));

            // When
            List<RoutePointProjection> routePoints = findAllRoutePointsByRouteIdUseCase.findAllRoutePointsByRouteId(routeId);

            //Then
            assertThat(routePoints).containsExactly(new RoutePointProjection(44.5, 78.1, 12.3));
            verify(routePointQueryRepository).findAllRoutePointsByRouteId(routeId);
        }
    }
}
