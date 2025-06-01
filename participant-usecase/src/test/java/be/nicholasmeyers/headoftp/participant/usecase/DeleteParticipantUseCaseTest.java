package be.nicholasmeyers.headoftp.participant.usecase;

import be.nicholasmeyers.headoftp.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteParticipantUseCaseTest {

    @InjectMocks
    private DeleteParticipantUseCase deleteParticipantUseCase;

    @Mock
    private ParticipantRepository participantRepository;

    @Nested
    class DeleteParticipant {
        @Test
        void givenId_whenDeleteParticipant_thenVerifyDeleteParticipantIsCalled() {
            // Given
            UUID id = UUID.fromString("e8247144-8b3b-4aac-85f0-339c0b6ba922");

            // When
            deleteParticipantUseCase.deleteParticipant(id);

            // Then
            verify(participantRepository).deleteParticipantById(UUID.fromString("e8247144-8b3b-4aac-85f0-339c0b6ba922"));
        }
    }
}
