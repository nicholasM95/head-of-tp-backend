package be.nicholasmeyers.headoftp.participant.domain;

import be.nicholasmeyers.headoftp.common.domain.validation.Creation;
import be.nicholasmeyers.headoftp.common.domain.validation.Error;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static be.nicholasmeyers.headoftp.participant.domain.ParticipantRole.TP;
import static be.nicholasmeyers.headoftp.participant.domain.ParticipantVehicle.CAR;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantFactoryTest {

    @Nested
    class CreateParticipant {
        @Test
        void givenCreateParticipantRequest_whenCreateParticipant_thenReturnCreationWithoutErrors() {
            // Given
            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Nicholas Meyers", CAR, TP, "789301");

            // When
            Creation<Participant> creation = ParticipantFactory.createParticipant(createParticipantRequest);

            // Then
            assertThat(creation.getValue()).isNotNull();
            assertThat(creation.getValue().getName()).isEqualTo("Nicholas Meyers");
            assertThat(creation.getValue().getVehicle()).isEqualTo(CAR);
            assertThat(creation.getValue().getRole()).isEqualTo(TP);
            assertThat(creation.getValue().getDeviceId()).isEqualTo("789301");

            assertThat(creation.getNotification()).isNotNull();
            assertThat(creation.getNotification().hasErrors()).isFalse();
        }

        @Test
        void givenCreateParticipantRequestWithoutName_whenCreateParticipant_thenReturnCreationWithErrors() {
            // Given
            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest(null, CAR, TP, "789301");

            // When
            Creation<Participant> creation = ParticipantFactory.createParticipant(createParticipantRequest);

            // Then
            assertThat(creation.getValue()).isNotNull();
            assertThat(creation.getValue().getName()).isNull();
            assertThat(creation.getValue().getVehicle()).isEqualTo(CAR);
            assertThat(creation.getValue().getRole()).isEqualTo(TP);
            assertThat(creation.getValue().getDeviceId()).isEqualTo("789301");

            assertThat(creation.getNotification()).isNotNull();
            assertThat(creation.getNotification().hasErrors()).isTrue();
            assertThat(creation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.empty", "name"));
        }

        @Test
        void givenCreateParticipantRequestWithoutVehicle_whenCreateParticipant_thenReturnCreationWithErrors() {
            // Given
            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Nicholas Meyers", null, TP, "789301");

            // When
            Creation<Participant> creation = ParticipantFactory.createParticipant(createParticipantRequest);

            // Then
            assertThat(creation.getValue()).isNotNull();
            assertThat(creation.getValue().getName()).isEqualTo("Nicholas Meyers");
            assertThat(creation.getValue().getVehicle()).isNull();
            assertThat(creation.getValue().getRole()).isEqualTo(TP);
            assertThat(creation.getValue().getDeviceId()).isEqualTo("789301");

            assertThat(creation.getNotification()).isNotNull();
            assertThat(creation.getNotification().hasErrors()).isTrue();
            assertThat(creation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "vehicle"));
        }

        @Test
        void givenCreateParticipantRequestWithoutRole_whenCreateParticipant_thenReturnCreationWithErrors() {
            // Given
            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Nicholas Meyers", CAR, null, "789301");

            // When
            Creation<Participant> creation = ParticipantFactory.createParticipant(createParticipantRequest);

            // Then
            assertThat(creation.getValue()).isNotNull();
            assertThat(creation.getValue().getName()).isEqualTo("Nicholas Meyers");
            assertThat(creation.getValue().getVehicle()).isEqualTo(CAR);
            assertThat(creation.getValue().getRole()).isNull();
            assertThat(creation.getValue().getDeviceId()).isEqualTo("789301");

            assertThat(creation.getNotification()).isNotNull();
            assertThat(creation.getNotification().hasErrors()).isTrue();
            assertThat(creation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "role"));
        }

        @Test
        void givenCreateParticipantRequestWithoutDeviceId_whenCreateParticipant_thenReturnCreationWithoutErrors() {
            // Given
            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Nicholas Meyers", CAR, TP, null);

            // When
            Creation<Participant> creation = ParticipantFactory.createParticipant(createParticipantRequest);

            // Then
            assertThat(creation.getValue()).isNotNull();
            assertThat(creation.getValue().getName()).isEqualTo("Nicholas Meyers");
            assertThat(creation.getValue().getVehicle()).isEqualTo(CAR);
            assertThat(creation.getValue().getRole()).isEqualTo(TP);
            assertThat(creation.getValue().getDeviceId()).isNull();

            assertThat(creation.getNotification()).isNotNull();
            assertThat(creation.getNotification().hasErrors()).isTrue();
            assertThat(creation.getNotification().getErrors()).containsExactly(new Error("field.must.not.be.null", "deviceId"));
        }
    }
}
