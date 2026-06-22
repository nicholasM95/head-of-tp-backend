package be.nicholasmeyers.headoftp.participant.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParticipantJpaRepository extends JpaRepository<ParticipantJpaEntity, UUID> {
}
