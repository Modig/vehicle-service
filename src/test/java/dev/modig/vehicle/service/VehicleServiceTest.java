package dev.modig.vehicle.service;

import dev.modig.vehicle.exception.VehicleNotFoundException;
import dev.modig.vehicle.model.Vehicle;
import dev.modig.vehicle.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository repository;

    @InjectMocks
    private VehicleService service;

    @Test
    void shouldReturnVehicleIfFound() {
        Vehicle vehicle = new Vehicle("ABC123", "Toyota", "Camry", 2018);
        when(repository.findByRegistrationNumber("ABC123")).thenReturn(Optional.of(vehicle));

        Vehicle result = service.getVehicle("ABC123");

        assertThat(result).isEqualTo(vehicle);
        verify(repository).findByRegistrationNumber("ABC123");
    }

    @Test
    void shouldThrowExceptionIfNotFound() {
        when(repository.findByRegistrationNumber("MISSING")).thenReturn(Optional.empty());

        VehicleNotFoundException ex = assertThrows(VehicleNotFoundException.class, () ->
                service.getVehicle("MISSING"));

        assertThat(ex.getMessage()).contains("MISSING");
    }
}
