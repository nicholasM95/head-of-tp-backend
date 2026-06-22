package be.nicholasmeyers.headoftp.participant.repository;

import be.nicholasmeyers.headoftp.participant.domain.Participant;

import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository {

    void createParticipant(Participant participant);

    Optional<Participant> findParticipantById(UUID participantId);

    void updateParticipant(Participant participant);

    void deleteParticipantById(UUID participantId);
}
