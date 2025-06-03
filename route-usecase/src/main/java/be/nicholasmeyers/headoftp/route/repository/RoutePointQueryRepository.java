package be.nicholasmeyers.headoftp.route.repository;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutePointQueryRepository {

    List<RoutePointProjection> findAllRoutePointsByRouteId(UUID routeId);

    Optional<RoutePointProjection> findRoutePointByRouteIdAndMetersOnRoute(UUID routeId, Integer metersOnRoute);
}
