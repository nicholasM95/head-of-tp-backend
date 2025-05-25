package be.nicholasmeyers.headoftp.route.projection;

import java.util.UUID;

public record RouteVirtualGhostProjection(UUID routeId, Double latitude, Double longitude) {
}
