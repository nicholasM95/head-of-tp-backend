package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.projection.RouteProjection;
import be.nicholasmeyers.headoftp.route.repository.RouteQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class FindAllRoutesUseCase {

    private final RouteQueryRepository routeQueryRepository;

    public List<RouteProjection> findAll() {
        log.info("Find all routes");
        return routeQueryRepository.findAllRoutes();
    }
}
