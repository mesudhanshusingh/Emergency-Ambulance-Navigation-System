package com.amburoute.controller;

import com.amburoute.entity.Ambulance;
import com.amburoute.service.AmbulanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ambulances")
@CrossOrigin(origins = "*")
public class AmbulanceController {

    private final AmbulanceService ambulanceService;

    public AmbulanceController(AmbulanceService ambulanceService) {
        this.ambulanceService = ambulanceService;
    }

    @GetMapping
    public ResponseEntity<List<Ambulance>> getAllAmbulances() {
        return ResponseEntity.ok(ambulanceService.getAllAmbulances());
    }

    @PostMapping("/{id}/location")
    public ResponseEntity<Ambulance> updateLocation(
            @PathVariable Long id,
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(required = false) Double speed,
            @RequestParam(required = false) Double heading) {
        return ResponseEntity.ok(ambulanceService.updateAmbulanceLocation(id, lat, lng, speed, heading));
    }
}
