package com.amburoute.service;

import com.amburoute.entity.Ambulance;
import com.amburoute.repository.AmbulanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;

    public AmbulanceService(AmbulanceRepository ambulanceRepository) {
        this.ambulanceRepository = ambulanceRepository;
    }

    public List<Ambulance> getAllAmbulances() {
        return ambulanceRepository.findAll();
    }

    public Ambulance updateAmbulanceLocation(Long id, Double lat, Double lng, Double speed, Double heading) {
        Ambulance ambulance = ambulanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ambulance not found: " + id));

        ambulance.setLatitude(lat);
        ambulance.setLongitude(lng);
        if (speed != null) ambulance.setSpeed(speed);
        if (heading != null) ambulance.setHeading(heading);
        ambulance.setUpdatedAt(LocalDateTime.now());

        return ambulanceRepository.save(ambulance);
    }
}
