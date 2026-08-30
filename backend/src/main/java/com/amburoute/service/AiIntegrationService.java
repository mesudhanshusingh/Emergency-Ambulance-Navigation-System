package com.amburoute.service;

import com.amburoute.dto.ChatDTOs;
import com.amburoute.dto.HospitalDTOs;
import com.amburoute.entity.Hospital;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AiIntegrationService {

    @Value("${amburoute.ai-service.url:http://localhost:8000}")
    private String aiServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<HospitalDTOs.HospitalDTO> getAiHospitalRecommendations(
            Double patientLat, Double patientLng, String emergencyType, String criticality,
            String conditionDesc, List<Hospital> availableHospitals) {

        try {
            // Prepare payload for Python FastAPI AI Service
            List<Map<String, Object>> hospitalPayloads = new ArrayList<>();
            for (Hospital h : availableHospitals) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", h.getId());
                map.put("name", h.getName());
                map.put("latitude", h.getLatitude());
                map.put("longitude", h.getLongitude());
                
                int icu = h.getBeds().stream().filter(b -> "ICU".equalsIgnoreCase(b.getBedType())).mapToInt(b -> b.getAvailableCount()).sum();
                int em = h.getBeds().stream().filter(b -> "EMERGENCY".equalsIgnoreCase(b.getBedType())).mapToInt(b -> b.getAvailableCount()).sum();
                
                map.put("icu_available", icu);
                map.put("emergency_available", em);
                map.put("rating", h.getRating() != null ? h.getRating() : 4.5);
                hospitalPayloads.add(map);
            }

            Map<String, Object> requestPayload = new HashMap<>();
            requestPayload.put("patient_condition", conditionDesc != null ? conditionDesc : emergencyType);
            requestPayload.put("emergency_type", emergencyType);
            requestPayload.put("criticality", criticality);
            requestPayload.put("patient_lat", patientLat);
            requestPayload.put("patient_lng", patientLng);
            requestPayload.put("hospitals", hospitalPayloads);

            String endpoint = aiServiceUrl + "/ai/hospital-recommendation";
            ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, requestPayload, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<Map<String, Object>> recs = (List<Map<String, Object>>) response.getBody().get("recommended_hospitals");
                List<HospitalDTOs.HospitalDTO> dtoList = new ArrayList<>();
                
                Map<Long, Hospital> hospitalMap = new HashMap<>();
                for (Hospital h : availableHospitals) hospitalMap.put(h.getId(), h);

                for (Map<String, Object> rec : recs) {
                    Long hId = ((Number) rec.get("hospital_id")).longValue();
                    Hospital h = hospitalMap.get(hId);
                    if (h != null) {
                        dtoList.add(HospitalDTOs.HospitalDTO.builder()
                                .id(h.getId())
                                .name(h.getName())
                                .latitude(h.getLatitude())
                                .longitude(h.getLongitude())
                                .address(h.getAddress())
                                .phone(h.getPhone())
                                .emergencyStatus(h.getEmergencyStatus())
                                .rating(h.getRating())
                                .distanceKm(((Number) rec.get("distance_km")).doubleValue())
                                .matchPercentage(((Number) rec.get("match_percentage")).intValue())
                                .recommendationTier((String) rec.get("recommendation_tier"))
                                .explanation((String) rec.get("explanation"))
                                .build());
                    }
                }
                return dtoList;
            }
        } catch (Exception e) {
            System.err.println("⚠️ Python AI Service unreachable or returned error. Falling back to local heuristic recommendation engine.");
        }

        // Safe Local Fallback Logic
        return calculateLocalFallbackRecommendations(patientLat, patientLng, emergencyType, criticality, availableHospitals);
    }

    public ChatDTOs.ChatMessageResponse getAiChatResponse(String message, String contextEmergencyType) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("message", message);
            payload.put("context_emergency_type", contextEmergencyType);

            String endpoint = aiServiceUrl + "/ai/chat-triage";
            ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, payload, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map body = response.getBody();
                return ChatDTOs.ChatMessageResponse.builder()
                        .reply((String) body.get("reply"))
                        .detectedEmergencyType((String) body.get("detected_emergency_type"))
                        .suggestedCriticality((String) body.get("suggested_criticality"))
                        .recommendEmergencyActivation((Boolean) body.get("recommend_emergency_activation"))
                        .medicalDisclaimer((String) body.get("medical_disclaimer"))
                        .build();
            }
        } catch (Exception e) {
            System.err.println("⚠️ Python AI Chat service fallback triggered.");
        }

        // Local fallback triage response
        String lower = message.toLowerCase();
        boolean isCrit = lower.contains("chest pain") || lower.contains("heart") || lower.contains("accident") || lower.contains("bleed");
        return ChatDTOs.ChatMessageResponse.builder()
                .reply(isCrit ? 
                    "🚨 URGENT: The symptoms described suggest a potential emergency. Please click ACTIVATE EMERGENCY to dispatch an ambulance." : 
                    "Please describe the symptoms (e.g., chest pain, breathing trouble). For emergencies, click ACTIVATE EMERGENCY.")
                .detectedEmergencyType(isCrit ? "CARDIAC" : "OTHER")
                .suggestedCriticality(isCrit ? "CRITICAL" : "MODERATE")
                .recommendEmergencyActivation(isCrit)
                .medicalDisclaimer("⚠️ Disclaimer: Decision support only. In life-threatening emergencies, contact emergency medical services.")
                .build();
    }

    private List<HospitalDTOs.HospitalDTO> calculateLocalFallbackRecommendations(
            Double lat, Double lng, String emergencyType, String criticality, List<Hospital> hospitals) {

        List<HospitalDTOs.HospitalDTO> list = new ArrayList<>();
        for (Hospital h : hospitals) {
            double dist = haversine(lat, lng, h.getLatitude(), h.getLongitude());
            int icu = h.getBeds().stream().filter(b -> "ICU".equalsIgnoreCase(b.getBedType())).mapToInt(b -> b.getAvailableCount()).sum();
            
            int match = (int) Math.max(50, 95 - (dist * 5) + (icu * 3));
            String tier = match >= 80 ? "HIGHLY_RECOMMENDED" : (match >= 65 ? "SUITABLE" : "FALLBACK");
            
            list.add(HospitalDTOs.HospitalDTO.builder()
                    .id(h.getId())
                    .name(h.getName())
                    .latitude(h.getLatitude())
                    .longitude(h.getLongitude())
                    .address(h.getAddress())
                    .phone(h.getPhone())
                    .emergencyStatus(h.getEmergencyStatus())
                    .rating(h.getRating())
                    .distanceKm(round(dist, 2))
                    .matchPercentage(Math.min(98, match))
                    .recommendationTier(tier)
                    .explanation("Local heuristic score: Distance " + round(dist, 1) + " km, " + icu + " ICU beds available.")
                    .build());
        }
        list.sort((a, b) -> Integer.compare(b.getMatchPercentage(), a.getMatchPercentage()));
        return list;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    private double round(double val, int places) {
        double scale = Math.pow(10, places);
        return Math.round(val * scale) / scale;
    }
}
