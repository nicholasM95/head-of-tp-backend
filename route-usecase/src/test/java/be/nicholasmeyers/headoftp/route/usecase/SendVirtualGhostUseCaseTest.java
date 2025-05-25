package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.event.LocationEventSender;
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
import java.util.UUID;

import static be.nicholasmeyers.headoftp.route.projection.RouteProjectionMother.createRouteProjection1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SendVirtualGhostUseCaseTest {

    @InjectMocks
    private SendVirtualGhostUseCase sendVirtualGhostUseCase;

    @Mock
    private RouteQueryRepository routeQueryRepository;

    @Mock
    private RoutePointQueryRepository routePointQueryRepository;

    @Mock
    private LocationEventSender locationEventSender;

    @Nested
    class SendVirtualGhost {
        @Test
        void givenRoutes_whenSendVirtualGhosts_thenVerifyLocationEventSenderIsCalled() {
            // Given
            when(routeQueryRepository.findAllActiveRoutes()).thenReturn(List.of(createRouteProjection1()));

            RoutePointProjection routePointProjection = new RoutePointProjection(0.0, 0.0, 0.0);
            when(routePointQueryRepository.findAllRoutePointsByRouteId(any(UUID.class))).thenReturn(List.of(routePointProjection));

            // When
            sendVirtualGhostUseCase.sendVirtualGhost();

            // Then
            verify(locationEventSender).sendLocationEvent(anyList());
        }
    }
}
