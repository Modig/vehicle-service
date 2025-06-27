package dev.modig.vehicle.repository;

import dev.modig.vehicle.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@Repository
public class VehicleRepository {

    private final Map<String, Vehicle> vehicleRegistry = new HashMap<>();

    public VehicleRepository() {
        vehicleRegistry.put("ABC123", new dev.modig.vehicle.model.Vehicle("ABC123", "Toyota", "Camry", 2018));
        vehicleRegistry.put("XYZ789", new dev.modig.vehicle.model.Vehicle("XYZ789", "Honda", "Civic", 2020));
        vehicleRegistry.put("AAA111", new dev.modig.vehicle.model.Vehicle("AAA111", "Ferrari", "F40", 1992));
        vehicleRegistry.put("ABC12A", new dev.modig.vehicle.model.Vehicle("ABC12A", "BMW", "320", 2021));
        vehicleRegistry.put("AUD00I", new dev.modig.vehicle.model.Vehicle("AUD00I", "Audi", "Q7", 2020));
    }

    public Optional<Vehicle> findByRegistrationNumber(String regNumber) {
        return Optional.ofNullable(vehicleRegistry.get(regNumber.toUpperCase()));
    }
}
