package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class FindRoutePointCenterByRouteIdUseCase {

    private final RoutePointQueryRepository routePointQueryRepository;

    public Optional<RoutePointProjection> findRoutePointCenterByRouteId(UUID routeId) {
        log.info("Find route point center by route id {}", routeId);
        return getCenter(routePointQueryRepository.findAllRoutePointsByRouteId(routeId));
    }

    private Optional<RoutePointProjection> getCenter(List<RoutePointProjection> routePoints) {
        if(routePoints.isEmpty()) return Optional.empty();
        double sumLat = 0;
        double sumLon = 0;
        for (RoutePointProjection point : routePoints) {
            sumLat += point.latitude();
            sumLon += point.longitude();
        }

        double avgLat = sumLat / routePoints.size();
        double avgLon = sumLon / routePoints.size();
        return Optional.of(new RoutePointProjection(avgLat, avgLon, 0.0, 0));
    }
}
