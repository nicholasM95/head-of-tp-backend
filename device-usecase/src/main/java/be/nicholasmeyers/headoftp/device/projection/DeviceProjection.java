package be.nicholasmeyers.headoftp.device.projection;

import java.time.LocalDateTime;

public record DeviceProjection(String deviceId, LocalDateTime lastModifiedDateLocation) {
}
