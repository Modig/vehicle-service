package dev.modig.vehicle.controller;

import dev.modig.vehicle.exception.VehicleNotFoundException;
import dev.modig.vehicle.model.Vehicle;
import dev.modig.vehicle.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST controller for vehicle-related API endpoints.
 * Provides vehicle lookup based on registration number.
 */
@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService service) {
        this.vehicleService = service;
    }

    /**
     * Returns vehicle information based on registration number.
     *
     * @param registrationNumber the vehicle's registration number
     * @return ResponseEntity containing vehicle data or error message
     */
    @Operation(summary = "Get vehicle by registration number")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vehicle found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vehicle.class))), @ApiResponse(responseCode = "404", description = "Vehicle not found"), @ApiResponse(responseCode = "400", description = "Invalid registration number")})
    @GetMapping("/{registrationNumber}")
    public ResponseEntity<?> getVehicleInfo(@PathVariable("registrationNumber") String registrationNumber) {
        if (!registrationNumber.matches("^[A-Za-z0-9]{1,10}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid registration number");
        }

        try {
            return ResponseEntity.ok(vehicleService.getVehicle(registrationNumber));
        } catch (VehicleNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
        }
    }
}
