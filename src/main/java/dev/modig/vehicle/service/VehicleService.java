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

    /**
     * Retrieves a vehicle by registration number.
     *
     * @param regNumber the vehicle's registration number
     * @return the Vehicle object
     * @throws VehicleNotFoundException if no vehicle is found
     */
    public Vehicle getVehicle(String regNumber) {
        return repository.findByRegistrationNumber(regNumber)
                .orElseThrow(() -> new VehicleNotFoundException(regNumber));
    }
}
