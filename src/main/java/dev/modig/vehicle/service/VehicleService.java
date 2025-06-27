package dev.modig.vehicle.service;

import dev.modig.vehicle.exception.VehicleNotFoundException;
import dev.modig.vehicle.model.Vehicle;
import dev.modig.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public Vehicle getVehicle(String regNumber) {
        return repository.findByRegistrationNumber(regNumber)
                .orElseThrow(() -> new VehicleNotFoundException(regNumber));
    }
}
