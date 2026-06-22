package be.nicholasmeyers.headoftp.route.projection;

import java.util.UUID;

public record RouteMarkerProjection(UUID routeId, String deviceId, Double latitude, Double longitude, MarkerType type) {
}
