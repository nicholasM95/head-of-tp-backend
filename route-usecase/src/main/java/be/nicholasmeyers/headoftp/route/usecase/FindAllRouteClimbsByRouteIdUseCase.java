package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.projection.RouteClimbProjection;
import be.nicholasmeyers.headoftp.route.repository.RouteClimbQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class FindAllRouteClimbsByRouteIdUseCase {

    private final RouteClimbQueryRepository routeClimbQueryRepository;

    public List<RouteClimbProjection> findAllRouteClimbsByRouteId(UUID routeId) {
        log.info("Find all route climbs by route id {}", routeId);
        return routeClimbQueryRepository.findAllRouteClimbsByRouteId(routeId);
    }
}
