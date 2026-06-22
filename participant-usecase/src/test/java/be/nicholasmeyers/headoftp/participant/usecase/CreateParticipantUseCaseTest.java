package be.nicholasmeyers.headoftp.participant.usecase;

import be.nicholasmeyers.headoftp.participant.domain.CreateParticipantRequest;
import be.nicholasmeyers.headoftp.participant.domain.Participant;
import be.nicholasmeyers.headoftp.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.TP;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.CAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class CreateParticipantUseCaseTest {

    @InjectMocks
    private CreateParticipantUseCase createParticipantUseCase;

    @Mock
    private ParticipantRepository participantRepository;

    @Nested
    class CreateParticipant {
        @Test
        void givenCreateParticipantRequest_whenCreateParticipant_thenVerifyCreateParticipant() {
            // Given
            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Nicholas Meyers", CAR, TP, "7656454");

            // When
            createParticipantUseCase.createParticipant(createParticipantRequest);

            // Then
            ArgumentCaptor<Participant> participantArgumentCaptor = ArgumentCaptor.forClass(Participant.class);
            verify(participantRepository).createParticipant(participantArgumentCaptor.capture());

            Participant participant = participantArgumentCaptor.getValue();
            assertThat(participant.getName()).isEqualTo("Nicholas Meyers");
            assertThat(participant.getVehicle()).isEqualTo(CAR);
            assertThat(participant.getRole()).isEqualTo(TP);
            assertThat(participant.getDeviceId()).isEqualTo("7656454");
        }

        @Test
        void givenInvalidCreateParticipantRequest_whenCreateParticipant_thenDontCreateParticipant() {
            // Given
            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Nicholas Meyers", CAR, null, "7656454");

            // When
            createParticipantUseCase.createParticipant(createParticipantRequest);

            // Then
            verifyNoInteractions(participantRepository);
        }
    }
}
