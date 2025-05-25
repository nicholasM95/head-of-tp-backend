package be.nicholasmeyers.headoftp.route.repository;

import be.nicholasmeyers.headoftp.route.domain.Route;

import java.util.Optional;
import java.util.UUID;

public interface RouteRepository {

    Optional<Route> findRouteByRouteId(UUID routeId);

    void createRoute(Route route);

    void deleteRouteByRouteId(UUID routeId);

    void updateRoute(Route route);
}
