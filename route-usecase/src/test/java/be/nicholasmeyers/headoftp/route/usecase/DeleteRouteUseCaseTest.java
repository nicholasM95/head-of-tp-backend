package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.repository.RouteRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteRouteUseCaseTest {

    @InjectMocks
    private DeleteRouteUseCase deleteRouteUseCase;

    @Mock
    private RouteRepository routeRepository;

    @Nested
    class DeleteRoute {
        @Test
        void givenRouteId_whenDeleteRoute_thenRouteIsDeleted() {
            // Given
            UUID routeId = UUID.fromString("39645975-e135-4ad4-8130-87f947fb058c");

            // When
            deleteRouteUseCase.deleteRoute(routeId);

            // Then
            verify(routeRepository).deleteRouteByRouteId(routeId);
        }
    }
}
