package be.nicholasmeyers.headoftp.device.adapter.controller;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.device.domain.CreateDeviceLocationRequest;
import be.nicholasmeyers.headoftp.device.usecase.CreateDeviceLocationUseCase;
import be.nicholasmeyers.headoftp.device.usecase.FindAllDeviceIdsUseCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.json.JsonCompareMode.STRICT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
@ContextConfiguration(classes = DeviceTestConfig.class)
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateDeviceLocationUseCase createDeviceLocationUseCase;

    @MockitoBean
    private FindAllDeviceIdsUseCase findAllDeviceIdsUseCase;

    @Nested
    class CreateDeviceLocationOld {
        @Test
        void givenDeviceLocationParams_whenCreateDeviceLocationOld_thenSuccess() throws Exception {
            // Given
            String deviceId = "484897";
            String timestamp = "56846";
            String latitude = "1.2";
            String longitude = "99.2";
            String speed = "25.6";
            String bearing = "9.0";
            String altitude = "9900.3";
            String accuracy = "99.88";
            String battery = "10.0";

            when(createDeviceLocationUseCase.createDeviceLocation(any(CreateDeviceLocationRequest.class)))
                    .thenReturn(new Notification());

            // When & Then
            mockMvc.perform(post("/device/old?id={deviceId}&timestamp={timestamp}&lat={lat}&lon={lon}&speed={speed}&bearing={bearing}&altitude={altitude}&accuracy={accuracy}&batt={battery}",
                            deviceId, timestamp, latitude, longitude, speed, bearing, altitude, accuracy, battery))
                    .andExpect(status().isOk());


            CreateDeviceLocationRequest expected = new CreateDeviceLocationRequest("484897", 56846L, 1.2, 99.2, 25.6, 9.0,
                    9900.3, 99.88, 10.0);
            ArgumentCaptor<CreateDeviceLocationRequest> actual = ArgumentCaptor.forClass(CreateDeviceLocationRequest.class);
            verify(createDeviceLocationUseCase).createDeviceLocation(actual.capture());
            assertThat(actual.getValue()).isEqualTo(expected);
        }

        @Test
        void givenDeviceLocationParamsWithLongDeviceId_whenCreateDeviceLocationOld_thenSuccess() throws Exception {
            // Given
            String deviceId = "484897testtesdzefjizezd42343I8O8";
            String timestamp = "56846";
            String latitude = "1.2";
            String longitude = "99.2";
            String speed = "25.6";
            String bearing = "9.0";
            String altitude = "9900.3";
            String accuracy = "99.88";
            String battery = "10.0";

            when(createDeviceLocationUseCase.createDeviceLocation(any(CreateDeviceLocationRequest.class)))
                    .thenReturn(new Notification());

            // When & Then
            mockMvc.perform(post("/device/old?id={deviceId}&timestamp={timestamp}&lat={lat}&lon={lon}&speed={speed}&bearing={bearing}&altitude={altitude}&accuracy={accuracy}&batt={battery}",
                            deviceId, timestamp, latitude, longitude, speed, bearing, altitude, accuracy, battery))
                    .andExpect(status().isOk());


            CreateDeviceLocationRequest expected = new CreateDeviceLocationRequest("484897testtesdzefjiz", 56846L, 1.2, 99.2, 25.6, 9.0,
                    9900.3, 99.88, 10.0);
            ArgumentCaptor<CreateDeviceLocationRequest> actual = ArgumentCaptor.forClass(CreateDeviceLocationRequest.class);
            verify(createDeviceLocationUseCase).createDeviceLocation(actual.capture());
            assertThat(actual.getValue()).isEqualTo(expected);
        }

        @Test
        void givenInvalidDeviceLocationParams_whenCreateDeviceLocationOld_thenSuccess() throws Exception {
            // Given
            String deviceId = "";
            String timestamp = "56846";
            String latitude = "1.2";
            String longitude = "99.2";
            String speed = "25.6";
            String bearing = "9.0";
            String altitude = "9900.3";
            String accuracy = "99.88";
            String battery = "10.0";

            when(createDeviceLocationUseCase.createDeviceLocation(any(CreateDeviceLocationRequest.class)))
                    .thenReturn(Notification.of("error"));

            // When & Then
            mockMvc.perform(post("/device/old?id={deviceId}&timestamp={timestamp}&lat={lat}&lon={lon}&speed={speed}&bearing={bearing}&altitude={altitude}&accuracy={accuracy}&batt={battery}",
                            deviceId, timestamp, latitude, longitude, speed, bearing, altitude, accuracy, battery))
                    .andExpect(status().isOk());


            CreateDeviceLocationRequest expected = new CreateDeviceLocationRequest("", 56846L, 1.2, 99.2, 25.6, 9.0,
                    9900.3, 99.88, 10.0);
            ArgumentCaptor<CreateDeviceLocationRequest> actual = ArgumentCaptor.forClass(CreateDeviceLocationRequest.class);
            verify(createDeviceLocationUseCase).createDeviceLocation(actual.capture());
            assertThat(actual.getValue()).isEqualTo(expected);
        }
    }



    @Nested
    class CreateDeviceLocation {
        @Test
        void givenDeviceLocation_whenCreateDeviceLocation_thenSuccess() throws Exception {
            // Given
            String request = """
                    {
                        "location": {
                            "timestamp": "2025-07-08T23:01:53.561Z",
                            "coords": {
                                "latitude": 1.2,
                                "longitude": 99.2,
                                "accuracy": 99.88,
                                "speed": 25.6,
                                "heading": -1,
                                "altitude": 9900.3
                            },
                            "is_moving": false,
                            "odometer": 0,
                            "event": "motionchange",
                            "battery": {
                                "level": 10.0,
                                "is_charging": false
                            },
                            "activity": {
                                "type": "still"
                            },
                            "extras": {},
                            "_": "&id=484897&lat=1.2&lon=99.2&timestamp=2025-07-08T23:01:53.561Z&"
                        },
                        "device_id": "484897"
                    }
                    """;

            when(createDeviceLocationUseCase.createDeviceLocation(any(CreateDeviceLocationRequest.class)))
                    .thenReturn(new Notification());

            // When & Then
            mockMvc.perform(post("/device")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isOk());


            CreateDeviceLocationRequest expected = new CreateDeviceLocationRequest("484897", 1752015713561L, 1.2, 99.2, 25.6, 0.0,
                    9900.3, 99.88, 10.0);
            ArgumentCaptor<CreateDeviceLocationRequest> actual = ArgumentCaptor.forClass(CreateDeviceLocationRequest.class);
            verify(createDeviceLocationUseCase).createDeviceLocation(actual.capture());
            assertThat(actual.getValue()).isEqualTo(expected);
        }

        @Test
        void givenDeviceLocationWithLongDeviceId_whenCreateDeviceLocation_thenSuccess() throws Exception {
            // Given
            String request = """
                    {
                        "location": {
                            "timestamp": "2025-07-08T23:01:53.561Z",
                            "coords": {
                                "latitude": 1.2,
                                "longitude": 99.2,
                                "accuracy": 99.88,
                                "speed": 25.6,
                                "heading": -1,
                                "altitude": 9900.3
                            },
                            "is_moving": false,
                            "odometer": 0,
                            "event": "motionchange",
                            "battery": {
                                "level": 10.0,
                                "is_charging": false
                            },
                            "activity": {
                                "type": "still"
                            },
                            "extras": {},
                            "_": "&id=484897testtesdzefjizezd42343I8O8&lat=1.2&lon=99.2&timestamp=2025-07-08T23:01:53.561Z&"
                        },
                        "device_id": "484897testtesdzefjizezd42343I8O8"
                    }
                    """;

            when(createDeviceLocationUseCase.createDeviceLocation(any(CreateDeviceLocationRequest.class)))
                    .thenReturn(new Notification());

            // When & Then
            mockMvc.perform(post("/device")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isOk());


            CreateDeviceLocationRequest expected = new CreateDeviceLocationRequest("484897testtesdzefjiz", 1752015713561L, 1.2, 99.2, 25.6, 0.0,
                    9900.3, 99.88, 10.0);
            ArgumentCaptor<CreateDeviceLocationRequest> actual = ArgumentCaptor.forClass(CreateDeviceLocationRequest.class);
            verify(createDeviceLocationUseCase).createDeviceLocation(actual.capture());
            assertThat(actual.getValue()).isEqualTo(expected);
        }

        @Test
        void givenInvalidDeviceLocation_whenCreateDeviceLocation_thenSuccess() throws Exception {
            // Given
            String request = """
                    {
                        "location": {
                            "timestamp": "2025-07-08T23:01:53.561Z",
                            "coords": {
                                "latitude": 1.2,
                                "longitude": 99.2,
                                "accuracy": 99.88,
                                "speed": 25.6,
                                "heading": -1,
                                "altitude": 9900.3
                            },
                            "is_moving": false,
                            "odometer": 0,
                            "event": "motionchange",
                            "battery": {
                                "level": 10.0,
                                "is_charging": false
                            },
                            "activity": {
                                "type": "still"
                            },
                            "extras": {},
                            "_": "&id=&lat=1.2&lon=99.2&timestamp=2025-07-08T23:01:53.561Z&"
                        },
                        "device_id": ""
                    }
                    """;

            when(createDeviceLocationUseCase.createDeviceLocation(any(CreateDeviceLocationRequest.class)))
                    .thenReturn(Notification.of("error"));

            // When & Then
            mockMvc.perform(post("/device")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isOk());


            CreateDeviceLocationRequest expected = new CreateDeviceLocationRequest("", 1752015713561L, 1.2, 99.2, 25.6, 0.0,
                    9900.3, 99.88, 10.0);
            ArgumentCaptor<CreateDeviceLocationRequest> actual = ArgumentCaptor.forClass(CreateDeviceLocationRequest.class);
            verify(createDeviceLocationUseCase).createDeviceLocation(actual.capture());
            assertThat(actual.getValue()).isEqualTo(expected);
        }

        @Test
        void givenDeviceLocationNotificationParams_whenCreateDeviceLocation_thenSuccess() throws Exception {
            // Given

            // When & Then
            mockMvc.perform(post("/device")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("id", "nicholasa539273")
                            .param("notificationToken", "er9ZyztTT62vE28WIBiACQ%3AAPA91b..."))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class CreateDeviceLocationV2 {
        @Test
        void givenDeviceLocationV2_whenCreateDeviceLocationV2_thenSuccess() throws Exception {
            // Given
            String request = """
                    {
                        "device_id": "484897",
                        "location": {
                            "timestamp": "2025-07-08T23:01:53.561Z",
                            "coords": {
                                "latitude": 1.2,
                                "longitude": 99.2,
                                "accuracy": 99.88,
                                "speed": 25.6,
                                "bearing": 9.0,
                                "altitude": 9900.3
                            }
                        },
                        "battery": 0.78
                    }
                    """;

            when(createDeviceLocationUseCase.createDeviceLocation(any(CreateDeviceLocationRequest.class)))
                    .thenReturn(new Notification());

            // When & Then
            mockMvc.perform(post("/device/location")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isOk());

            CreateDeviceLocationRequest expected = new CreateDeviceLocationRequest("484897", 1752015713561L, 1.2, 99.2, 25.6, 9.0,
                    9900.3, 99.88, 0.78);
            ArgumentCaptor<CreateDeviceLocationRequest> actual = ArgumentCaptor.forClass(CreateDeviceLocationRequest.class);
            verify(createDeviceLocationUseCase).createDeviceLocation(actual.capture());
            assertThat(actual.getValue()).isEqualTo(expected);
        }

        @Test
        void givenInvalidDeviceLocationV2_whenCreateDeviceLocationV2_thenSuccess() throws Exception {
            // Given
            String request = """
                    {
                        "device_id": "484897",
                        "location": {
                            "timestamp": "2025-07-08T23:01:53.561Z",
                            "coords": {
                                "latitude": 1.2,
                                "longitude": 99.2,
                                "accuracy": 99.88,
                                "speed": 25.6,
                                "bearing": 9.0,
                                "altitude": 9900.3
                            }
                        },
                        "battery": 10.0
                    }
                    """;

            when(createDeviceLocationUseCase.createDeviceLocation(any(CreateDeviceLocationRequest.class)))
                    .thenReturn(Notification.of("error"));

            // When & Then
            mockMvc.perform(post("/device/location")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isOk());

            CreateDeviceLocationRequest expected = new CreateDeviceLocationRequest("484897", 1752015713561L, 1.2, 99.2, 25.6, 9.0,
                    9900.3, 99.88, 10.0);
            ArgumentCaptor<CreateDeviceLocationRequest> actual = ArgumentCaptor.forClass(CreateDeviceLocationRequest.class);
            verify(createDeviceLocationUseCase).createDeviceLocation(actual.capture());
            assertThat(actual.getValue()).isEqualTo(expected);
        }
    }

    @Nested
    class GetDeviceLocation {
        @Test
        void givenDeviceLocationParams_whenGetDeviceLocation_thenSuccess() throws Exception {
            // Given
            when(findAllDeviceIdsUseCase.findAllDeviceIds()).thenReturn(List.of("484897", "56846"));

            // When & Then
            String expectedResponse = """
            [
              {
                "id": "484897"
              },{
                "id": "56846"
              }
            ]
            """;

            mockMvc.perform(get("/device"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponse, STRICT));
        }
    }
}
