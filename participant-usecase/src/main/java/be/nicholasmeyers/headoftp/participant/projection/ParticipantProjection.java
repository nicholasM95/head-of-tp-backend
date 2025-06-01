package be.nicholasmeyers.headoftp.participant.projection;

import be.nicholasmeyers.headoftp.participant.domain.ParticipantRole;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle;

import java.time.LocalDateTime;
import java.util.UUID;

public record ParticipantProjection(UUID id, String name, String deviceId, ParticipantVehicle vehicle, ParticipantRole role,  LocalDateTime createDate,
                                    LocalDateTime lastModifiedDate) {
}
