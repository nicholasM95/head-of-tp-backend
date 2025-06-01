package be.nicholasmeyers.headoftp.participant.adapter.repository;

import be.nicholasmeyers.headoftp.participant.domain.Participant;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantRole;
import be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "participant")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ParticipantJpaEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle", nullable = false)
    private ParticipantVehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ParticipantRole role;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    public ParticipantJpaEntity(Participant participant) {
        this.id = UUID.randomUUID();
        this.deviceId = participant.getDeviceId();
        this.name = participant.getName();
        this.vehicle = participant.getVehicle();
        this.role = participant.getRole();
    }

    public Participant toDomainObject() {
        return Participant.builder()
                .id(id)
                .name(name)
                .vehicle(vehicle)
                .role(role)
                .deviceId(deviceId)
                .build();
    }

    public void updateFromDomain(Participant participant) {
        this.deviceId = participant.getDeviceId();
        this.name = participant.getName();
        this.vehicle = participant.getVehicle();
        this.role = participant.getRole();
    }
}
