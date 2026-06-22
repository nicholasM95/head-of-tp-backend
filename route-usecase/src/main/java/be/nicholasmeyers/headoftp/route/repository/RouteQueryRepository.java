package be.nicholasmeyers.headoftp.route.repository;

import be.nicholasmeyers.headoftp.route.projection.RouteProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RouteQueryRepository {

    Optional<RouteProjection> findById(UUID id);

    List<RouteProjection> findAllRoutes();

    List<RouteProjection> findAllActiveRoutes();

}
