package be.nicholasmeyers.headoftp.route.event;

import be.nicholasmeyers.headoftp.route.projection.RouteVirtualGhostProjection;

import java.util.List;

public interface LocationEventSender {
    void sendLocationEvent(List<RouteVirtualGhostProjection> routeVirtualGhostProjections);
}
