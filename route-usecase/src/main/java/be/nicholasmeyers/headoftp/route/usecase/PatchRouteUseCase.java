package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.domain.Route;
import be.nicholasmeyers.headoftp.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PatchRouteUseCase {

    private final RouteRepository routeRepository;

    public void patchRoute(UUID routeId, Double estimatedAverageSpeed, LocalDateTime estimatedStartTime, Integer pauseInMinutes) {
        Optional<Route> route = routeRepository.findRouteByRouteId(routeId);
        if (route.isPresent()) {
            if (estimatedAverageSpeed != null) {
                route.get().setEstimatedAverageSpeed(estimatedAverageSpeed);
            }

            if (estimatedStartTime != null) {
                route.get().setEstimatedStartTime(estimatedStartTime);
            }

            if (pauseInMinutes != null) {
                route.get().setPauseInMinutes(pauseInMinutes);
            }

            if (estimatedAverageSpeed != null || estimatedStartTime != null || pauseInMinutes != null) {
                routeRepository.updateRoute(route.get());
            }
        }
    }
}
