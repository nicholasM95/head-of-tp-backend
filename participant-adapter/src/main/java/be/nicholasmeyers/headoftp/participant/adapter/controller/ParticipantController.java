package be.nicholasmeyers.headoftp.participant.adapter.controller;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.participant.adapter.resource.CreateParticipantRequestResource;
import be.nicholasmeyers.headoftp.participant.adapter.resource.ParticipantResponseResource;
import be.nicholasmeyers.headoftp.participant.adapter.resource.PatchParticipantRequestResource;
import be.nicholasmeyers.headoftp.participant.adapter.resource.RoleTypeResource;
import be.nicholasmeyers.headoftp.participant.adapter.resource.VehicleTypeResource;
import be.nicholasmeyers.headoftp.participant.domain.CreateParticipantRequest;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantRole;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle;
import be.nicholasmeyers.headoftp.participant.projection.ParticipantProjection;
import be.nicholasmeyers.headoftp.participant.usecase.CreateParticipantUseCase;
import be.nicholasmeyers.headoftp.participant.usecase.FindAllParticipantUseCase;
import be.nicholasmeyers.headoftp.participant.usecase.PatchParticipantUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = {"https://headoftp.com", "http://localhost:5173"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class ParticipantController implements ParticipantApi {

    private static final ZoneId ZONE_ID_BRUSSELS = ZoneId.of("Europe/Brussels");

    private final CreateParticipantUseCase createParticipantUseCase;
    private final FindAllParticipantUseCase findAllParticipantUseCase;
    private final PatchParticipantUseCase patchParticipantUseCase;

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

    @Override
    public ResponseEntity<List<ParticipantResponseResource>> findAllParticipants() {
        return ResponseEntity.ok(findAllParticipantUseCase.findAllParticipants().stream().map(this::map).toList());
    }

    @Override
    public ResponseEntity<Void> patchParticipantById(UUID id, PatchParticipantRequestResource patchParticipantRequestResource) {
        patchParticipantUseCase.patchParticipant(id, patchParticipantRequestResource.getName(), patchParticipantRequestResource.getDeviceId(),
                ParticipantVehicle.valueOf(patchParticipantRequestResource.getVehicle().name()),
                ParticipantRole.valueOf(patchParticipantRequestResource.getRole().name()));
        return ResponseEntity.noContent().build();
    }

    private ParticipantResponseResource map(ParticipantProjection participantProjection) {
        return ParticipantResponseResource.builder()
                .id(participantProjection.id())
                .name(participantProjection.name())
                .deviceId(participantProjection.deviceId())
                .vehicle(VehicleTypeResource.valueOf(participantProjection.vehicle().name()))
                .role(RoleTypeResource.valueOf(participantProjection.role().name()))
                .createDate(participantProjection.createDate().atZone(ZONE_ID_BRUSSELS).toOffsetDateTime())
                .lastModifiedDate(participantProjection.lastModifiedDate().atZone(ZONE_ID_BRUSSELS).toOffsetDateTime())
                .build();
    }
}
