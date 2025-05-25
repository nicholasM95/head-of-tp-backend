package be.nicholasmeyers.headoftp.route.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RouteFactory {

    public static Creation<Route> createRoute(CreateRouteRequest createRouteRequest) {
        Route route = new Route(createRouteRequest);
        return Creation.of(route, route.validate());
    }
}
