package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class DeleteRouteUseCase {

    private final RouteRepository routeRepository;

    public void deleteRoute(UUID routeId) {
        log.info("Delete route with id: {}", routeId);
        routeRepository.deleteRouteByRouteId(routeId);
    }
}
