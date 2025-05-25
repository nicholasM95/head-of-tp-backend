package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.route.domain.CreateRoutePointRequest;
import be.nicholasmeyers.headoftp.route.domain.CreateRouteRequest;
import be.nicholasmeyers.headoftp.route.domain.Route;
import be.nicholasmeyers.headoftp.route.domain.RouteFactory;
import be.nicholasmeyers.headoftp.route.domain.RoutePoint;
import be.nicholasmeyers.headoftp.route.domain.RoutePointFactory;
import be.nicholasmeyers.headoftp.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class CreateRouteUseCase {

    private final RouteRepository routeRepository;

    public Notification createRoute(String name, List<CreateRoutePointRequest> createRoutePointRequests) {
        log.info("Create route with name {} and {} points", name, createRoutePointRequests.size());
        List<Creation<RoutePoint>> creations = createRoutePointRequests.stream()
                .map(RoutePointFactory::createRoutePoint)
                .toList();

        List<Notification> notifications = creations.stream()
                .map(Creation::getNotification)
                .toList();

        Notification notification = Notification.merge(notifications);

        if (notification.hasErrors()) {
            return notification;
        }

        CreateRouteRequest createRouteRequest = new CreateRouteRequest(name, creations.stream().map(Creation::getValue).toList());
        Creation<Route> route = RouteFactory.createRoute(createRouteRequest);

        if (route.getNotification().hasErrors()) {
            return route.getNotification();
        }

        routeRepository.createRoute(route.getValue());
        return notification;
    }
}
