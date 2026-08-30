package com.amburoute.controller;

import com.amburoute.dto.RailwayDTOs;
import com.amburoute.service.RailwayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/railway")
@CrossOrigin(origins = "*")
public class RailwayController {

    private final RailwayService railwayService;

    public RailwayController(RailwayService railwayService) {
        this.railwayService = railwayService;
    }

    @GetMapping("/crossings")
    public ResponseEntity<List<RailwayDTOs.RailwayCrossingDTO>> getAllCrossings() {
        return ResponseEntity.ok(railwayService.getAllCrossings());
    }
}
