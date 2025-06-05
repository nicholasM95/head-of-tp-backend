package be.nicholasmeyers.headoftp.route.adapter.socket;

import be.nicholasmeyers.headoftp.route.event.LocationEventSender;
import be.nicholasmeyers.headoftp.route.projection.RouteMarkerProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class LocationEventWebSocketSender implements LocationEventSender {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendLocationEvent(List<RouteMarkerProjection> routeMarkerProjections) {
        routeMarkerProjections.forEach(routeMarkerProjection -> {
            RoutePoint routePoint = new RoutePoint(routeMarkerProjection.latitude(), routeMarkerProjection.longitude());
            String destination = "/topic/route/ID/TYPE"
                    .replace("ID", routeMarkerProjection.routeId().toString())
                    .replace("TYPE", routeMarkerProjection.type().name().toLowerCase());
            messagingTemplate.convertAndSend(destination, routePoint);
        });
    }

    private record RoutePoint(Double latitude, Double longitude) {
    }
}
