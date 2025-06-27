package dev.modig.vehicle.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String registrationNumber) {
        super("Vehicle not found: " + registrationNumber);
    }
}
