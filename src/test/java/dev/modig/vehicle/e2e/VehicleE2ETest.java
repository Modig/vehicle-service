package dev.modig.vehicle.e2e;

import dev.modig.vehicle.VehicleServiceApplication;
import dev.modig.vehicle.model.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = VehicleServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VehicleE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/vehicle";
    }

    @Test
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
    void testInvalidRegNumber() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl() + "/INVALID-REG", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("\"status\":400");
        assertThat(response.getBody()).contains("Bad Request");
    }

    @Test
    void testVehicleNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl() + "/NOVALUE", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Vehicle not found");
    }
}
