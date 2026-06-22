package be.nicholasmeyers.headoftp.participant.repository;

import be.nicholasmeyers.headoftp.participant.projection.ParticipantProjection;

import java.util.List;

public interface ParticipantQueryRepository {
    List<ParticipantProjection> findAllParticipants();
}
