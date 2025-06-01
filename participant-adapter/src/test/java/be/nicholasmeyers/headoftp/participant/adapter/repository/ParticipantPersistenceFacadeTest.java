package be.nicholasmeyers.headoftp.participant.adapter.repository;

import be.nicholasmeyers.headoftp.participant.domain.CreateParticipantRequest;
import be.nicholasmeyers.headoftp.participant.domain.Participant;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.BIKER;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.TP;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.BIKE;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.CAR;
import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class ParticipantPersistenceFacadeTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ParticipantPersistenceFacade participantPersistenceFacade;

    @Nested
    class CreateParticipant {
        @Test
        void givenParticipant_whenCreateParticipant_thenParticipantCreated() {
            // Given
            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Nicholas Meyers", CAR, TP, "32423");
            Participant participant = ParticipantFactory.createParticipant(createParticipantRequest).getValue();

            // When
            participantPersistenceFacade.createParticipant(participant);

            // Then
            String sql = """
                    SELECT id, device_id, name, vehicle, role, created_date, last_modified_date
                    FROM participant WHERE device_id = ?
                    """;
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, "32423");

            assertThat(result).isNotNull();
            assertThat(result.get("id")).isNotNull();
            assertThat(result.get("device_id")).isEqualTo("32423");
            assertThat(result.get("name")).isEqualTo("Nicholas Meyers");
            assertThat(result.get("vehicle")).isEqualTo("CAR");
            assertThat(result.get("role")).isEqualTo("TP");
            assertThat(result.get("created_date")).isNotNull();
            assertThat(result.get("last_modified_date")).isNotNull();
        }
    }

    @Sql("participant.sql")
    @Nested
    class FindParticipantById {
        @Test
        void givenId_whenFindParticipantById_thenParticipantReturned() {
            // Given
            UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

            // When
            Optional<Participant> participant = participantPersistenceFacade.findParticipantById(id);

            // Then
            assertThat(participant).isPresent();
            assertThat(participant.get().getId()).isEqualTo(id);
            assertThat(participant.get().getDeviceId()).isEqualTo("DEV1234567890");
            assertThat(participant.get().getName()).isEqualTo("Nicholas Meyers");
            assertThat(participant.get().getVehicle()).isEqualTo(CAR);
            assertThat(participant.get().getRole()).isEqualTo(TP);
        }

        @Test
        void givenInvalidId_whenFindParticipantById_thenParticipantReturned() {
            // Given
            UUID id = UUID.randomUUID();

            // When
            Optional<Participant> participant = participantPersistenceFacade.findParticipantById(id);

            // Then
            assertThat(participant).isEmpty();
        }
    }

    @Sql("participant.sql")
    @Nested
    class UpdateParticipant {
        @Test
        void givenParticipant_whenUpdateParticipant_thenParticipantUpdated() {
            // Given
            UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
            Optional<Participant> participant = participantPersistenceFacade.findParticipantById(id);

            participant.get().setName("Mieke Muis");
            participant.get().setDeviceId("999");
            participant.get().setVehicle(BIKE);
            participant.get().setRole(BIKER);

            // When
            participantPersistenceFacade.updateParticipant(participant.get());

            // Then
            String sql = """
                    SELECT id, device_id, name, vehicle, role, created_date, last_modified_date
                    FROM participant WHERE device_id = ?
                    """;
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, "999");

            assertThat(result).isNotNull();
            assertThat(result.get("id")).isEqualTo(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
            assertThat(result.get("device_id")).isEqualTo("999");
            assertThat(result.get("name")).isEqualTo("Mieke Muis");
            assertThat(result.get("vehicle")).isEqualTo("BIKE");
            assertThat(result.get("role")).isEqualTo("BIKER");
            assertThat(result.get("created_date")).isNotNull();
            assertThat(result.get("last_modified_date")).isNotNull();
        }
    }

    @Sql(value = "participant.sql")
    @Nested
    class DeleteParticipantById {
        @Test
        void givenId_whenDeleteParticipantById_thenParticipantIsDeleted() {
            // Given
            UUID participantId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

            // When
            participantPersistenceFacade.deleteParticipantById(participantId);

            // Then
            String sql = "SELECT COUNT(id) FROM participant WHERE id = '123e4567-e89b-12d3-a456-426614174000'";
            Long result = jdbcTemplate.queryForObject(sql, Long.class);
            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(0L);
        }
    }
}
