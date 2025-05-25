package be.nicholasmeyers.headoftp.route.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public record RouteProjection(UUID id,
                              String name,
                              Integer elevationGain,
                              Double estimatedAverageSpeed,
                              Integer distanceInMeters,
                              Integer durationInMinutes,
                              LocalDateTime estimatedStartTime,
                              LocalDateTime estimatedEndTime,
                              Integer pauseInMinutes,
                              LocalDateTime startTime,
                              Double averageSpeed,
                              LocalDateTime createDate,
                              LocalDateTime lastModifiedDate) {
}
