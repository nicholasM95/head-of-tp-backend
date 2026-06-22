package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.gateway.RoutePlannerGateway;
import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.projection.RouteProjection;
import be.nicholasmeyers.headoftp.route.repository.RoutePointQueryRepository;
import be.nicholasmeyers.headoftp.route.repository.RouteQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.route.utils.RoutePointCalculateUtils.calculateGhostRiderTimes;
import static be.nicholasmeyers.headoftp.route.utils.RoutePointCalculateUtils.calculateTimeToPoint;

@RequiredArgsConstructor
@Slf4j
public class NavigateToTpOnRouteUseCase {

    private static final String GOOGLE_MAPS_URL = "https://www.google.com/maps/dir/?api=1&origin=My+Location&destination=LAT,LNG";
    private static final int KILOMETER_IN_METER = 1000;
    private static final int FIVE_MINUTES = 300;

    private final RouteQueryRepository routeQueryRepository;
    private final RoutePointQueryRepository routePointQueryRepository;
    private final RoutePlannerGateway routePlannerGateway;

    public Optional<String> navigateToTpOnRoute(UUID routeId, UUID tp) {
        log.info("Navigating to tp {} on route {}", tp, routeId);

        Optional<RouteProjection> optionalRoute = routeQueryRepository.findById(routeId);
        if (optionalRoute.isPresent()) {
            RouteProjection route = optionalRoute.get();
            List<RoutePointProjection> routePoints = routePointQueryRepository.findAllRoutePointsByRouteId(route.id());
            int routeDistanceInMeter = route.distanceInMeters();
            RoutePointProjection currentLocationBike = findCurrentLocationFromRiders(route, routePoints);
            Optional<RoutePointProjection> currentLocationCar = routePointQueryRepository.findLastRoutePointByParticipantId(tp);

            if (currentLocationCar.isPresent()) {
                for (int i = currentLocationBike.distanceFromStartInMeter() + KILOMETER_IN_METER; i < routeDistanceInMeter; i = i + KILOMETER_IN_METER) {
                    Optional<RoutePointProjection> possibleMeetingPoint = routePointQueryRepository.findRoutePointByRouteIdAndMetersOnRoute(routeId, i);
                    if (possibleMeetingPoint.isPresent()) {
                        log.info("Possible meeting point LAT: {} LONG: {}", possibleMeetingPoint.get().latitude(), possibleMeetingPoint.get().longitude());
                        Integer durationInSecondsForCar = routePlannerGateway.calculateDuration(currentLocationCar.get(), possibleMeetingPoint.get());
                        Integer durationInSecondsForBike = calculateTimeToPoint(routePoints, route.estimatedAverageSpeed(), currentLocationBike, possibleMeetingPoint.get());

                        log.info("Duration in seconds, Bike: {}, Car: {}", durationInSecondsForBike, durationInSecondsForCar);

                        if (durationInSecondsForCar + FIVE_MINUTES < durationInSecondsForBike) {
                            log.info("Meeting point founded LAT: {} LONG: {}", possibleMeetingPoint.get().latitude(), possibleMeetingPoint.get().longitude());
                            return possibleMeetingPoint.map(routePointProjection -> GOOGLE_MAPS_URL
                                    .replace("LAT", routePointProjection.latitude().toString())
                                    .replace("LNG", routePointProjection.longitude().toString()));
                        }
                    }
                }
            } else {
                log.warn("Current location car not found");
            }
        }
        log.info("No meeting point found");
        return Optional.empty();
    }

    private RoutePointProjection findCurrentLocationFromRiders(RouteProjection route, List<RoutePointProjection> routePoints) {
        ZoneId zoneId = ZoneId.of("Europe/Brussels");
        if (route.startTime() != null) {
            log.info("Calculate points for {} from actual start time", route.id());
            long startTimeInMillis = route.startTime().plusMinutes(route.pauseInMinutes()).atZone(zoneId).toInstant().toEpochMilli();
            return calculateGhostRiderTimes(routePoints, route.averageSpeed(), startTimeInMillis);
        } else {
            log.info("Calculate points for {} from estimated start time", route.id());
            long startTimeInMillis = route.estimatedStartTime().plusMinutes(route.pauseInMinutes()).atZone(zoneId).toInstant().toEpochMilli();
            return calculateGhostRiderTimes(routePoints, route.estimatedAverageSpeed(), startTimeInMillis);
        }
    }
}
