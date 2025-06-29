package dev.modig.vehicle.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/vehicle/ABC123 - Success")
    void shouldReturnVehicleInfoWhenValidRegistrationExists() throws Exception {
        mockMvc.perform(get("/api/v1/vehicle/ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"))
                .andExpect(jsonPath("$.year").value(2018));
    }

    @Test
    @DisplayName("GET /api/v1/vehicle/INVALID-REG - Bad Request due to format")
    void shouldReturnBadRequestWhenInvalidFormat() throws Exception {
        mockMvc.perform(get("/api/v1/vehicle/INVALID-REG"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Invalid registration number")));
    }

    @Test
    @DisplayName("GET /api/v1/vehicle/UNKNOWN - Not Found")
    void shouldReturnNotFoundWhenVehicleDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/vehicle/UNKNOWN"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Vehicle not found"));
    }

    @Test
    @DisplayName("GET /api/v1/vehicle/xyz789 - Case insensitive match")
    void shouldReturnVehicleEvenIfLowerCaseRegistration() throws Exception {
        mockMvc.perform(get("/api/v1/vehicle/xyz789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Honda"));
    }
}
