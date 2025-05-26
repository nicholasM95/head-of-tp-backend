package be.nicholasmeyers.headoftp.route.adapter.socket;

import be.nicholasmeyers.headoftp.route.event.LocationEventSender;
import be.nicholasmeyers.headoftp.route.projection.RouteVirtualGhostProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class LocationEventWebSocketSender implements LocationEventSender {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendLocationEvent(List<RouteVirtualGhostProjection> routeVirtualGhostProjections) {
        messagingTemplate.convertAndSend("/topic/route/ghost", routeVirtualGhostProjections);
    }
}
