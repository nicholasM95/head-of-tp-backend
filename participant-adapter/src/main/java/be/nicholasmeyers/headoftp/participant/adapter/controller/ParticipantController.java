package be.nicholasmeyers.headoftp.participant.adapter.controller;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.participant.adapter.resource.CreateParticipantRequestResource;
import be.nicholasmeyers.headoftp.participant.domain.CreateParticipantRequest;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantRole;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle;
import be.nicholasmeyers.headoftp.participant.usecase.CreateParticipantUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://headoftp.com", "http://localhost:4200"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class ParticipantController implements ParticipantApi {

    private final CreateParticipantUseCase createParticipantUseCase;

    @Override
    public ResponseEntity<Void> createParticipant(CreateParticipantRequestResource createParticipantRequestResource) {
        CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest(
                createParticipantRequestResource.getName(),
                ParticipantVehicle.valueOf(createParticipantRequestResource.getVehicle().name()),
                ParticipantRole.valueOf(createParticipantRequestResource.getRole().name()),
                createParticipantRequestResource.getDeviceId());

        Notification notification = createParticipantUseCase.createParticipant(createParticipantRequest);

        if (notification.hasErrors()) {
            log.error(notification.getErrors().toString());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
