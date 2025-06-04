package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.event.LocationEventSender;
import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.projection.RouteProjection;
import be.nicholasmeyers.headoftp.route.projection.RouteVirtualGhostProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import be.nicholasmeyers.headoftp.route.repository.RouteQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static be.nicholasmeyers.headoftp.route.utils.RoutePointCalculateUtils.calculateGhostRiderTimes;

@RequiredArgsConstructor
@Slf4j
public class SendVirtualGhostUseCase {

    private final RouteQueryRepository routeQueryRepository;
    private final RoutePointQueryRepository routePointQueryRepository;
    private final LocationEventSender locationEventSender;

    public void sendVirtualGhost() {
        List<RouteVirtualGhostProjection> virtualGhosts = new ArrayList<>();
        List<RouteProjection> routes = routeQueryRepository.findAllActiveRoutes();
        ZoneId zoneId = ZoneId.of("Europe/Brussels");
        routes.forEach(route -> {
            if (route.startTime() != null) {
                log.info("Calculate ghost points for {} from actual start time", route.id());
                List<RoutePointProjection> routePoints = routePointQueryRepository.findAllRoutePointsByRouteId(route.id());
                long startTimeInMillis = route.startTime().plusMinutes(route.pauseInMinutes()).atZone(zoneId).toInstant().toEpochMilli();
                RoutePointProjection ghost = calculateGhostRiderTimes(routePoints, route.averageSpeed(), startTimeInMillis);
                virtualGhosts.add(new RouteVirtualGhostProjection(route.id(), ghost.latitude(), ghost.longitude()));
            } else {
                log.info("Calculate ghost points for {} from estimated start time", route.id());
                List<RoutePointProjection> routePoints = routePointQueryRepository.findAllRoutePointsByRouteId(route.id());
                long startTimeInMillis = route.estimatedStartTime().plusMinutes(route.pauseInMinutes()).atZone(zoneId).toInstant().toEpochMilli();
                RoutePointProjection ghost = calculateGhostRiderTimes(routePoints, route.estimatedAverageSpeed(), startTimeInMillis);
                virtualGhosts.add(new RouteVirtualGhostProjection(route.id(), ghost.latitude(), ghost.longitude()));
            }
        });
        locationEventSender.sendLocationEvent(virtualGhosts);
    }
}
