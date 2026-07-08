package be.nicholasmeyers.headoftp.route.projection;

public record RouteClimbProjection(Integer startDistanceInMeter,
                                    Integer endDistanceInMeter,
                                    Integer lengthInMeter,
                                    Integer elevationGainInMeter,
                                    Double averageGradient) {
}
