package be.nicholasmeyers.headoftp.participant.adapter.repository;

import be.nicholasmeyers.headoftp.participant.domain.CreateParticipantRequest;
import be.nicholasmeyers.headoftp.participant.domain.Participant;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.TP;
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
}
