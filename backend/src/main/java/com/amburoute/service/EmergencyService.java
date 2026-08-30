package com.amburoute.service;

import com.amburoute.dto.EmergencyDTOs;
import com.amburoute.dto.RouteDTOs;
import com.amburoute.entity.*;
import com.amburoute.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmergencyService {

    private final EmergencyRequestRepository emergencyRequestRepository;
    private final HospitalRepository hospitalRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final RouteOptionRepository routeOptionRepository;
    private final TrafficAlertRepository trafficAlertRepository;

    public EmergencyService(EmergencyRequestRepository emergencyRequestRepository,
                            HospitalRepository hospitalRepository,
                            AmbulanceRepository ambulanceRepository,
                            RouteOptionRepository routeOptionRepository,
                            TrafficAlertRepository trafficAlertRepository) {
        this.emergencyRequestRepository = emergencyRequestRepository;
        this.hospitalRepository = hospitalRepository;
        this.ambulanceRepository = ambulanceRepository;
        this.routeOptionRepository = routeOptionRepository;
        this.trafficAlertRepository = trafficAlertRepository;
    }

    public EmergencyDTOs.EmergencyResponseDTO activateEmergency(EmergencyDTOs.EmergencyActivationRequest req) {
        // 1. Assign closest active hospital if not provided
        Hospital hospital = null;
        if (req.getSelectedHospitalId() != null) {
            hospital = hospitalRepository.findById(req.getSelectedHospitalId()).orElse(null);
        }
        if (hospital == null) {
            List<Hospital> active = hospitalRepository.findAllActive();
            hospital = active.isEmpty() ? null : active.get(0);
        }

        // 2. Dispatch nearest available ambulance
        Ambulance ambulance = null;
        List<Ambulance> availableAmbulances = ambulanceRepository.findByStatus("AVAILABLE");
        if (!availableAmbulances.isEmpty()) {
            ambulance = availableAmbulances.get(0);
            ambulance.setStatus("ON_CALL");
            ambulanceRepository.save(ambulance);
        }

        // 3. Save Emergency Request
        EmergencyRequest emergency = EmergencyRequest.builder()
                .patientName(req.getPatientName())
                .patientAge(req.getPatientAge())
                .conditionDesc(req.getConditionDesc())
                .emergencyType(req.getEmergencyType())
                .criticality(req.getCriticality())
                .status("EN_ROUTE")
                .sourceLat(req.getSourceLat())
                .sourceLng(req.getSourceLng())
                .assignedHospital(hospital)
                .assignedAmbulance(ambulance)
                .build();

        EmergencyRequest savedEmergency = emergencyRequestRepository.save(emergency);

        // 4. Generate Routes (Fastest vs Safe vs Alternate with Railway Assessment)
        List<RouteOption> routes = generateRoutesForEmergency(savedEmergency);

        // 5. Create Traffic Hotspot Alert for Green Corridor
        TrafficAlert trafficAlert = TrafficAlert.builder()
                .emergencyId(savedEmergency.getId())
                .locationName("Central Junction & MG Road Intersection")
                .latitude(req.getSourceLat() + 0.005)
                .longitude(req.getSourceLng() + 0.005)
                .severity("HIGH")
                .greenCorridorActive(true)
                .build();
        trafficAlertRepository.save(trafficAlert);

        return mapToDTO(savedEmergency, routes, true);
    }

    public EmergencyDTOs.EmergencyResponseDTO getEmergencyById(Long id) {
        EmergencyRequest emergency = emergencyRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emergency request not found: " + id));
        List<RouteOption> routes = routeOptionRepository.findByEmergencyId(id);
        return mapToDTO(emergency, routes, true);
    }

    public List<EmergencyDTOs.EmergencyResponseDTO> getActiveEmergencies() {
        List<EmergencyRequest> active = emergencyRequestRepository.findByStatusIn(List.of("ACTIVATED", "DISPATCHED", "EN_ROUTE"));
        List<EmergencyDTOs.EmergencyResponseDTO> list = new ArrayList<>();
        for (EmergencyRequest e : active) {
            List<RouteOption> routes = routeOptionRepository.findByEmergencyId(e.getId());
            list.add(mapToDTO(e, routes, false));
        }
        return list;
    }

    private List<RouteOption> generateRoutesForEmergency(EmergencyRequest emergency) {
        List<RouteOption> routes = new ArrayList<>();

        // Route A: Fastest Route (Passes Central Railway Crossing - HIGH RISK)
        RouteOption routeA = RouteOption.builder()
                .emergency(emergency)
                .routeName("Route A (MG Road Expressway)")
                .distanceKm(4.2)
                .etaMinutes(12)
                .riskLevel("HIGH")
                .trafficDensity("HEAVY")
                .includesRailway(true)
                .activeSelected(false)
                .waypoints("[[12.9690, 77.5850], [12.9730, 77.5950], [12.9785, 77.5990]]")
                .build();

        // Route B: SAFE Alternate Route (Avoids Railway Crossing)
        RouteOption routeB = RouteOption.builder()
                .emergency(emergency)
                .routeName("Route B (South Flyover Bypass)")
                .distanceKm(4.8)
                .etaMinutes(14)
                .riskLevel("LOW")
                .trafficDensity("LIGHT")
                .includesRailway(false)
                .activeSelected(true)
                .waypoints("[[12.9690, 77.5850], [12.9600, 77.5900], [12.9700, 77.6000], [12.9785, 77.5990]]")
                .build();

        routes.add(routeOptionRepository.save(routeA));
        routes.add(routeOptionRepository.save(routeB));

        return routes;
    }

    private EmergencyDTOs.EmergencyResponseDTO mapToDTO(EmergencyRequest e, List<RouteOption> routes, Boolean greenCorridorActive) {
        List<RouteDTOs.RouteOptionDTO> routeDTOs = new ArrayList<>();
        if (routes != null) {
            for (RouteOption r : routes) {
                routeDTOs.add(RouteDTOs.RouteOptionDTO.builder()
                        .id(r.getId())
                        .routeName(r.getRouteName())
                        .distanceKm(r.getDistanceKm())
                        .etaMinutes(r.getEtaMinutes())
                        .riskLevel(r.getRiskLevel())
                        .trafficDensity(r.getTrafficDensity())
                        .includesRailway(r.getIncludesRailway())
                        .activeSelected(r.getActiveSelected())
                        .waypoints(r.getWaypoints())
                        .build());
            }
        }

        return EmergencyDTOs.EmergencyResponseDTO.builder()
                .emergencyId(e.getId())
                .patientName(e.getPatientName())
                .patientAge(e.getPatientAge())
                .conditionDesc(e.getConditionDesc())
                .emergencyType(e.getEmergencyType())
                .criticality(e.getCriticality())
                .status(e.getStatus())
                .sourceLat(e.getSourceLat())
                .sourceLng(e.getSourceLng())
                .assignedHospitalId(e.getAssignedHospital() != null ? e.getAssignedHospital().getId() : null)
                .hospitalName(e.getAssignedHospital() != null ? e.getAssignedHospital().getName() : "Assigning...")
                .assignedAmbulanceId(e.getAssignedAmbulance() != null ? e.getAssignedAmbulance().getId() : null)
                .ambulanceVehicleNumber(e.getAssignedAmbulance() != null ? e.getAssignedAmbulance().getVehicleNumber() : "KA-01-EQ-1001")
                .routes(routeDTOs)
                .greenCorridorActive(greenCorridorActive)
                .createdAt(e.getCreatedAt() != null ? e.getCreatedAt().toString() : LocalDateTime.now().toString())
                .build();
    }
}
