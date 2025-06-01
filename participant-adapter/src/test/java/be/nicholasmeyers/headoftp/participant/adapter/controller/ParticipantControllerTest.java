package be.nicholasmeyers.headoftp.participant.adapter.controller;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.participant.domain.CreateParticipantRequest;
import be.nicholasmeyers.headoftp.participant.usecase.CreateParticipantUseCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.BIKER;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.BIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipantController.class)
@ContextConfiguration(classes = ParticipantTestConfig.class)
public class ParticipantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateParticipantUseCase createParticipantUseCase;

    @Nested
    class CreateParticipant {
        @Test
        void givenRequestBody_whenCreateParticipant_thenReturnNoContent() throws Exception {
            // Given
            String requestBody = """
                    {
                        "name": "Nicholas Meyers",
                        "vehicle": "BIKE",
                        "role": "BIKER",
                        "deviceId": "542"
                    }
                    """;

            when(createParticipantUseCase.createParticipant(any(CreateParticipantRequest.class)))
                    .thenReturn(Notification.empty());

            // When & Then
            mockMvc.perform(post("/participant")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isNoContent());

            ArgumentCaptor<CreateParticipantRequest> captor = ArgumentCaptor.forClass(CreateParticipantRequest.class);
            verify(createParticipantUseCase).createParticipant(captor.capture());

            CreateParticipantRequest createParticipantRequest = captor.getValue();
            assertThat(createParticipantRequest.name()).isEqualTo("Nicholas Meyers");
            assertThat(createParticipantRequest.vehicle()).isEqualTo(BIKE);
            assertThat(createParticipantRequest.role()).isEqualTo(BIKER);
            assertThat(createParticipantRequest.deviceId()).isEqualTo("542");
        }

        @Test
        void givenInvalidRequestBody_whenCreateParticipant_thenReturnNoContent() throws Exception {
            // Given
            String requestBody = """
                    {
                        "name": "Nicholas Meyers",
                        "vehicle": "BIKE",
                        "role": "BIKER",
                        "deviceId": "invalid"
                    }
                    """;

            when(createParticipantUseCase.createParticipant(any(CreateParticipantRequest.class)))
                    .thenReturn(Notification.of("error"));

            // When & Then
            mockMvc.perform(post("/participant")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest());

            ArgumentCaptor<CreateParticipantRequest> captor = ArgumentCaptor.forClass(CreateParticipantRequest.class);
            verify(createParticipantUseCase).createParticipant(captor.capture());

            CreateParticipantRequest createParticipantRequest = captor.getValue();
            assertThat(createParticipantRequest.name()).isEqualTo("Nicholas Meyers");
            assertThat(createParticipantRequest.vehicle()).isEqualTo(BIKE);
            assertThat(createParticipantRequest.role()).isEqualTo(BIKER);
            assertThat(createParticipantRequest.deviceId()).isEqualTo("invalid");
        }
    }

}
