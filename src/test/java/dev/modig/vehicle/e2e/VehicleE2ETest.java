package dev.modig.vehicle.e2e;

import dev.modig.vehicle.VehicleServiceApplication;
import dev.modig.vehicle.model.Vehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

    @SpringBootTest(
            classes = VehicleServiceApplication.class,
            webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
    )
    class VehicleE2ETest {

        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        private String baseUrl() {
            return "http://localhost:" + port + "/api/v1/vehicle";
        }

        @Test
        @DisplayName("GET /api/v1/vehicle/ABC123 - Full E2E Success Case")
        void testGetVehicleSuccess() {
            ResponseEntity<Vehicle> response = restTemplate.getForEntity(baseUrl() + "/ABC123", Vehicle.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Vehicle vehicle = response.getBody();
            assertThat(vehicle).isNotNull();
            assertThat(vehicle.make()).isEqualTo("Toyota");
            assertThat(vehicle.model()).isEqualTo("Camry");
            assertThat(vehicle.year()).isEqualTo(2018);
        }

        @Test
        @DisplayName("GET /api/v1/vehicle/INVALID-REG - Bad Request E2E")
        void testInvalidRegNumber() {
            ResponseEntity<String> response = restTemplate.getForEntity(baseUrl() + "/INVALID-REG", String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).contains("\"status\":400");
            assertThat(response.getBody()).contains("Bad Request");
        }

        @Test
        @DisplayName("GET /api/v1/vehicle/NOVALUE - Not Found E2E")
        void testVehicleNotFound() {
            ResponseEntity<String> response = restTemplate.getForEntity(baseUrl() + "/NOVALUE", String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody()).isEqualTo("Vehicle not found");
        }
    }

