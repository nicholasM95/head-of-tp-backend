package be.nicholasmeyers.headoftp.route.adapter.client;

import be.nicholasmeyers.headoftp.route.gateway.RoutePlannerGateway;
import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Component
public class RoutePlannerClient implements RoutePlannerGateway {

    private static final String URL_MAPBOX = "https://api.mapbox.com/directions/v5/mapbox/driving/LONG1,LAT1;LONG2,LAT2?access_token=ACCESS_TOKEN";

    private final WebClient webClient;
    private final String accessToken;

    public RoutePlannerClient(@Value("${mapbox.access-token}")String accessToken) {
        this.webClient = WebClient.create();
        this.accessToken = accessToken;
    }

    @Override
    public Integer calculateDuration(RoutePointProjection point1, RoutePointProjection point2) {
        try {
            String url = URL_MAPBOX
                    .replace("LONG1", point1.longitude().toString())
                    .replace("LAT1", point1.latitude().toString())
                    .replace("LONG2", point2.longitude().toString())
                    .replace("LAT2", point2.latitude().toString())
                    .replace("ACCESS_TOKEN", accessToken);
            URI uri = new URI(url);
            RouteInfoClientResponseResource response = webClient.get().uri(uri).retrieve()
                    .bodyToMono(RouteInfoClientResponseResource.class).block();
            if (response == null) {
                log.error("Could not get route info from mapbox");
                return 0;
            }
            if (response.routes != null && response.routes.size() == 1) {
                return (int) Math.floor(response.routes.getFirst().duration);
            }
            log.error("Could not get routes from mapbox");
            return 0;
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        } catch (WebClientResponseException e) {
            log.error("Could not get routes from mapbox status code {}", e.getStatusCode());
            return 0;
        }
    }

    private record RouteInfoClientResponseResource(List<RouteClientResponseResource> routes, String code) {

    }

    private record RouteClientResponseResource(Double duration) {

    }
}
