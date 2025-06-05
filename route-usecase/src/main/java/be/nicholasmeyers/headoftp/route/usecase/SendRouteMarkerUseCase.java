package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.event.LocationEventSender;
import be.nicholasmeyers.headoftp.route.projection.MarkerType;
import be.nicholasmeyers.headoftp.route.projection.RoutePointDeviceProjection;
import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.projection.RouteProjection;
import be.nicholasmeyers.headoftp.route.projection.RouteMarkerProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import be.nicholasmeyers.headoftp.route.repository.RouteQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static be.nicholasmeyers.headoftp.route.projection.MarkerType.GHOST;
import static be.nicholasmeyers.headoftp.route.utils.RoutePointCalculateUtils.calculateGhostRiderTimes;

@RequiredArgsConstructor
@Slf4j
public class SendRouteMarkerUseCase {

    private final RouteQueryRepository routeQueryRepository;
    private final RoutePointQueryRepository routePointQueryRepository;
    private final LocationEventSender locationEventSender;

    public void sendRouteMarkers() {
        List<RouteMarkerProjection> routeMarkers = new ArrayList<>();
        List<RouteProjection> routes = routeQueryRepository.findAllActiveRoutes();
        List<RoutePointDeviceProjection> devices = routePointQueryRepository.findLastRoutePointOfEveryDevice();

        routes.forEach(route -> {
            routeMarkers.add(createRouteMarkerGhost(route));
            devices.forEach(device -> {
                routeMarkers.add(new RouteMarkerProjection(route.id(), device.latitude(), device.longitude(), MarkerType.valueOf(device.vehicle().name())));
            });
        });
        locationEventSender.sendLocationEvent(routeMarkers);
    }

    private RouteMarkerProjection createRouteMarkerGhost(RouteProjection route) {
        ZoneId zoneId = ZoneId.of("Europe/Brussels");
        if (route.startTime() != null) {
            log.info("Calculate ghost points for {} from actual start time", route.id());
            List<RoutePointProjection> routePoints = routePointQueryRepository.findAllRoutePointsByRouteId(route.id());
            long startTimeInMillis = route.startTime().plusMinutes(route.pauseInMinutes()).atZone(zoneId).toInstant().toEpochMilli();
            RoutePointProjection ghost = calculateGhostRiderTimes(routePoints, route.averageSpeed(), startTimeInMillis);
            return new RouteMarkerProjection(route.id(), ghost.latitude(), ghost.longitude(), GHOST);
        } else {
            log.info("Calculate ghost points for {} from estimated start time", route.id());
            List<RoutePointProjection> routePoints = routePointQueryRepository.findAllRoutePointsByRouteId(route.id());
            long startTimeInMillis = route.estimatedStartTime().plusMinutes(route.pauseInMinutes()).atZone(zoneId).toInstant().toEpochMilli();
            RoutePointProjection ghost = calculateGhostRiderTimes(routePoints, route.estimatedAverageSpeed(), startTimeInMillis);
            return new RouteMarkerProjection(route.id(), ghost.latitude(), ghost.longitude(), GHOST);
        }
    }
}
