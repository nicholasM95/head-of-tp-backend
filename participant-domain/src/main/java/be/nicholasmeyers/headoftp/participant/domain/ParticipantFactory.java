package be.nicholasmeyers.headoftp.participant.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipantFactory {

    public static Creation<Participant> createParticipant(CreateParticipantRequest createParticipantRequest) {
        Participant participant = new Participant(createParticipantRequest);
        return Creation.of(participant, participant.validate());
    }
}
