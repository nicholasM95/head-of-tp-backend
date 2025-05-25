package be.nicholasmeyers.headoftp.route.usecase;

import be.nicholasmeyers.headoftp.route.projection.RouteProjection;
import be.nicholasmeyers.headoftp.route.repository.RouteQueryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.nicholasmeyers.headoftp.route.projection.RouteProjectionMother.createRouteProjection1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllRoutesUseCaseTest {

    @InjectMocks
    private FindAllRoutesUseCase findAllRoutesUseCase;

    @Mock
    private RouteQueryRepository routeQueryRepository;

    @Nested
    class FindAllRoutesTest {
        @Test
        void testFindAllRoutes() {
            // Given
            RouteProjection routeProjection = createRouteProjection1();
            when(routeQueryRepository.findAllRoutes()).thenReturn(List.of(routeProjection));

            // When
            List<RouteProjection> routes = findAllRoutesUseCase.findAll();

            // Then
            assertThat(routes).containsExactly(routeProjection);
        }
    }
}
