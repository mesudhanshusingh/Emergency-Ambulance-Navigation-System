package com.amburoute.controller;

import com.amburoute.dto.HospitalDTOs;
import com.amburoute.service.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@CrossOrigin(origins = "*")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public ResponseEntity<List<HospitalDTOs.HospitalDTO>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<HospitalDTOs.HospitalDTO>> getRecommendedHospitals(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "CARDIAC") String emergencyType,
            @RequestParam(defaultValue = "CRITICAL") String criticality,
            @RequestParam(required = false) String conditionDesc) {
        return ResponseEntity.ok(hospitalService.getRecommendedHospitals(lat, lng, emergencyType, criticality, conditionDesc));
    }

    @PostMapping("/reserve-bed")
    public ResponseEntity<HospitalDTOs.BedReservationResponse> reserveBed(@RequestBody HospitalDTOs.BedReservationRequest request) {
        return ResponseEntity.ok(hospitalService.reserveBed(request));
    }
}
