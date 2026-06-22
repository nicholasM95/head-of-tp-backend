package be.nicholasmeyers.headoftp.route.event;

import be.nicholasmeyers.headoftp.route.projection.RouteMarkerProjection;

import java.util.List;

public interface LocationEventSender {
    void sendLocationEvent(List<RouteMarkerProjection> routeMarkerProjections);
}
