package be.nicholasmeyers.headoftp.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"be.nicholasmeyers.headoftp.route.adapter", "be.nicholasmeyers.headoftp.device.adapter", "be.nicholasmeyers.headoftp.participant.adapter"})
@EnableJpaRepositories(basePackages = {"be.nicholasmeyers.headoftp.route.adapter.repository", "be.nicholasmeyers.headoftp.device.adapter.repository", "be.nicholasmeyers.headoftp.participant.adapter.repository"})
@EntityScan(basePackages = {"be.nicholasmeyers.headoftp.route.adapter.repository", "be.nicholasmeyers.headoftp.device.adapter.repository", "be.nicholasmeyers.headoftp.participant.adapter.repository"})
@EnableJpaAuditing
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
