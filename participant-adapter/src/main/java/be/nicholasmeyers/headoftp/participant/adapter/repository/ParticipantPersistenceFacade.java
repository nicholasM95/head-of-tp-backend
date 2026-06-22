package be.nicholasmeyers.headoftp.participant.adapter.repository;

import be.nicholasmeyers.headoftp.participant.domain.Participant;
import be.nicholasmeyers.headoftp.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional
public class ParticipantPersistenceFacade implements ParticipantRepository {

    private final ParticipantJpaRepository participantJpaRepository;

    @Override
    public void createParticipant(Participant participant) {
        participantJpaRepository.saveAndFlush(new ParticipantJpaEntity(participant));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Participant> findParticipantById(UUID participantId) {
        return participantJpaRepository.findById(participantId).map(ParticipantJpaEntity::toDomainObject);
    }

    @Override
    public void updateParticipant(Participant participant) {
        Optional<ParticipantJpaEntity> participantJpaEntity = participantJpaRepository.findById(participant.getId());
        if (participantJpaEntity.isPresent()) {
            participantJpaEntity.get().updateFromDomain(participant);
            participantJpaRepository.saveAndFlush(participantJpaEntity.get());
        }
    }

    @Override
    public void deleteParticipantById(UUID participantId) {
        participantJpaRepository.deleteById(participantId);
        participantJpaRepository.flush();
    }
}
