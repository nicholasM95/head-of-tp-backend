package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.domain.CreateRouteRequest;
import be.nicholasmeyers.headoftp.route.domain.Route;
import be.nicholasmeyers.headoftp.route.domain.RouteFactory;
import be.nicholasmeyers.headoftp.route.repository.RouteRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatchRouteUseCaseTest {

    @InjectMocks
    private PatchRouteUseCase patchRouteUseCase;

    @Mock
    private RouteRepository routeRepository;

    @Nested
    class PatchRoute {
        @Test
        void givenRouteIdAndEstimatedAverageSpeedAndEstimatedStartTimeAndPauseInMinutes_whenPatchRoute_thenUpdateRouteIsCalled() {
            // Given
            UUID routeId = UUID.randomUUID();
            Double estimatedAverageSpeed = 30.0;
            LocalDateTime estimatedStartTime = LocalDateTime.of(2025, 6, 1, 10, 30);
            Integer pauseInMinutes = 45;

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", List.of());
            Route route = RouteFactory.createRoute(createRouteRequest).getValue();

            when(routeRepository.findRouteByRouteId(any(UUID.class)))
                    .thenReturn(Optional.of(route));

            // When
            patchRouteUseCase.patchRoute(routeId, estimatedAverageSpeed, estimatedStartTime, pauseInMinutes);

            // Then
            verify(routeRepository).findRouteByRouteId(routeId);
            ArgumentCaptor<Route> routeCaptor = ArgumentCaptor.forClass(Route.class);
            verify(routeRepository).updateRoute(routeCaptor.capture());
            Route updatedRoute = routeCaptor.getValue();
            assertThat(updatedRoute.getEstimatedAverageSpeed()).isEqualTo(estimatedAverageSpeed);
            assertThat(updatedRoute.getEstimatedStartTime()).isEqualTo(estimatedStartTime);
            assertThat(updatedRoute.getPauseInMinutes()).isEqualTo(pauseInMinutes);
        }

        @Test
        void givenRouteIdAndEstimatedStartTimeAndPauseInMinutes_whenPatchRoute_thenUpdateRouteIsCalled() {
            // Given
            UUID routeId = UUID.randomUUID();
            Double estimatedAverageSpeed = null;
            LocalDateTime estimatedStartTime = LocalDateTime.of(2025, 6, 1, 10, 30);
            Integer pauseInMinutes = 45;

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", List.of());
            Route route = RouteFactory.createRoute(createRouteRequest).getValue();

            when(routeRepository.findRouteByRouteId(any(UUID.class)))
                    .thenReturn(Optional.of(route));

            // When
            patchRouteUseCase.patchRoute(routeId, estimatedAverageSpeed, estimatedStartTime, pauseInMinutes);

            // Then
            verify(routeRepository).findRouteByRouteId(routeId);
            ArgumentCaptor<Route> routeCaptor = ArgumentCaptor.forClass(Route.class);
            verify(routeRepository).updateRoute(routeCaptor.capture());
            Route updatedRoute = routeCaptor.getValue();
            assertThat(updatedRoute.getEstimatedAverageSpeed()).isEqualTo(28.0);
            assertThat(updatedRoute.getEstimatedStartTime()).isEqualTo(estimatedStartTime);
            assertThat(updatedRoute.getPauseInMinutes()).isEqualTo(pauseInMinutes);
        }

        @Test
        void givenRouteIdAndEstimatedAverageSpeedAndPauseInMinutes_whenPatchRoute_thenUpdateRouteIsCalled() {
            // Given
            UUID routeId = UUID.randomUUID();
            Double estimatedAverageSpeed = 30.0;
            LocalDateTime estimatedStartTime = null;
            Integer pauseInMinutes = 45;

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", List.of());
            Route route = RouteFactory.createRoute(createRouteRequest).getValue();

            when(routeRepository.findRouteByRouteId(any(UUID.class)))
                    .thenReturn(Optional.of(route));

            // When
            patchRouteUseCase.patchRoute(routeId, estimatedAverageSpeed, estimatedStartTime, pauseInMinutes);

            // Then
            verify(routeRepository).findRouteByRouteId(routeId);
            ArgumentCaptor<Route> routeCaptor = ArgumentCaptor.forClass(Route.class);
            verify(routeRepository).updateRoute(routeCaptor.capture());
            Route updatedRoute = routeCaptor.getValue();
            assertThat(updatedRoute.getEstimatedAverageSpeed()).isEqualTo(estimatedAverageSpeed);
            assertThat(updatedRoute.getEstimatedStartTime()).isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));
            assertThat(updatedRoute.getPauseInMinutes()).isEqualTo(pauseInMinutes);
        }

        @Test
        void givenRouteIdAndEstimatedAverageSpeedAndEstimatedStartTime_whenPatchRoute_thenUpdateRouteIsCalled() {
            // Given
            UUID routeId = UUID.randomUUID();
            Double estimatedAverageSpeed = 30.0;
            LocalDateTime estimatedStartTime = LocalDateTime.of(2025, 6, 1, 10, 30);
            Integer pauseInMinutes = null;

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", List.of());
            Route route = RouteFactory.createRoute(createRouteRequest).getValue();

            when(routeRepository.findRouteByRouteId(any(UUID.class)))
                    .thenReturn(Optional.of(route));

            // When
            patchRouteUseCase.patchRoute(routeId, estimatedAverageSpeed, estimatedStartTime, pauseInMinutes);

            // Then
            verify(routeRepository).findRouteByRouteId(routeId);
            ArgumentCaptor<Route> routeCaptor = ArgumentCaptor.forClass(Route.class);
            verify(routeRepository).updateRoute(routeCaptor.capture());
            Route updatedRoute = routeCaptor.getValue();
            assertThat(updatedRoute.getEstimatedAverageSpeed()).isEqualTo(estimatedAverageSpeed);
            assertThat(updatedRoute.getEstimatedStartTime()).isEqualTo(estimatedStartTime);
            assertThat(updatedRoute.getPauseInMinutes()).isEqualTo(0);
        }

        @Test
        void givenRouteId_whenPatchRoute_thenUpdateRouteIsCalled() {
            // Given
            UUID routeId = UUID.randomUUID();
            Double estimatedAverageSpeed = null;
            LocalDateTime estimatedStartTime = null;
            Integer pauseInMinutes = null;

            CreateRouteRequest createRouteRequest = new CreateRouteRequest("route", List.of());
            Route route = RouteFactory.createRoute(createRouteRequest).getValue();

            when(routeRepository.findRouteByRouteId(any(UUID.class)))
                    .thenReturn(Optional.of(route));

            // When
            patchRouteUseCase.patchRoute(routeId, estimatedAverageSpeed, estimatedStartTime, pauseInMinutes);

            // Then
            verify(routeRepository).findRouteByRouteId(routeId);
            verifyNoMoreInteractions(routeRepository);
        }
    }
}
