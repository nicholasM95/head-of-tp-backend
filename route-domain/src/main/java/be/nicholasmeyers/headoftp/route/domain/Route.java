package be.nicholasmeyers.headoftp.route.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.ListValidator;
import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.common.domain.validation.StringValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class Route {

    private static final double AVERAGE_SPEED = 28.0;
    private static final double EARTH_RADIUS_IN_METERS = 6371000;
    private static final int DEFAULT_PAUSE_IN_MINUTES = 0;


    private UUID id;
    private final String name;
    private final List<RoutePoint> points;
    private final Integer elevationGain;
    private Double estimatedAverageSpeed;
    private final Integer distanceInMeters;
    private Integer durationInMinutes;
    private LocalDateTime estimatedStartTime;
    private LocalDateTime estimatedEndTime;
    private Integer pauseInMinutes;
    private LocalDateTime startTime;
    private Double averageSpeed;

    protected Route(CreateRouteRequest createRouteRequest) {
        this.name = createRouteRequest.name();
        this.points = createRouteRequest.points();
        this.elevationGain = calculateElevationGain(createRouteRequest.points());
        this.estimatedAverageSpeed = AVERAGE_SPEED;
        this.distanceInMeters = calculateTotalDistanceInMeters(createRouteRequest.points());
        this.durationInMinutes = (int) Math.round((distanceInMeters / 1000.0) / estimatedAverageSpeed * 60.0);
        this.estimatedStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.NOON);
        this.estimatedEndTime = this.estimatedStartTime.plusMinutes(durationInMinutes);
        this.pauseInMinutes = DEFAULT_PAUSE_IN_MINUTES;
    }

    protected Notification validate() {
        Notification notification = new Notification();
        StringValidator.notBlank("name", name, notification);
        ListValidator.notEmpty("points", points, notification);
        return notification;
    }

    public void setEstimatedAverageSpeed(Double estimatedAverageSpeed) {
        this.estimatedAverageSpeed = estimatedAverageSpeed;
        this.durationInMinutes = (int) Math.round((distanceInMeters / 1000.0) / estimatedAverageSpeed * 60.0);
        this.estimatedEndTime = this.estimatedStartTime.plusMinutes(durationInMinutes);
    }

    public void setEstimatedStartTime(LocalDateTime estimatedStartTime) {
        this.estimatedStartTime = estimatedStartTime;
        this.estimatedEndTime = this.estimatedStartTime.plusMinutes(durationInMinutes);
    }

    public void setPauseInMinutes(Integer pauseInMinutes) {
        this.pauseInMinutes = pauseInMinutes;
        this.estimatedEndTime = this.estimatedStartTime.plusMinutes(durationInMinutes).plusMinutes(pauseInMinutes);
    }

    private int calculateElevationGain(List<RoutePoint> points) {
        if (points == null || points.isEmpty()) {
            return 0;
        }
        double elevationGain = 0.0;

        for (int i = 1; i < points.size(); i++) {
            double delta = points.get(i).getAltitude() - points.get(i - 1).getAltitude();
            if (delta > 0) {
                elevationGain += delta;
            }
        }

        return (int) Math.round(elevationGain);
    }

    private int calculateTotalDistanceInMeters(List<RoutePoint> points) {
        if (points == null || points.isEmpty()) {
            return 0;
        }
        double totalDistance = 0.0;

        for (int i = 1; i < points.size(); i++) {
            totalDistance += haversine(points.get(i - 1), points.get(i));
        }

        return (int) Math.round(totalDistance);
    }

    private double haversine(RoutePoint p1, RoutePoint p2) {
        double lat1 = Math.toRadians(p1.getLatitude());
        double lon1 = Math.toRadians(p1.getLongitude());
        double lat2 = Math.toRadians(p2.getLatitude());
        double lon2 = Math.toRadians(p2.getLongitude());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                   + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_IN_METERS * c;
    }
}
