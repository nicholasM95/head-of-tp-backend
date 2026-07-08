package be.nicholasmeyers.headoftp.route.repository;

import be.nicholasmeyers.headoftp.route.projection.RouteClimbProjection;

import java.util.List;
import java.util.UUID;

public interface RouteClimbQueryRepository {

    List<RouteClimbProjection> findAllRouteClimbsByRouteId(UUID routeId);
}
