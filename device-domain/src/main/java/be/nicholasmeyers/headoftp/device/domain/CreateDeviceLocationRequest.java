package be.nicholasmeyers.headoftp.device.domain;

public record CreateDeviceLocationRequest(String deviceId, Long timestamp, Double latitude, Double longitude, Double speed, Double bearing,
                                          Double altitude, Double accuracy, Double battery) {
}
