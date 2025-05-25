package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class FindAllRoutePointsByRouteIdUseCase {

    private final RoutePointQueryRepository routePointQueryRepository;

    public List<RoutePointProjection> findAllRoutePointsByRouteId(UUID routeId) {
        log.info("Find all route points by route id {}", routeId);
        return routePointQueryRepository.findAllRoutePointsByRouteId(routeId);
    }
}
