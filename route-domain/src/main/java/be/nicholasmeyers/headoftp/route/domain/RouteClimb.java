package be.nicholasmeyers.headoftp.route.domain;

public record RouteClimb(Integer startDistanceInMeter,
                         Integer endDistanceInMeter,
                         Integer lengthInMeter,
                         Integer elevationGainInMeter,
                         Double averageGradient) {
}
