package be.nicholasmeyers.headoftp.route.adapter.repository;

import be.nicholasmeyers.headoftp.route.domain.RoutePoint;
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
@Table(name = "route_point")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RoutePointJpaEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "route_id", nullable = false, updatable = false)
    private UUID routeId;

    @Column(name = "latitude", nullable = false, updatable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false, updatable = false)
    private Double longitude;

    @Column(name = "altitude", nullable = false, updatable = false)
    private Double altitude;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    public RoutePointJpaEntity(UUID routeId, RoutePoint routePoint) {
        this.id = UUID.randomUUID();
        this.routeId = routeId;
        this.latitude = routePoint.getLatitude();
        this.longitude = routePoint.getLongitude();
        this.altitude = routePoint.getAltitude();
    }
}
