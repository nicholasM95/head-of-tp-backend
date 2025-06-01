package be.nicholasmeyers.headoftp.participant.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.Notification;
import be.nicholasmeyers.headoftp.common.domain.validation.ObjectValidator;
import be.nicholasmeyers.headoftp.common.domain.validation.StringValidator;
import lombok.Getter;

@Getter
public class Participant {
    private String name;
    private ParticipantVehicle vehicle;
    private ParticipantRole role;
    private String deviceId;

    protected Participant(CreateParticipantRequest createParticipantRequest) {
        this.name = createParticipantRequest.name();
        this.vehicle = createParticipantRequest.vehicle();
        this.role = createParticipantRequest.role();
        this.deviceId = createParticipantRequest.deviceId();
    }

    protected Notification validate() {
        Notification notification = new Notification();
        StringValidator.notBlank("name", name, notification);
        StringValidator.notExceedingMaxLength("name", name, 20, notification);
        ObjectValidator.notNull("vehicle", vehicle, notification);
        ObjectValidator.notNull("role", role, notification);
        ObjectValidator.notNull("deviceId", deviceId, notification);
        StringValidator.notExceedingMaxLength("deviceId", deviceId, 20, notification);
        return notification;
    }
}
