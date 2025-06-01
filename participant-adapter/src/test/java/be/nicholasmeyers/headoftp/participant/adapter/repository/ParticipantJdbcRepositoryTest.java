package be.nicholasmeyers.headoftp.participant.adapter.repository;

import be.nicholasmeyers.headoftp.participant.projection.ParticipantProjection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.TP;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.CAR;
import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class ParticipantJdbcRepositoryTest {
    @Autowired
    private ParticipantJdbcRepository participantJdbcRepository;

    @Sql("participant.sql")
    @Nested
    class FindAllParticipants {
        @Test
        void given_whenFindAllParticipants_thenReturnParticipants() {
            // Given

            // When
            List<ParticipantProjection> participants = participantJdbcRepository.findAllParticipants();

            // Then
            assertThat(participants).containsExactly(new ParticipantProjection(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                    "Nicholas Meyers", "8923", CAR, TP, LocalDateTime.of(2025, 6, 1, 12, 0),
                    LocalDateTime.of(2025, 6, 1, 12, 0)));
        }
    }
}
