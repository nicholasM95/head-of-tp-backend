package be.nicholasmeyers.headoftp.participant.usecase;

import be.nicholasmeyers.headoftp.participant.domain.Participant;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantRole;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle;
import be.nicholasmeyers.headoftp.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PatchParticipantUseCase {

    private final ParticipantRepository participantRepository;

    public void patchParticipant(UUID participantId, String name, String deviceId, ParticipantVehicle vehicle, ParticipantRole role) {
        Optional<Participant> participant = participantRepository.findParticipantById(participantId);
        if (participant.isPresent()) {
            if (name != null) {
                participant.get().setName(name);
            }

            if (deviceId != null) {
                participant.get().setDeviceId(deviceId);
            }

            if (vehicle != null) {
                participant.get().setVehicle(vehicle);
            }

            if (role != null) {
                participant.get().setRole(role);
            }

            if (name != null || deviceId != null || vehicle != null || role != null) {
                participantRepository.updateParticipant(participant.get());
            }
        }
    }
}
