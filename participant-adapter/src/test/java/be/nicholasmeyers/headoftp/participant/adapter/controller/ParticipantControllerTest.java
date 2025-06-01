package be.nicholasmeyers.headoftp.participant.adapter.controller;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.participant.domain.CreateParticipantRequest;
import be.nicholasmeyers.headoftp.participant.projection.ParticipantProjection;
import be.nicholasmeyers.headoftp.participant.usecase.CreateParticipantUseCase;
import be.nicholasmeyers.headoftp.participant.usecase.DeleteParticipantUseCase;
import be.nicholasmeyers.headoftp.participant.usecase.FindAllParticipantUseCase;
import be.nicholasmeyers.headoftp.participant.usecase.PatchParticipantUseCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.RIDER;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.TP;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.BIKE;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.CAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.json.JsonCompareMode.STRICT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipantController.class)
@ContextConfiguration(classes = ParticipantTestConfig.class)
public class ParticipantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateParticipantUseCase createParticipantUseCase;

    @MockitoBean
    private FindAllParticipantUseCase findAllParticipantUseCase;

    @MockitoBean
    private PatchParticipantUseCase patchParticipantUseCase;

    @MockitoBean
    private DeleteParticipantUseCase deleteParticipantUseCase;

    @Nested
    class CreateParticipant {
        @Test
        void givenRequestBody_whenCreateParticipant_thenReturnNoContent() throws Exception {
            // Given
            String requestBody = """
                    {
                        "name": "Nicholas Meyers",
                        "vehicle": "BIKE",
                        "role": "RIDER",
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
            assertThat(createParticipantRequest.role()).isEqualTo(RIDER);
            assertThat(createParticipantRequest.deviceId()).isEqualTo("542");
        }

        @Test
        void givenInvalidRequestBody_whenCreateParticipant_thenReturnNoContent() throws Exception {
            // Given
            String requestBody = """
                    {
                        "name": "Nicholas Meyers",
                        "vehicle": "BIKE",
                        "role": "RIDER",
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
            assertThat(createParticipantRequest.role()).isEqualTo(RIDER);
            assertThat(createParticipantRequest.deviceId()).isEqualTo("invalid");
        }
    }

    @Nested
    class FindAllParticipants {
        @Test
        void given_whenFindAllParticipants_thenReturnParticipants() throws Exception {
            // Given

            when(findAllParticipantUseCase.findAllParticipants())
                    .thenReturn(List.of(new ParticipantProjection(UUID.fromString("bec6a70a-402c-4957-b47f-fa49abbfd416"),
                            "Nicholas Meyers",
                            "678294",
                            CAR,
                            TP,
                            LocalDateTime.of(2025, 5, 1, 12, 30),
                            LocalDateTime.of(2025, 5, 1, 12, 30))));

            // When & Then
            String expectedResponse = """
                    [
                      {
                        "id": "bec6a70a-402c-4957-b47f-fa49abbfd416",
                        "name": "Nicholas Meyers",
                        "deviceId": "678294",
                        "vehicle": "CAR",
                        "role": "TP",
                        "createDate": "2025-05-01T12:30:00+02:00",
                        "lastModifiedDate": "2025-05-01T12:30:00+02:00"
                      }
                    ]
                    """;

            mockMvc.perform(get("/participant"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponse, STRICT));
        }
    }

    @Nested
    class PatchParticipantById {
        @Test
        void givenPatchParticipantRequest_whenPatchParticipantById_thenParticipantPatched() throws Exception {
            // Given
            String requestBody = """
                    {
                        "name": "Nicholas Meyers",
                        "vehicle": "BIKE",
                        "role": "RIDER",
                        "deviceId": "23414"
                    }
                    """;

            // When & Then
            mockMvc.perform(patch("/participant/{id}", "ddd2121d-2cd6-4f0b-8604-cc6987cd6a9f")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isNoContent());

            verify(patchParticipantUseCase)
                    .patchParticipant(UUID.fromString("ddd2121d-2cd6-4f0b-8604-cc6987cd6a9f"), "Nicholas Meyers", "23414", BIKE, RIDER);
        }
    }

    @Nested
    class DeleteParticipantById {
        @Test
        void givenId_whenDeleteParticipantById_thenParticipantDeleted() throws Exception {
            // Given
            UUID id = UUID.randomUUID();

            // When & Then
            mockMvc.perform(delete("/participant/{id}", id.toString()))
                    .andExpect(status().isNoContent());

            verify(deleteParticipantUseCase).deleteParticipant(id);
        }
    }
}
