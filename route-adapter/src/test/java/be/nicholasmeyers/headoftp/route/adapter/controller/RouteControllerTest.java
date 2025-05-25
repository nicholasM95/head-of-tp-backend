package be.nicholasmeyers.headoftp.route.adapter.controller;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.route.domain.CreateRoutePointRequest;
import be.nicholasmeyers.headoftp.route.projection.RoutePointProjection;
import be.nicholasmeyers.headoftp.route.projection.RouteProjection;
import be.nicholasmeyers.headoftp.route.usecase.CreateRouteUseCase;
import be.nicholasmeyers.headoftp.route.usecase.DeleteRouteUseCase;
import be.nicholasmeyers.headoftp.route.usecase.FindAllRoutePointsByRouteIdUseCase;
import be.nicholasmeyers.headoftp.route.usecase.FindAllRoutesUseCase;
import be.nicholasmeyers.headoftp.route.usecase.FindRoutePointCenterByRouteIdUseCase;
import be.nicholasmeyers.headoftp.route.usecase.PatchRouteUseCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.route.projection.RouteProjectionMother.createRouteProjection1;
import static be.nicholasmeyers.headoftp.route.projection.RouteProjectionMother.createRouteProjection2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.json.JsonCompareMode.STRICT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouteController.class)
@ContextConfiguration(classes = RouteTestConfig.class)
public class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateRouteUseCase createRouteUseCase;

    @MockitoBean
    private DeleteRouteUseCase deleteRouteUseCase;

    @MockitoBean
    private PatchRouteUseCase patchRouteUseCase;

    @MockitoBean
    private FindAllRoutesUseCase findAllRoutesUseCase;

    @MockitoBean
    private FindAllRoutePointsByRouteIdUseCase findAllRoutePointsByRouteIdUseCase;

    @MockitoBean
    private FindRoutePointCenterByRouteIdUseCase findRoutePointCenterByRouteIdUseCase;

    @Nested
    class CreateRoute {
        @Test
        void givenGpxFile_whenCreateRoute_thenReturnNoContent() throws Exception {
            // Given
            ClassPathResource resource = new ClassPathResource("test.gpx");
            String gpxContent = Files.readString(resource.getFile().toPath());

            Notification notification = new Notification();
            when(createRouteUseCase.createRoute(anyString(), anyList())).thenReturn(notification);

            // When & Then
            mockMvc.perform(post("/route")
                            .contentType("application/gpx+xml")
                            .content(gpxContent))
                    .andExpect(status().isNoContent());

            ArgumentCaptor<List<CreateRoutePointRequest>> captor = ArgumentCaptor.forClass(List.class);
            verify(createRouteUseCase).createRoute(eq("104 KM // N Gravel"), captor.capture());
            List<CreateRoutePointRequest> actualRoutePoints = captor.getValue();
            assertThat(actualRoutePoints).hasSize(1866);
        }

        @Test
        void givenInvalidGpxFile_whenCreateRoute_thenReturnNoContent() throws Exception {
            // Given
            ClassPathResource resource = new ClassPathResource("test.gpx");
            String gpxContent = Files.readString(resource.getFile().toPath());

            Notification notification = Notification.of("error", "err");
            when(createRouteUseCase.createRoute(anyString(), anyList())).thenReturn(notification);

            // When & Then
            mockMvc.perform(post("/route")
                            .contentType("application/gpx+xml")
                            .content(gpxContent))
                    .andExpect(status().isBadRequest());

            ArgumentCaptor<List<CreateRoutePointRequest>> captor = ArgumentCaptor.forClass(List.class);
            verify(createRouteUseCase).createRoute(eq("104 KM // N Gravel"), captor.capture());
            List<CreateRoutePointRequest> actualRoutePoints = captor.getValue();
            assertThat(actualRoutePoints).hasSize(1866);
        }

        @Test
        void givenTextFile_whenCreateRoute_thenReturnNoContent() throws Exception {
            // Given
            ClassPathResource resource = new ClassPathResource("test.txt");
            String gpxContent = Files.readString(resource.getFile().toPath());

            // When & Then
            mockMvc.perform(post("/route")
                            .contentType("application/xml")
                            .content(gpxContent))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(createRouteUseCase);
        }
    }

    @Nested
    class DeleteRouteByRouteId {
        @Test
        void given_whenDeleteRouteByRouteId_thenRouteDeleted() throws Exception {
            // Given

            // When & Then
            mockMvc.perform(delete("/route/{routeId}", "930f9c57-ffe7-4d10-a33d-9f3d728c1b08")
                            .contentType("application/json"))
                    .andExpect(status().isNoContent());

            verify(deleteRouteUseCase).deleteRoute(UUID.fromString("930f9c57-ffe7-4d10-a33d-9f3d728c1b08"));
        }
    }

    @Nested
    class PatchRouteByRouteId {
        @Test
        void givenPatchRequest_whenPatchRouteByRouteId_thenCallPatchRoute() throws Exception {
            // Given
            String request = """
                    {
                        "estimatedAverageSpeed": 30.5,
                        "estimatedStartTime": "2025-05-23T12:00:00Z",
                        "pauseInMinutes": "45"
                    }
                    """;

            // When & Then
            mockMvc.perform(patch("/route/{routeId}", "930f9c57-ffe7-4d10-a33d-9f3d728c1b08")
                            .contentType("application/json")
                            .content(request))
                    .andExpect(status().isNoContent());

            verify(patchRouteUseCase)
                    .patchRoute(UUID.fromString("930f9c57-ffe7-4d10-a33d-9f3d728c1b08"), 30.5,
                            LocalDateTime.of(2025, 5, 23, 12, 0), 45);
        }
    }

    @Nested
    class GetRoutes {
        @Test
        void givenRoutes_whenGetRoutes_thenReturnRoutes() throws Exception {
            // Given
            RouteProjection routeProjection1 = createRouteProjection1();
            RouteProjection routeProjection2 = createRouteProjection2();

            when(findAllRoutesUseCase.findAll()).thenReturn(List.of(routeProjection1, routeProjection2));

            // When & Then
            String expectedResponse = """
                    [
                        {
                            "id": "e0483c47-0aa0-442d-808b-8897687f4af2",
                            "name": "route-name 1",
                            "elevationGain": 1200,
                            "estimatedAverageSpeed": 24.4,
                            "distanceInMeters": 12000,
                            "durationInMinutes": 180,
                            "estimatedStartTime": "2025-05-20T12:00:00+02:00",
                            "estimatedEndTime": "2025-05-20T14:00:00+02:00",
                            "pauseInMinutes": 5,
                            "createDate": "2025-05-19T12:00:00+02:00",
                            "lastModifiedDate": "2025-05-19T13:02:00+02:00",
                            "startTime": "2025-05-20T12:05:00+02:00",
                            "averageSpeed": 35.1
                        },
                        {
                            "id": "74d9a93b-7b7b-4ca7-a107-64bc70172c2e",
                            "name": "route-name 2",
                            "elevationGain": 1400,
                            "estimatedAverageSpeed": 27.4,
                            "distanceInMeters": 14000,
                            "durationInMinutes": 210,
                            "estimatedStartTime": "2025-05-22T12:00:00+02:00",
                            "estimatedEndTime": "2025-05-22T14:00:00+02:00",
                            "pauseInMinutes": 30,
                            "createDate": "2025-05-21T12:00:00+02:00",
                            "lastModifiedDate": "2025-05-21T13:02:00+02:00",
                            "startTime": "2025-05-22T12:05:00+02:00",
                            "averageSpeed": 30.1
                        }
                    ]
                    """;

            mockMvc.perform(get("/route")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponse, STRICT));

            verify(findAllRoutesUseCase).findAll();
        }
    }

    @Nested
    class GetRoutePointByRouteId {
        @Test
        void givenRoutePoints_whenGetRoutePointByRouteId_thenReturnRoutePoints() throws Exception {
            // Given
            RoutePointProjection routePointProjection1 = new RoutePointProjection(54.5, 90.1, 12.2);
            RoutePointProjection routePointProjection2 = new RoutePointProjection(54.6, 91.6, 12.7);

            when(findAllRoutePointsByRouteIdUseCase.findAllRoutePointsByRouteId(any(UUID.class)))
                    .thenReturn(List.of(routePointProjection1, routePointProjection2));

            // When & Then
            String expectedResponse = """
                    [
                      {
                        "latitude": 54.5,
                        "longitude": 90.1,
                        "altitude": 12.2
                      },
                      {
                        "latitude": 54.6,
                        "longitude": 91.6,
                        "altitude": 12.7
                      }
                    ]
                    """;

            mockMvc.perform(get("/route-point/{routeId}", "03d3c4ee-cb56-44f8-935b-d360a0432e85")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponse, STRICT));

            verify(findAllRoutePointsByRouteIdUseCase).findAllRoutePointsByRouteId(UUID.fromString("03d3c4ee-cb56-44f8-935b-d360a0432e85"));
        }
    }

    @Nested
    class GetRoutePointCenterByRouteId {
        @Test
        void givenRoutePoint_whenGetRoutePointCenterByRouteId_thenReturnRoutePoint() throws Exception {
            RoutePointProjection routePointProjection = new RoutePointProjection(54.6, 91.6, 12.3);

            when(findRoutePointCenterByRouteIdUseCase.findRoutePointCenterByRouteId(any(UUID.class)))
                    .thenReturn(Optional.of(routePointProjection));

            // When & Then
            String expectedResponse = """
                    {
                      "latitude": 54.6,
                      "longitude": 91.6,
                      "altitude": 12.3
                    }
                    """;

            mockMvc.perform(get("/route-point/center/{routeId}", "03d3c4ee-cb56-44f8-935b-d360a0432e85")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponse, STRICT));

            verify(findRoutePointCenterByRouteIdUseCase).findRoutePointCenterByRouteId(UUID.fromString("03d3c4ee-cb56-44f8-935b-d360a0432e85"));
        }

        @Test
        void givenNoRoutePoint_whenGetRoutePointCenterByRouteId_thenReturnRoutePointCenterBrussels() throws Exception {

            when(findRoutePointCenterByRouteIdUseCase.findRoutePointCenterByRouteId(any(UUID.class)))
                    .thenReturn(Optional.empty());

            // When & Then
            String expectedResponse = """
                    {
                      "latitude": 50.8467,
                      "longitude": 4.3499,
                      "altitude": 0.0
                    }
                    """;

            mockMvc.perform(get("/route-point/center/{routeId}", "03d3c4ee-cb56-44f8-935b-d360a0432e85")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponse, STRICT));

            verify(findRoutePointCenterByRouteIdUseCase).findRoutePointCenterByRouteId(UUID.fromString("03d3c4ee-cb56-44f8-935b-d360a0432e85"));
        }
    }
}
