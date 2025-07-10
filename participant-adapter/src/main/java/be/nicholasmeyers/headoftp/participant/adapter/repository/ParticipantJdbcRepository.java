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
                SELECT id, name, device_id, vehicle, role, participant_created_date, last_modified_date, location_created_date
                FROM (
                         SELECT
                             p.id,
                             p.name,
                             p.device_id,
                             p.vehicle,
                             p.role,
                             p.created_date AS participant_created_date,
                             p.last_modified_date,
                             l.created_date AS location_created_date,
                             ROW_NUMBER() OVER (
                                 PARTITION BY p.id
                                 ORDER BY l.created_date DESC
                                 ) AS rn
                         FROM participant p
                                  JOIN device_location l
                                       ON UPPER(p.device_id) = UPPER(l.device_id)
                     ) sub
                WHERE rn = 1
                ORDER BY name;
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
                resultSet.getTimestamp("participant_created_date").toLocalDateTime(),
                resultSet.getTimestamp("last_modified_date").toLocalDateTime(),
                resultSet.getTimestamp("location_created_date").toLocalDateTime()
        );
    }
}
