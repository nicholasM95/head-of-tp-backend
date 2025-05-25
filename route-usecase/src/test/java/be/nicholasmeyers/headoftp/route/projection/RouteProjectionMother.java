package be.nicholasmeyers.headoftp.route.projection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RouteProjectionMother {

    public static RouteProjection createRouteProjection1() {
        return new RouteProjection(UUID.fromString("e0483c47-0aa0-442d-808b-8897687f4af2"),
                "route-name 1",
                1200,
                24.4,
                12000,
                180,
                LocalDateTime.of(2025, 5, 20, 12, 0),
                LocalDateTime.of(2025, 5, 20, 14, 0),
                5,
                LocalDateTime.of(2025, 5, 20, 12, 5),
                35.1,
                LocalDateTime.of(2025, 5, 19, 12, 0),
                LocalDateTime.of(2025, 5, 19, 13, 2));
    }

    public static RouteProjection createRouteProjection2() {
        return new RouteProjection(UUID.fromString("74d9a93b-7b7b-4ca7-a107-64bc70172c2e"),
                "route-name 2",
                1400,
                27.4,
                14000,
                210,
                LocalDateTime.of(2025, 5, 22, 12, 0),
                LocalDateTime.of(2025, 5, 22, 14, 0),
                30,
                LocalDateTime.of(2025, 5, 22, 12, 5),
                30.1,
                LocalDateTime.of(2025, 5, 21, 12, 0),
                LocalDateTime.of(2025, 5, 21, 13, 2));
    }

    public static RouteProjection createRouteProjection3() {
        return new RouteProjection(UUID.fromString("f654bfa7-1824-4123-be0b-a93993a21d6d"),
                "route-name 3",
                1200,
                24.4,
                12000,
                180,
                LocalDateTime.of(2025, 5, 20, 12, 0),
                LocalDateTime.of(2125, 5, 20, 14, 0),
                5,
                null,
                0.0,
                LocalDateTime.of(2025, 5, 19, 12, 0),
                LocalDateTime.of(2025, 5, 19, 13, 2));
    }

    public static RouteProjection createRouteProjection4() {
        return new RouteProjection(UUID.fromString("cc335f92-d2e8-483c-b0ae-aa7e75677353"),
                "route-name 4",
                2000,
                21.4,
                1000,
                120,
                LocalDateTime.of(2025, 5, 20, 12, 0),
                LocalDateTime.of(2025, 5, 20, 14, 0),
                50,
                null,
                0.0,
                LocalDateTime.of(2025, 5, 19, 12, 0),
                LocalDateTime.of(2025, 5, 19, 12, 0));
    }
}
