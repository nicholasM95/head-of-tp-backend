package be.nicholasmeyers.headoftp.route.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.DoubleValidator;
import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import lombok.Getter;

@Getter
public class RoutePoint {
    private final Double latitude;
    private final Double longitude;
    private final Double altitude;

    protected RoutePoint(CreateRoutePointRequest createRoutePointRequest) {
        this.latitude = createRoutePointRequest.latitude();
        this.longitude = createRoutePointRequest.longitude();
        this.altitude = createRoutePointRequest.altitude();
    }

    protected Notification validate() {
        Notification notification = new Notification();
        DoubleValidator.notZero("latitude", latitude, notification);
        DoubleValidator.notZero("longitude", longitude, notification);
        DoubleValidator.notZero("altitude", altitude, notification);
        return notification;
    }
}
