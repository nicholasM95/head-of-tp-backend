package be.nicholasmeyers.headoftp.participant.domain;

public record CreateParticipantRequest(String name, ParticipantVehicle vehicle, ParticipantRole role, String deviceId) {
}
