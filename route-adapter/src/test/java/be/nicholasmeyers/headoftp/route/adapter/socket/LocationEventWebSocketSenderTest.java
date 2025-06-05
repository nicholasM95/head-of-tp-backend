package be.nicholasmeyers.headoftp.route.adapter.socket;

import be.nicholasmeyers.headoftp.route.projection.RouteMarkerProjection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.route.projection.MarkerType.GHOST;

@ExtendWith(MockitoExtension.class)
public class LocationEventWebSocketSenderTest {

    @InjectMocks
    private LocationEventWebSocketSender locationEventWebSocketSender;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @Nested
    class SendLocationEvent {
        @Test
        void givenListOfRouteMakerProjection_whenSendLocationEvent_thenShouldSendLocationEvent() {
            // Given
            RouteMarkerProjection routeMarkerProjection =
                    new RouteMarkerProjection(UUID.fromString("5d2a0a54-0327-42aa-bfd5-1521731c3370"),
                            90.4, 55.4, GHOST);
            List<RouteMarkerProjection> routeMarkerProjections = List.of(routeMarkerProjection);

            // When
            locationEventWebSocketSender.sendLocationEvent(routeMarkerProjections);

            // Then
        }
    }
}
