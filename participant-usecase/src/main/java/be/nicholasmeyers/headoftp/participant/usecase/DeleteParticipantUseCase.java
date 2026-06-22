package be.nicholasmeyers.headoftp.participant.usecase;

import be.nicholasmeyers.headoftp.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class DeleteParticipantUseCase {

    private final ParticipantRepository participantRepository;

    public void deleteParticipant(UUID participantId) {
        log.info("Delete participant with id: {}", participantId);
        participantRepository.deleteParticipantById(participantId);
    }
}
