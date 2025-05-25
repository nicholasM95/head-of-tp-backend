package be.nicholasmeyers.headoftp.route.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoutePointJpaRepository extends JpaRepository<RoutePointJpaEntity, UUID> {

    void deleteByRouteId(UUID routeId);
}
