package be.nicholasmeyers.headoftp.route.adapter.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = { "be.nicholasmeyers.headoftp.route.adapter.repository"})
@EnableJpaRepositories(basePackages = { "be.nicholasmeyers.headoftp.route.adapter.repository"})
@EntityScan(basePackages = { "be.nicholasmeyers.headoftp.route.adapter.repository"})
public class JpaTestConfig {
}
