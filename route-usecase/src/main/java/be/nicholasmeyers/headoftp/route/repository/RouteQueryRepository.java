package be.nicholasmeyers.headoftp.route.repository;

import be.nicholasmeyers.headoftp.route.projection.RouteProjection;

import java.util.List;

public interface RouteQueryRepository {

    List<RouteProjection> findAllRoutes();

    List<RouteProjection> findAllActiveRoutes();

}
