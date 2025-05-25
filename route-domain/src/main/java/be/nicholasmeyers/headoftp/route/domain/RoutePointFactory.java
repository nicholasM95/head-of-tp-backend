package be.nicholasmeyers.headoftp.route.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutePointFactory {

    public static Creation<RoutePoint> createRoutePoint(CreateRoutePointRequest createRoutePointRequest) {
        RoutePoint routePoint = new RoutePoint(createRoutePointRequest);
        return Creation.of(routePoint, routePoint.validate());
    }
}
