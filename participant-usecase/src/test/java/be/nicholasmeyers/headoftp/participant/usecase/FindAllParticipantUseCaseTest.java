package be.nicholasmeyers.headoftp.participant.usecase;

import be.nicholasmeyers.headoftp.participant.projection.ParticipantProjection;
import be.nicholasmeyers.headoftp.participant.repository.ParticipantQueryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.TP;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.CAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllParticipantUseCaseTest {

    @InjectMocks
    private FindAllParticipantUseCase findAllParticipantUseCase;

    @Mock
    private ParticipantQueryRepository participantQueryRepository;

    @Nested
    class FindAllParticipants {
        @Test
        void given_whenFindAllParticipants_thenReturnParticipants() {
            // Given

            when(participantQueryRepository.findAllParticipants())
                    .thenReturn(List.of(
                            new ParticipantProjection(UUID.fromString("4cccda25-fb41-47b3-9e0f-f63b68a0ad47"), "Nicholas Meyers", "8923", CAR, TP, LocalDateTime.of(2025, 5, 12, 15, 30), LocalDateTime.of(2025, 5, 12, 15, 30))));

            // When
            List<ParticipantProjection> participants = findAllParticipantUseCase.findAllParticipants();

            // Then
            assertThat(participants).containsExactly(new ParticipantProjection(UUID.fromString("4cccda25-fb41-47b3-9e0f-f63b68a0ad47"), "Nicholas Meyers", "8923", CAR, TP, LocalDateTime.of(2025, 5, 12, 15, 30), LocalDateTime.of(2025, 5, 12, 15, 30)));
        }
    }
}
