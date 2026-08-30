package com.amburoute.controller;

import com.amburoute.service.GreenCorridorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "*")
public class GreenCorridorController {

    private final GreenCorridorService greenCorridorService;

    public GreenCorridorController(GreenCorridorService greenCorridorService) {
        this.greenCorridorService = greenCorridorService;
    }

    @PostMapping("/green-corridor")
    public ResponseEntity<Map<String, Object>> activateGreenCorridor(
            @RequestParam Long emergencyId,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng) {
        return ResponseEntity.ok(greenCorridorService.activateGreenCorridor(emergencyId, lat, lng));
    }
}
