package be.nicholasmeyers.headoftp.participant.adapter.repository;

import be.nicholasmeyers.headoftp.participant.domain.ParticipantRole;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle;
import be.nicholasmeyers.headoftp.participant.projection.ParticipantProjection;
import be.nicholasmeyers.headoftp.participant.repository.ParticipantQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class ParticipantJdbcRepository implements ParticipantQueryRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<ParticipantProjection> findAllParticipants() {
        String query = """
                SELECT id, name, device_id, vehicle, role, created_date, last_modified_date FROM participant ORDER BY name
                """;

        return template.query(query, Map.of(), this::map);
    }

    private ParticipantProjection map(ResultSet resultSet, int i) throws SQLException {
        return new ParticipantProjection(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("device_id"),
                ParticipantVehicle.valueOf(resultSet.getString("vehicle")),
                ParticipantRole.valueOf(resultSet.getString("role")),
                resultSet.getTimestamp("created_date").toLocalDateTime(),
                resultSet.getTimestamp("last_modified_date").toLocalDateTime()
        );
    }
}
