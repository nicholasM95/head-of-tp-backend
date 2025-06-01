package be.nicholasmeyers.headoftp.participant.usecase;

import be.nicholasmeyers.headoftp.participant.domain.CreateParticipantRequest;
import be.nicholasmeyers.headoftp.participant.domain.Participant;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantFactory;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantRole;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle;
import be.nicholasmeyers.headoftp.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.BIKER;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.TP;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.BIKE;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.CAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatchParticipantUseCaseTest {

    @InjectMocks
    private PatchParticipantUseCase patchParticipantUseCase;

    @Mock
    private ParticipantRepository participantRepository;

    @Nested
    class PatchParticipant {
        @Test
        void givenNameAndDeviceIdAndVehicleAndRole_whenPatchParticipant_thenParticipantUpdated() {
            // Given
            UUID participantId = UUID.fromString("74eda9fe-9d6b-4f9e-9c9a-3d6918a63065");
            String name = "Nicholas Meyers";
            String deviceId = "8913";
            ParticipantVehicle vehicle = CAR;
            ParticipantRole role = TP;

            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Jos", BIKE, BIKER, "111");
            Participant participant = ParticipantFactory.createParticipant(createParticipantRequest).getValue();
            when(participantRepository.findParticipantById(any(UUID.class)))
                    .thenReturn(Optional.of(participant));

            // When
            patchParticipantUseCase.patchParticipant(participantId, name, deviceId, vehicle, role);

            // Then
            verify(participantRepository).findParticipantById(UUID.fromString("74eda9fe-9d6b-4f9e-9c9a-3d6918a63065"));
            ArgumentCaptor<Participant> participantCaptor = ArgumentCaptor.forClass(Participant.class);
            verify(participantRepository).updateParticipant(participantCaptor.capture());

            assertThat(participantCaptor.getValue().getName()).isEqualTo("Nicholas Meyers");
            assertThat(participantCaptor.getValue().getDeviceId()).isEqualTo("8913");
            assertThat(participantCaptor.getValue().getVehicle()).isEqualTo(CAR);
            assertThat(participantCaptor.getValue().getRole()).isEqualTo(TP);
        }

        @Test
        void givenNameAndDeviceIdAndVehicle_whenPatchParticipant_thenParticipantUpdated() {
            // Given
            UUID participantId = UUID.fromString("74eda9fe-9d6b-4f9e-9c9a-3d6918a63065");
            String name = "Nicholas Meyers";
            String deviceId = "8913";
            ParticipantVehicle vehicle = CAR;

            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Jos", BIKE, BIKER, "111");
            Participant participant = ParticipantFactory.createParticipant(createParticipantRequest).getValue();
            when(participantRepository.findParticipantById(any(UUID.class)))
                    .thenReturn(Optional.of(participant));

            // When
            patchParticipantUseCase.patchParticipant(participantId, name, deviceId, vehicle, null);

            // Then
            verify(participantRepository).findParticipantById(UUID.fromString("74eda9fe-9d6b-4f9e-9c9a-3d6918a63065"));
            ArgumentCaptor<Participant> participantCaptor = ArgumentCaptor.forClass(Participant.class);
            verify(participantRepository).updateParticipant(participantCaptor.capture());

            assertThat(participantCaptor.getValue().getName()).isEqualTo("Nicholas Meyers");
            assertThat(participantCaptor.getValue().getDeviceId()).isEqualTo("8913");
            assertThat(participantCaptor.getValue().getVehicle()).isEqualTo(CAR);
            assertThat(participantCaptor.getValue().getRole()).isEqualTo(BIKER);
        }

        @Test
        void givenNameAndDeviceId_whenPatchParticipant_thenParticipantUpdated() {
            // Given
            UUID participantId = UUID.fromString("74eda9fe-9d6b-4f9e-9c9a-3d6918a63065");
            String name = "Nicholas Meyers";
            String deviceId = "8913";

            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Jos", BIKE, BIKER, "111");
            Participant participant = ParticipantFactory.createParticipant(createParticipantRequest).getValue();
            when(participantRepository.findParticipantById(any(UUID.class)))
                    .thenReturn(Optional.of(participant));

            // When
            patchParticipantUseCase.patchParticipant(participantId, name, deviceId, null, null);

            // Then
            verify(participantRepository).findParticipantById(UUID.fromString("74eda9fe-9d6b-4f9e-9c9a-3d6918a63065"));
            ArgumentCaptor<Participant> participantCaptor = ArgumentCaptor.forClass(Participant.class);
            verify(participantRepository).updateParticipant(participantCaptor.capture());

            assertThat(participantCaptor.getValue().getName()).isEqualTo("Nicholas Meyers");
            assertThat(participantCaptor.getValue().getDeviceId()).isEqualTo("8913");
            assertThat(participantCaptor.getValue().getVehicle()).isEqualTo(BIKE);
            assertThat(participantCaptor.getValue().getRole()).isEqualTo(BIKER);
        }

        @Test
        void givenName_whenPatchParticipant_thenParticipantUpdated() {
            // Given
            UUID participantId = UUID.fromString("74eda9fe-9d6b-4f9e-9c9a-3d6918a63065");
            String name = "Nicholas Meyers";

            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Jos", BIKE, BIKER, "111");
            Participant participant = ParticipantFactory.createParticipant(createParticipantRequest).getValue();
            when(participantRepository.findParticipantById(any(UUID.class)))
                    .thenReturn(Optional.of(participant));

            // When
            patchParticipantUseCase.patchParticipant(participantId, name, null, null, null);

            // Then
            verify(participantRepository).findParticipantById(UUID.fromString("74eda9fe-9d6b-4f9e-9c9a-3d6918a63065"));
            ArgumentCaptor<Participant> participantCaptor = ArgumentCaptor.forClass(Participant.class);
            verify(participantRepository).updateParticipant(participantCaptor.capture());

            assertThat(participantCaptor.getValue().getName()).isEqualTo("Nicholas Meyers");
            assertThat(participantCaptor.getValue().getDeviceId()).isEqualTo("111");
            assertThat(participantCaptor.getValue().getVehicle()).isEqualTo(BIKE);
            assertThat(participantCaptor.getValue().getRole()).isEqualTo(BIKER);
        }

        @Test
        void given_whenPatchParticipant_thenParticipantNotUpdated() {
            // Given
            UUID participantId = UUID.fromString("74eda9fe-9d6b-4f9e-9c9a-3d6918a63065");
            String name = "Nicholas Meyers";

            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Jos", BIKE, BIKER, "111");
            Participant participant = ParticipantFactory.createParticipant(createParticipantRequest).getValue();
            when(participantRepository.findParticipantById(any(UUID.class)))
                    .thenReturn(Optional.of(participant));

            // When
            patchParticipantUseCase.patchParticipant(participantId, null, null, null, null);

            // Then
            verify(participantRepository).findParticipantById(UUID.fromString("74eda9fe-9d6b-4f9e-9c9a-3d6918a63065"));
            verifyNoMoreInteractions(participantRepository);
        }
    }
}
