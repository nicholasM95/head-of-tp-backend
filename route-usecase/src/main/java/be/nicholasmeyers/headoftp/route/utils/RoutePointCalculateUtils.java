package be.nicholasmeyers.headoftp.route.utils;

import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutePointCalculateUtils {

    private static final double EARTH_RADIUS_IN_METERS = 6371000;

    public static RoutePointProjection calculateGhostRiderTimes(List<RoutePointProjection> points, double averageSpeedKmh, long startTimeMillis) {
        return calculateGhostRiderTimes(points, averageSpeedKmh, startTimeMillis, 0);
    }


    public static RoutePointProjection calculateGhostRiderTimes(List<RoutePointProjection> points, double averageSpeedKmh, long startTimeMillis, int inMinutes) {
        double totalDistance = 0.0;
        RoutePointProjection previous = null;
        double averageSpeedMps = averageSpeedKmh * 1000 / 3600; // Average speed in m/s

        for (RoutePointProjection point : points) {
            if (previous != null) {
                double distance = calculate3DDistance(previous, point);
                totalDistance += distance;
                long timeMillis = startTimeMillis + (long) ((totalDistance / averageSpeedMps) * 1000);
                LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), ZoneId.of("Europe/Brussels"));
                if (dateTime.isAfter(ChronoLocalDateTime.from(LocalDateTime.now().plusMinutes(inMinutes).atZone(ZoneId.of("Europe/Brussels"))))) {
                    return point;
                }
            }
            previous = point;
        }
        return points.getLast();
    }

    public static Integer calculateTimeToPoint(List<RoutePointProjection> points,
                                            double averageSpeedKmh,
                                            RoutePointProjection currentPosition,
                                            RoutePointProjection targetPoint) {
        double averageSpeedMps = averageSpeedKmh * 1000 / 3600; // m/s

        boolean started = false;
        double distanceRemaining = 0.0;
        RoutePointProjection previous = null;

        for (RoutePointProjection point : points) {
            if (!started) {
                if (point.equals(currentPosition)) {
                    started = true;
                    previous = point;
                }
            } else {
                double segmentDistance = calculate3DDistance(previous, point);
                distanceRemaining += segmentDistance;

                if (point.equals(targetPoint)) {
                    break;
                }
                previous = point;
            }
        }

        return (int) ((distanceRemaining / averageSpeedMps) * 1000);
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
