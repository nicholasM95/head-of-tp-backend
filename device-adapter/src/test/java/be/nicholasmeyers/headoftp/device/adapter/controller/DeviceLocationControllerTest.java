package be.nicholasmeyers.headoftp.device.adapter.controller;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.device.domain.CreateDeviceLocationRequest;
import be.nicholasmeyers.headoftp.device.usecase.CreateDeviceLocationUseCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceLocationController.class)
@ContextConfiguration(classes = DeviceTestConfig.class)
public class DeviceLocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateDeviceLocationUseCase createDeviceLocationUseCase;

    @Nested
    class CreateDeviceLocation {
        @Test
        void givenDeviceLocationParams_whenCreateDeviceLocation_thenSuccess() throws Exception {
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
            mockMvc.perform(post("/device?id={deviceId}&timestamp={timestamp}&lat={lat}&lon={lon}&speed={speed}&bearing={bearing}&altitude={altitude}&accuracy={accuracy}&batt={battery}",
                            deviceId, timestamp, latitude, longitude, speed, bearing, altitude, accuracy, battery))
                    .andExpect(status().isOk());


            CreateDeviceLocationRequest expected = new CreateDeviceLocationRequest("484897", 56846L, 1.2, 99.2, 25.6, 9.0,
                    9900.3, 99.88, 10.0);
            ArgumentCaptor<CreateDeviceLocationRequest> actual = ArgumentCaptor.forClass(CreateDeviceLocationRequest.class);
            verify(createDeviceLocationUseCase).createDeviceLocation(actual.capture());
            assertThat(actual.getValue()).isEqualTo(expected);
        }

        @Test
        void givenInvalidDeviceLocationParams_whenCreateDeviceLocation_thenSuccess() throws Exception {
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
            mockMvc.perform(post("/device?id={deviceId}&timestamp={timestamp}&lat={lat}&lon={lon}&speed={speed}&bearing={bearing}&altitude={altitude}&accuracy={accuracy}&batt={battery}",
                            deviceId, timestamp, latitude, longitude, speed, bearing, altitude, accuracy, battery))
                    .andExpect(status().isOk());


            CreateDeviceLocationRequest expected = new CreateDeviceLocationRequest("", 56846L, 1.2, 99.2, 25.6, 9.0,
                    9900.3, 99.88, 10.0);
            ArgumentCaptor<CreateDeviceLocationRequest> actual = ArgumentCaptor.forClass(CreateDeviceLocationRequest.class);
            verify(createDeviceLocationUseCase).createDeviceLocation(actual.capture());
            assertThat(actual.getValue()).isEqualTo(expected);
        }
    }
}
