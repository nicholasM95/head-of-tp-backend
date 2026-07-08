package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.domain.RouteClimb;
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
@Table(name = "route_climb")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RouteClimbJpaEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "route_id", nullable = false, updatable = false)
    private UUID routeId;

    @Column(name = "start_distance_in_meter", nullable = false, updatable = false)
    private Integer startDistanceInMeter;

    @Column(name = "end_distance_in_meter", nullable = false, updatable = false)
    private Integer endDistanceInMeter;

    @Column(name = "length_in_meter", nullable = false, updatable = false)
    private Integer lengthInMeter;

    @Column(name = "elevation_gain_in_meter", nullable = false, updatable = false)
    private Integer elevationGainInMeter;

    @Column(name = "average_gradient", nullable = false, updatable = false)
    private Double averageGradient;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    public RouteClimbJpaEntity(UUID routeId, RouteClimb routeClimb) {
        this.id = UUID.randomUUID();
        this.routeId = routeId;
        this.startDistanceInMeter = routeClimb.startDistanceInMeter();
        this.endDistanceInMeter = routeClimb.endDistanceInMeter();
        this.lengthInMeter = routeClimb.lengthInMeter();
        this.elevationGainInMeter = routeClimb.elevationGainInMeter();
        this.averageGradient = routeClimb.averageGradient();
    }
}
