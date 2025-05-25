package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.domain.Route;
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
@Table(name = "route")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RouteJpaEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "route_name", nullable = false, updatable = false)
    private String name;

    @Column(name = "elevation_gain", nullable = false, updatable = false)
    private Integer elevationGain;

    @Column(name = "estimated_average_speed", nullable = false)
    private Double estimatedAverageSpeed;

    @Column(name = "distance_in_meters", nullable = false, updatable = false)
    private Integer distanceInMeters;

    @Column(name = "duration_in_minutes", nullable = false)
    private Integer durationInMinutes;

    @Column(name = "estimated_start_time", nullable = false)
    private LocalDateTime estimatedStartTime;

    @Column(name = "estimated_end_time", nullable = false)
    private LocalDateTime estimatedEndTime;

    @Column(name = "pause_in_minutes", nullable = false)
    private Integer pauseInMinutes;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "average_speed")
    private Double averageSpeed;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    public RouteJpaEntity(Route route) {
        this.id = UUID.randomUUID();
        this.name = route.getName();
        this.elevationGain = route.getElevationGain();
        this.estimatedAverageSpeed = route.getEstimatedAverageSpeed();
        this.distanceInMeters = route.getDistanceInMeters();
        this.durationInMinutes = route.getDurationInMinutes();
        this.estimatedStartTime = route.getEstimatedStartTime();
        this.estimatedEndTime = route.getEstimatedEndTime();
        this.pauseInMinutes = route.getPauseInMinutes();
        this.startTime = route.getStartTime();
        this.averageSpeed = route.getAverageSpeed();
    }

    public void updateFromDomain(Route route) {
        this.name = route.getName();
        this.elevationGain = route.getElevationGain();
        this.estimatedAverageSpeed = route.getEstimatedAverageSpeed();
        this.distanceInMeters = route.getDistanceInMeters();
        this.durationInMinutes = route.getDurationInMinutes();
        this.estimatedStartTime = route.getEstimatedStartTime();
        this.estimatedEndTime = route.getEstimatedEndTime();
        this.pauseInMinutes = route.getPauseInMinutes();
        this.startTime = route.getStartTime();
        this.averageSpeed = route.getAverageSpeed();
    }

    public Route toDomainObject() {
        return Route.builder()
                .id(this.id)
                .name(this.name)
                .elevationGain(this.elevationGain)
                .estimatedAverageSpeed(this.estimatedAverageSpeed)
                .distanceInMeters(this.distanceInMeters)
                .durationInMinutes(this.durationInMinutes)
                .estimatedStartTime(this.estimatedStartTime)
                .estimatedEndTime(this.estimatedEndTime)
                .pauseInMinutes(this.pauseInMinutes)
                .startTime(this.startTime)
                .averageSpeed(this.averageSpeed)
                .build();
    }
}
