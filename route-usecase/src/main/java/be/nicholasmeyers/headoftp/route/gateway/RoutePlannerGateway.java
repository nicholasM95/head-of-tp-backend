package be.nicholasmeyers.headoftp.route.gateway;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;

public interface RoutePlannerGateway {

    Integer calculateDuration(RoutePointProjection point1, RoutePointProjection point2);
}
