package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NavigateToMetersOnRouteUseCaseTest {

    @InjectMocks
    private NavigateToMetersOnRouteUseCase  navigateToMetersOnRouteUseCase;

    @Mock
    private RoutePointQueryRepository routePointQueryRepository;

    @Nested
    class FindRoutePointByRouteIdAndMetersOnRoute {
        @Test
        void givenRouteIdAndMeters_whenFindRoutePointByRouteIdAndMetersOnRoute_thenReturnOptionalOfRoute() {
            // Given
            UUID routeId = UUID.fromString("d822b9af-550a-4919-9369-ad24075de66a");
            Integer meters = 40000;

            RoutePointProjection routePointProjection = new RoutePointProjection(55.3, 90.1, 0.0, 20);
            when(routePointQueryRepository.findRoutePointByRouteIdAndMetersOnRoute(any(UUID.class), anyInt()))
                    .thenReturn(Optional.of(routePointProjection));

            // When
            Optional<String> url = navigateToMetersOnRouteUseCase.navigateToMetersOnRoute(routeId, meters);

            // Then
            assertThat(url).contains("https://www.google.com/maps/dir/?api=1&origin=My+Location&destination=55.3,90.1");
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(UUID.fromString("d822b9af-550a-4919-9369-ad24075de66a"), 40000);
        }

        @Test
        void givenRouteIdAndMeters_whenFindRoutePointByRouteIdAndMetersOnRoute_thenReturnEmptyOptional() {
            // Given
            UUID routeId = UUID.fromString("d822b9af-550a-4919-9369-ad24075de66a");
            Integer meters = 40000;

            when(routePointQueryRepository.findRoutePointByRouteIdAndMetersOnRoute(any(UUID.class), anyInt()))
                    .thenReturn(Optional.empty());

            // When
            Optional<String> url = navigateToMetersOnRouteUseCase.navigateToMetersOnRoute(routeId, meters);

            // Then
            assertThat(url).isEmpty();
            verify(routePointQueryRepository).findRoutePointByRouteIdAndMetersOnRoute(UUID.fromString("d822b9af-550a-4919-9369-ad24075de66a"), 40000);
        }
    }
}
