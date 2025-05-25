package be.nicholasmeyers.headoftp.device.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceLocationJpaRepository extends JpaRepository<DeviceLocationJpaEntity, UUID> {
}
