package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.projection.RouteClimbProjection;
import be.nicholasmeyers.headoftp.route.repository.RouteClimbQueryRepository;
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
public class FindAllRouteClimbsByRouteIdUseCaseTest {
    @InjectMocks
    private FindAllRouteClimbsByRouteIdUseCase findAllRouteClimbsByRouteIdUseCase;

    @Mock
    private RouteClimbQueryRepository routeClimbQueryRepository;

    @Nested
    class FindAllRouteClimbsByRouteId {
        @Test
        void givenRouteId_whenFindAllRouteClimbsByRouteId_thenReturnListOfRouteClimbs() {
            // Given
            UUID routeId = UUID.randomUUID();

            RouteClimbProjection routeClimbProjection = new RouteClimbProjection(4000, 5500, 1500, 120, 8.0);
            when(routeClimbQueryRepository.findAllRouteClimbsByRouteId(any(UUID.class))).thenReturn(List.of(routeClimbProjection));

            // When
            List<RouteClimbProjection> routeClimbs = findAllRouteClimbsByRouteIdUseCase.findAllRouteClimbsByRouteId(routeId);

            //Then
            assertThat(routeClimbs).containsExactly(new RouteClimbProjection(4000, 5500, 1500, 120, 8.0));
            verify(routeClimbQueryRepository).findAllRouteClimbsByRouteId(routeId);
        }
    }
}
