package be.nicholasmeyers.headoftp.participant.adapter.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = { "be.nicholasmeyers.headoftp.participant.adapter.repository"})
@EnableJpaRepositories(basePackages = { "be.nicholasmeyers.headoftp.participant.adapter.repository"})
@EntityScan(basePackages = { "be.nicholasmeyers.headoftp.participant.adapter.repository"})
public class JpaTestConfig {
}
