package be.nicholasmeyers.headoftp.participant.usecase;

import be.nicholasmeyers.headoftp.participant.projection.ParticipantProjection;
import be.nicholasmeyers.headoftp.participant.repository.ParticipantQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class FindAllParticipantUseCase {

    private final ParticipantQueryRepository participantQueryRepository;

    public List<ParticipantProjection> findAllParticipants() {
        return participantQueryRepository.findAllParticipants();
    }

}
