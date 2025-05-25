package be.nicholasmeyers.headoftp.route.domain;

import java.util.List;

public record CreateRouteRequest(String name, List<RoutePoint> points) {
}
