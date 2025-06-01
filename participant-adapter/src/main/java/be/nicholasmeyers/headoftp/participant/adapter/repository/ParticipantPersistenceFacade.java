package be.nicholasmeyers.headoftp.participant.adapter.repository;

import be.nicholasmeyers.headoftp.participant.domain.Participant;
import be.nicholasmeyers.headoftp.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParticipantPersistenceFacade implements ParticipantRepository {

    private final ParticipantJpaRepository participantJpaRepository;

    @Override
    public void createParticipant(Participant participant) {
        participantJpaRepository.saveAndFlush(new ParticipantJpaEntity(participant));
    }
}
