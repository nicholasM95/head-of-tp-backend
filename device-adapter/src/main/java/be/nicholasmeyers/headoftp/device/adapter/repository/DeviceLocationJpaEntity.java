package be.nicholasmeyers.headoftp.device.adapter.repository;

import be.nicholasmeyers.headoftp.device.domain.DeviceLocation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "device_location")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class DeviceLocationJpaEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "device_id", nullable = false, updatable = false)
    private String deviceId;

    @Column(name = "location_timestamp", nullable = false, updatable = false)
    private Long timestamp;

    @Column(name = "latitude", nullable = false, updatable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false, updatable = false)
    private Double longitude;

    @Column(name = "speed", nullable = false, updatable = false)
    private Double speed;

    @Column(name = "bearing", nullable = false, updatable = false)
    private Double bearing;

    @Column(name = "altitude", nullable = false, updatable = false)
    private Double altitude;

    @Column(name = "accuracy", nullable = false, updatable = false)
    private Double accuracy;

    @Column(name = "battery", nullable = false, updatable = false)
    private Double battery;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    public DeviceLocationJpaEntity(DeviceLocation deviceLocation) {
        this.id = UUID.randomUUID();
        this.deviceId = deviceLocation.getDeviceId();
        this.timestamp = deviceLocation.getTimestamp();
        this.latitude = deviceLocation.getLatitude();
        this.longitude = deviceLocation.getLongitude();
        this.speed = deviceLocation.getSpeed();
        this.bearing = deviceLocation.getBearing();
        this.altitude = deviceLocation.getAltitude();
        this.accuracy = deviceLocation.getAccuracy();
        this.battery = deviceLocation.getBattery();
    }
}
