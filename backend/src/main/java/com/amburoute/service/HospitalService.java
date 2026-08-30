package com.amburoute.service;

import com.amburoute.dto.HospitalDTOs;
import com.amburoute.entity.*;
import com.amburoute.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalBedRepository hospitalBedRepository;
    private final BedReservationRepository bedReservationRepository;
    private final EmergencyRequestRepository emergencyRequestRepository;
    private final AiIntegrationService aiIntegrationService;

    public HospitalService(HospitalRepository hospitalRepository,
                           HospitalBedRepository hospitalBedRepository,
                           BedReservationRepository bedReservationRepository,
                           EmergencyRequestRepository emergencyRequestRepository,
                           AiIntegrationService aiIntegrationService) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalBedRepository = hospitalBedRepository;
        this.bedReservationRepository = bedReservationRepository;
        this.emergencyRequestRepository = emergencyRequestRepository;
        this.aiIntegrationService = aiIntegrationService;
    }

    public List<HospitalDTOs.HospitalDTO> getAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        List<HospitalDTOs.HospitalDTO> dtoList = new ArrayList<>();
        for (Hospital h : hospitals) {
            dtoList.add(mapToDTO(h, null));
        }
        return dtoList;
    }

    public List<HospitalDTOs.HospitalDTO> getRecommendedHospitals(
            Double patientLat, Double patientLng, String emergencyType, String criticality, String conditionDesc) {

        List<Hospital> hospitals = hospitalRepository.findAllActive();
        return aiIntegrationService.getAiHospitalRecommendations(patientLat, patientLng, emergencyType, criticality, conditionDesc, hospitals);
    }

    public HospitalDTOs.BedReservationResponse reserveBed(HospitalDTOs.BedReservationRequest request) {
        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found: " + request.getHospitalId()));

        EmergencyRequest emergency = emergencyRequestRepository.findById(request.getEmergencyId())
                .orElseThrow(() -> new RuntimeException("Emergency request not found: " + request.getEmergencyId()));

        HospitalBed bed = hospitalBedRepository.findByHospitalIdAndBedType(request.getHospitalId(), request.getBedType())
                .orElseThrow(() -> new RuntimeException("Bed type not found in hospital: " + request.getBedType()));

        if (bed.getAvailableCount() <= 0) {
            throw new RuntimeException("No available beds of type: " + request.getBedType());
        }

        // Decrement available beds
        bed.setAvailableCount(bed.getAvailableCount() - 1);
        hospitalBedRepository.save(bed);

        BedReservation reservation = BedReservation.builder()
                .emergency(emergency)
                .hospital(hospital)
                .bedType(request.getBedType())
                .patientName(request.getPatientName())
                .status("CONFIRMED")
                .build();

        BedReservation saved = bedReservationRepository.save(reservation);

        return HospitalDTOs.BedReservationResponse.builder()
                .reservationId(saved.getId())
                .emergencyId(emergency.getId())
                .hospitalName(hospital.getName())
                .bedType(saved.getBedType())
                .patientName(saved.getPatientName())
                .status(saved.getStatus())
                .reservedAt(saved.getReservedAt() != null ? saved.getReservedAt().toString() : "JUST NOW")
                .build();
    }

    private HospitalDTOs.HospitalDTO mapToDTO(Hospital h, Double dist) {
        List<HospitalDTOs.BedInfoDTO> bedDTOs = new ArrayList<>();
        if (h.getBeds() != null) {
            for (HospitalBed b : h.getBeds()) {
                bedDTOs.add(HospitalDTOs.BedInfoDTO.builder()
                        .bedType(b.getBedType())
                        .totalCapacity(b.getTotalCapacity())
                        .availableCount(b.getAvailableCount())
                        .build());
            }
        }

        return HospitalDTOs.HospitalDTO.builder()
                .id(h.getId())
                .name(h.getName())
                .latitude(h.getLatitude())
                .longitude(h.getLongitude())
                .address(h.getAddress())
                .phone(h.getPhone())
                .emergencyStatus(h.getEmergencyStatus())
                .rating(h.getRating())
                .distanceKm(dist)
                .beds(bedDTOs)
                .build();
    }
}
