package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.event.LocationEventSender;
import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.projection.RouteProjection;
import be.nicholasmeyers.headoftp.route.projection.RouteVirtualGhostProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import be.nicholasmeyers.headoftp.route.repository.RouteQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SendVirtualGhostUseCase {

    private static final double EARTH_RADIUS_IN_METERS = 6371000;

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

    private RoutePointProjection calculateGhostRiderTimes(List<RoutePointProjection> points, double averageSpeedKmh, long startTimeMillis) {
        double totalDistance = 0.0;
        RoutePointProjection previous = null;
        double averageSpeedMps = averageSpeedKmh * 1000 / 3600; // Average speed in m/s

        for (RoutePointProjection point : points) {
            if (previous != null) {
                double distance = calculate3DDistance(previous, point);
                totalDistance += distance;
                long timeMillis = startTimeMillis + (long) ((totalDistance / averageSpeedMps) * 1000);
                LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), ZoneId.of("Europe/Brussels"));
                if (dateTime.isAfter(ChronoLocalDateTime.from(LocalDateTime.now().atZone(ZoneId.of("Europe/Brussels"))))) {
                    return point;
                }
            }
            previous = point;
        }
        return points.getLast();
    }

    private static double calculate3DDistance(RoutePointProjection wp1, RoutePointProjection wp2) {
        double lat1 = wp1.latitude();
        double lon1 = wp1.longitude();
        double ele1 = wp1.altitude();

        double lat2 = wp2.latitude();
        double lon2 = wp2.longitude();
        double ele2 = wp2.altitude();


        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double flatDistance = EARTH_RADIUS_IN_METERS * c;

        double heightDifference = ele2 - ele1;

        return Math.sqrt(flatDistance * flatDistance + heightDifference * heightDifference);
    }
}
