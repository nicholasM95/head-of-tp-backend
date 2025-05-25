package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.domain.Route;
import be.nicholasmeyers.headoftp.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoutePersistenceFacade implements RouteRepository {

    private final RouteJpaRepository routeJpaRepository;
    private final RoutePointJpaRepository routePointJpaRepository;

    @Override
    public Optional<Route> findRouteByRouteId(UUID routeId) {
        return routeJpaRepository.findById(routeId).map(RouteJpaEntity::toDomainObject);
    }

    @Override
    public void createRoute(Route route) {
        RouteJpaEntity routeJpaEntity = new RouteJpaEntity(route);
        routeJpaRepository.saveAndFlush(routeJpaEntity);

        route.getPoints().forEach(routePoints -> {
            RoutePointJpaEntity routePointJpaEntity = new RoutePointJpaEntity(routeJpaEntity.getId(), routePoints);
            routePointJpaRepository.save(routePointJpaEntity);
        });

        routePointJpaRepository.flush();
    }

    @Override
    public void deleteRouteByRouteId(UUID routeId) {
        routePointJpaRepository.deleteByRouteId(routeId);
        routeJpaRepository.flush();

        routeJpaRepository.deleteById(routeId);
        routeJpaRepository.flush();
    }

    @Override
    public void updateRoute(Route route) {
        Optional<RouteJpaEntity> routeJpaEntity = routeJpaRepository.findById(route.getId());
        if (routeJpaEntity.isPresent()) {
            routeJpaEntity.get().updateFromDomain(route);
            routeJpaRepository.saveAndFlush(routeJpaEntity.get());
        }

    }
}
