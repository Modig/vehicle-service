package dev.modig.vehicle.controller;

import dev.modig.vehicle.exception.VehicleNotFoundException;
import dev.modig.vehicle.model.Vehicle;
import dev.modig.vehicle.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class VehicleControllerTest {

    private VehicleService vehicleService;
    private VehicleController vehicleController;

    @BeforeEach
    public void setUp() {
        vehicleService = mock(VehicleService.class);
        vehicleController = new VehicleController(vehicleService);
    }

    @Test
    public void shouldReturnVehicleInfoWhenValidRegistrationNumber() {
        String registration = "ABC123";
        Vehicle expectedResult = new Vehicle("ABC123", "Toyota", "Camry", 2018);

        when(vehicleService.getVehicle(registration)).thenReturn(expectedResult);

        ResponseEntity<?> response = vehicleController.getVehicleInfo(registration);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResult);
        verify(vehicleService).getVehicle(registration);
    }

    @Test
    public void shouldReturnNotFoundWhenVehicleDoesNotExist() {
        String registration = "XYZ789";

        when(vehicleService.getVehicle(registration)).thenThrow(new VehicleNotFoundException(registration));

        ResponseEntity<?> response = vehicleController.getVehicleInfo(registration);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Vehicle not found");
    }

    @Test
    public void shouldThrowBadRequestForInvalidRegistrationNumber() {
        String invalidRegistrationNumber = "###$$$";

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                vehicleController.getVehicleInfo(invalidRegistrationNumber));

        assertThat(exception.getStatusCode().value()).isEqualTo(400);
        assertThat(exception.getReason()).isEqualTo("Invalid registration number");
    }

    @Test
    public void shouldAcceptRegistrationNumberOfMaxLength10() {
        String reg = "ABC123XYZ9"; // 10 chars
        Vehicle vehicle = new Vehicle(reg, "Ford", "Focus", 2019);
        when(vehicleService.getVehicle(reg)).thenReturn(vehicle);

        ResponseEntity<?> response = vehicleController.getVehicleInfo(reg);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(vehicle);
    }

    @Test
    public void shouldRejectRegistrationNumberLongerThan10() {
        String reg = "TOOLONG1234"; // 11 chars

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                vehicleController.getVehicleInfo(reg));

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
