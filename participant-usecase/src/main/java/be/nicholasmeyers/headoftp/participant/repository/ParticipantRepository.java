package be.nicholasmeyers.headoftp.participant.repository;

import be.nicholasmeyers.headoftp.participant.domain.Participant;

public interface ParticipantRepository {

    void createParticipant(Participant participant);
}
