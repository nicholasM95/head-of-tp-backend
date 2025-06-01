package be.nicholasmeyers.headoftp.participant.usecase;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.participant.domain.CreateParticipantRequest;
import be.nicholasmeyers.headoftp.participant.domain.Participant;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantFactory;
import be.nicholasmeyers.headoftp.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CreateParticipantUseCase {
    private final ParticipantRepository participantRepository;

    public Notification createParticipant(CreateParticipantRequest createParticipantRequest) {
        log.info("Creating participant {}", createParticipantRequest.name());
        Creation<Participant> participant = ParticipantFactory.createParticipant(createParticipantRequest);

        if (!participant.getNotification().hasErrors()) {
            participantRepository.createParticipant(participant.getValue());
        }

        return participant.getNotification();
    }
}
