package be.nicholasmeyers.headoftp.route.adapter.controller;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.route.adapter.resource.PatchRouteRequestResource;
import be.nicholasmeyers.headoftp.route.adapter.resource.RoutePointResponseResource;
import be.nicholasmeyers.headoftp.route.adapter.resource.RouteResponseResource;
import be.nicholasmeyers.headoftp.route.domain.CreateRoutePointRequest;
import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.projection.RouteProjection;
import be.nicholasmeyers.headoftp.route.usecase.CreateRouteUseCase;
import be.nicholasmeyers.headoftp.route.usecase.DeleteRouteUseCase;
import be.nicholasmeyers.headoftp.route.usecase.FindAllRoutePointsByRouteIdUseCase;
import be.nicholasmeyers.headoftp.route.usecase.FindAllRoutesUseCase;
import be.nicholasmeyers.headoftp.route.usecase.FindRoutePointCenterByRouteIdUseCase;
import be.nicholasmeyers.headoftp.route.usecase.PatchRouteUseCase;
//import io.jenetics.jpx.GPX;
//import io.jenetics.jpx.Metadata;
//import io.jenetics.jpx.Track;
//import io.jenetics.jpx.TrackSegment;
//import io.jenetics.jpx.WayPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = {"https://headoftp.com", "http://localhost:5173"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class RouteController implements RouteApi {

    private static final String MIME_TYPE_XML = "application/xml";
    private static final String MIME_TYPE_XML_GPX = "application/gpx+xml";
    private static final RoutePointProjection BRUSSELS_CENTER = new RoutePointProjection(50.8467, 4.3499, 0.0);

    private final Tika tika = new Tika();

    private final CreateRouteUseCase createRouteUseCase;
    private final DeleteRouteUseCase deleteRouteUseCase;
    private final PatchRouteUseCase patchRouteUseCase;
    private final FindAllRoutesUseCase findAllRoutesUseCase;
    private final FindAllRoutePointsByRouteIdUseCase findAllRoutePointsByRouteIdUseCase;
    private final FindRoutePointCenterByRouteIdUseCase findRoutePointCenterByRouteIdUseCase;

    @Override
    public ResponseEntity<Void> createRoute(Resource body) {
        try (InputStream inputStream = body.getInputStream()) {
            String mimeType = tika.detect(inputStream);
            if (!mimeType.equals(MIME_TYPE_XML) && !mimeType.equals(MIME_TYPE_XML_GPX)) {
                log.error("Invalid mime type: {}", mimeType);
                return ResponseEntity.badRequest().build();
            }

            /*GPX gpx = getGPX(inputStream);

            String name = getRouteName(gpx);
            List<CreateRoutePointRequest> createRoutePointRequests = getRoute(gpx);*/

            Notification notification = createRouteUseCase.createRoute("name", List.of());
            if (notification.hasErrors()) {
                log.error(notification.getErrors().toString());
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteRouteByRouteId(UUID routeId) {
        deleteRouteUseCase.deleteRoute(routeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<RouteResponseResource>> getRoutes() {
        return ResponseEntity.ok(findAllRoutesUseCase.findAll().stream().map(this::map).toList());
    }

    @Override
    public ResponseEntity<Void> patchRouteByRouteId(UUID routeId, PatchRouteRequestResource patchRouteRequestResource) {
        patchRouteUseCase.patchRoute(routeId, patchRouteRequestResource.getEstimatedAverageSpeed(),
                patchRouteRequestResource.getEstimatedStartTime().toLocalDateTime(), patchRouteRequestResource.getPauseInMinutes());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<RoutePointResponseResource>> getRoutePointByRouteId(UUID routeId) {
        return ResponseEntity.ok(findAllRoutePointsByRouteIdUseCase.findAllRoutePointsByRouteId(routeId).stream()
                .map(this::map)
                .toList());
    }

    @Override
    public ResponseEntity<RoutePointResponseResource> getRoutePointCenterByRouteId(UUID routeId) {
        return ResponseEntity.ok(map(findRoutePointCenterByRouteIdUseCase.findRoutePointCenterByRouteId(routeId)
                .orElse(BRUSSELS_CENTER)));
    }

    private RouteResponseResource map(RouteProjection routeProjection) {
        ZoneId zone = ZoneId.of("Europe/Brussels");

        return RouteResponseResource.builder()
                .id(routeProjection.id())
                .name(routeProjection.name())
                .elevationGain(routeProjection.elevationGain())
                .estimatedAverageSpeed(routeProjection.estimatedAverageSpeed())
                .distanceInMeters(routeProjection.distanceInMeters())
                .durationInMinutes(routeProjection.durationInMinutes())
                .estimatedStartTime(routeProjection.estimatedStartTime().atZone(zone).toOffsetDateTime())
                .estimatedEndTime(routeProjection.estimatedEndTime().atZone(zone).toOffsetDateTime())
                .pauseInMinutes(routeProjection.pauseInMinutes())
                .startTime(Optional.ofNullable(routeProjection.startTime()).map(startTime -> startTime.atZone(zone).toOffsetDateTime()).orElse(null))
                .averageSpeed(routeProjection.averageSpeed())
                .createDate(routeProjection.createDate().atZone(zone).toOffsetDateTime())
                .lastModifiedDate(routeProjection.lastModifiedDate().atZone(zone).toOffsetDateTime())
                .build();
    }

    private RoutePointResponseResource map(RoutePointProjection routePointProjection) {
        return RoutePointResponseResource.builder()
                .latitude(routePointProjection.latitude())
                .longitude(routePointProjection.longitude())
                .altitude(routePointProjection.altitude())
                .build();
    }

    /*private GPX getGPX(InputStream inputStream) throws IOException {
        return GPX.Reader.DEFAULT.read(inputStream);
    }

    private String getRouteName(GPX gpx) {
        Optional<Metadata> metadata = gpx.getMetadata();
        if (metadata.isPresent()) {
            Optional<String> name = metadata.get().getName();
            if (name.isPresent()) {
                return name.get();
            }
        }
        return UUID.randomUUID().toString();
    }

    private List<CreateRoutePointRequest> getRoute(GPX gpx) {
        return gpx.tracks()
                .flatMap(Track::segments)
                .flatMap(TrackSegment::points)
                .map(this::mapToRoutePoint)
                .toList();
    }

    private CreateRoutePointRequest mapToRoutePoint(WayPoint wayPoint) {
        double altitude = 0;
        if (wayPoint.getElevation().isPresent()) {
            altitude = wayPoint.getElevation().get().doubleValue();
        }
        return new CreateRoutePointRequest(wayPoint.getLatitude().doubleValue(), wayPoint.getLongitude().doubleValue(), altitude);
    }*/
}
