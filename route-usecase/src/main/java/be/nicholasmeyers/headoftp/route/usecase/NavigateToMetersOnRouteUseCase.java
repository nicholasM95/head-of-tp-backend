package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class NavigateToMetersOnRouteUseCase {

    private static final String GOOGLE_MAPS_URL = "https://www.google.com/maps/dir/?api=1&origin=My+Location&destination=LAT,LNG";

    private final RoutePointQueryRepository routePointQueryRepository;

    public Optional<String> navigateToMetersOnRoute(UUID routeId, Integer metersOnRoute) {
        log.info("Navigating to meter {} on route {}", metersOnRoute, routeId);
        Optional<RoutePointProjection> routePoint = routePointQueryRepository.findRoutePointByRouteIdAndMetersOnRoute(routeId, metersOnRoute);
        return routePoint.map(routePointProjection -> GOOGLE_MAPS_URL
                        .replace("LAT", routePointProjection.latitude().toString())
                        .replace("LNG", routePointProjection.longitude().toString()));
    }
}
