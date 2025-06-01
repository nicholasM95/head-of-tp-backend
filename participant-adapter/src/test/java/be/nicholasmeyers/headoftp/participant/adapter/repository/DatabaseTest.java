package be.nicholasmeyers.headoftp.participant.adapter.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:16.3-alpine:///headoftpparticipanttest",
        "spring.liquibase.change-log=classpath:db/changelog/db.changelog-participant.yaml"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {JpaTestConfig.class})
@EnableAutoConfiguration
@EnableJpaAuditing
@AutoConfigureJson
public @interface DatabaseTest {
}
