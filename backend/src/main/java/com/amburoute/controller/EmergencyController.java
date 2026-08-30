package com.amburoute.controller;

import com.amburoute.dto.EmergencyDTOs;
import com.amburoute.service.EmergencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emergency")
@CrossOrigin(origins = "*")
public class EmergencyController {

    private final EmergencyService emergencyService;

    public EmergencyController(EmergencyService emergencyService) {
        this.emergencyService = emergencyService;
    }

    @PostMapping("/activate")
    public ResponseEntity<EmergencyDTOs.EmergencyResponseDTO> activateEmergency(@RequestBody EmergencyDTOs.EmergencyActivationRequest request) {
        return ResponseEntity.ok(emergencyService.activateEmergency(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmergencyDTOs.EmergencyResponseDTO> getEmergencyById(@PathVariable Long id) {
        return ResponseEntity.ok(emergencyService.getEmergencyById(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<EmergencyDTOs.EmergencyResponseDTO>> getActiveEmergencies() {
        return ResponseEntity.ok(emergencyService.getActiveEmergencies());
    }
}
