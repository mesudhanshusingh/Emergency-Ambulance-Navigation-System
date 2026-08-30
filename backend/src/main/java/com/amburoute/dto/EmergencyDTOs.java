package com.amburoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public class EmergencyDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmergencyActivationRequest {
        private String patientName;
        private Integer patientAge;
        private String conditionDesc;
        private String emergencyType; // CARDIAC, ACCIDENT_TRAUMA, RESPIRATORY, STROKE, PREGNANCY, OTHER
        private String criticality;    // CRITICAL, HIGH, MODERATE
        private Double sourceLat;
        private Double sourceLng;
        private Long selectedHospitalId;

        public String getPatientName() { return patientName; }
        public void setPatientName(String patientName) { this.patientName = patientName; }

        public Integer getPatientAge() { return patientAge; }
        public void setPatientAge(Integer patientAge) { this.patientAge = patientAge; }

        public String getConditionDesc() { return conditionDesc; }
        public void setConditionDesc(String conditionDesc) { this.conditionDesc = conditionDesc; }

        public String getEmergencyType() { return emergencyType; }
        public void setEmergencyType(String emergencyType) { this.emergencyType = emergencyType; }

        public String getCriticality() { return criticality; }
        public void setCriticality(String criticality) { this.criticality = criticality; }

        public Double getSourceLat() { return sourceLat; }
        public void setSourceLat(Double sourceLat) { this.sourceLat = sourceLat; }

        public Double getSourceLng() { return sourceLng; }
        public void setSourceLng(Double sourceLng) { this.sourceLng = sourceLng; }

        public Long getSelectedHospitalId() { return selectedHospitalId; }
        public void setSelectedHospitalId(Long selectedHospitalId) { this.selectedHospitalId = selectedHospitalId; }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EmergencyResponseDTO {
        private Long emergencyId;
        private String patientName;
        private Integer patientAge;
        private String conditionDesc;
        private String emergencyType;
        private String criticality;
        private String status;
        private Double sourceLat;
        private Double sourceLng;
        private Long assignedHospitalId;
        private String hospitalName;
        private Long assignedAmbulanceId;
        private String ambulanceVehicleNumber;
        private List<RouteDTOs.RouteOptionDTO> routes;
        private Boolean greenCorridorActive;
        private String createdAt;

        public EmergencyResponseDTO() {}

        public EmergencyResponseDTO(Long emergencyId, String patientName, Integer patientAge, String conditionDesc, String emergencyType, String criticality, String status, Double sourceLat, Double sourceLng, Long assignedHospitalId, String hospitalName, Long assignedAmbulanceId, String ambulanceVehicleNumber, List<RouteDTOs.RouteOptionDTO> routes, Boolean greenCorridorActive, String createdAt) {
            this.emergencyId = emergencyId;
            this.patientName = patientName;
            this.patientAge = patientAge;
            this.conditionDesc = conditionDesc;
            this.emergencyType = emergencyType;
            this.criticality = criticality;
            this.status = status;
            this.sourceLat = sourceLat;
            this.sourceLng = sourceLng;
            this.assignedHospitalId = assignedHospitalId;
            this.hospitalName = hospitalName;
            this.assignedAmbulanceId = assignedAmbulanceId;
            this.ambulanceVehicleNumber = ambulanceVehicleNumber;
            this.routes = routes;
            this.greenCorridorActive = greenCorridorActive;
            this.createdAt = createdAt;
        }

        public Long getEmergencyId() { return emergencyId; }
        public void setEmergencyId(Long emergencyId) { this.emergencyId = emergencyId; }

        public String getPatientName() { return patientName; }
        public void setPatientName(String patientName) { this.patientName = patientName; }

        public Integer getPatientAge() { return patientAge; }
        public void setPatientAge(Integer patientAge) { this.patientAge = patientAge; }

        public String getConditionDesc() { return conditionDesc; }
        public void setConditionDesc(String conditionDesc) { this.conditionDesc = conditionDesc; }

        public String getEmergencyType() { return emergencyType; }
        public void setEmergencyType(String emergencyType) { this.emergencyType = emergencyType; }

        public String getCriticality() { return criticality; }
        public void setCriticality(String criticality) { this.criticality = criticality; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Double getSourceLat() { return sourceLat; }
        public void setSourceLat(Double sourceLat) { this.sourceLat = sourceLat; }

        public Double getSourceLng() { return sourceLng; }
        public void setSourceLng(Double sourceLng) { this.sourceLng = sourceLng; }

        public Long getAssignedHospitalId() { return assignedHospitalId; }
        public void setAssignedHospitalId(Long assignedHospitalId) { this.assignedHospitalId = assignedHospitalId; }

        public String getHospitalName() { return hospitalName; }
        public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

        public Long getAssignedAmbulanceId() { return assignedAmbulanceId; }
        public void setAssignedAmbulanceId(Long assignedAmbulanceId) { this.assignedAmbulanceId = assignedAmbulanceId; }

        public String getAmbulanceVehicleNumber() { return ambulanceVehicleNumber; }
        public void setAmbulanceVehicleNumber(String ambulanceVehicleNumber) { this.ambulanceVehicleNumber = ambulanceVehicleNumber; }

        public List<RouteDTOs.RouteOptionDTO> getRoutes() { return routes; }
        public void setRoutes(List<RouteDTOs.RouteOptionDTO> routes) { this.routes = routes; }

        public Boolean getGreenCorridorActive() { return greenCorridorActive; }
        public void setGreenCorridorActive(Boolean greenCorridorActive) { this.greenCorridorActive = greenCorridorActive; }

        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

        public static EmergencyResponseDTOBuilder builder() {
            return new EmergencyResponseDTOBuilder();
        }

        public static class EmergencyResponseDTOBuilder {
            private Long emergencyId;
            private String patientName;
            private Integer patientAge;
            private String conditionDesc;
            private String emergencyType;
            private String criticality;
            private String status;
            private Double sourceLat;
            private Double sourceLng;
            private Long assignedHospitalId;
            private String hospitalName;
            private Long assignedAmbulanceId;
            private String ambulanceVehicleNumber;
            private List<RouteDTOs.RouteOptionDTO> routes;
            private Boolean greenCorridorActive;
            private String createdAt;

            public EmergencyResponseDTOBuilder emergencyId(Long emergencyId) { this.emergencyId = emergencyId; return this; }
            public EmergencyResponseDTOBuilder patientName(String patientName) { this.patientName = patientName; return this; }
            public EmergencyResponseDTOBuilder patientAge(Integer patientAge) { this.patientAge = patientAge; return this; }
            public EmergencyResponseDTOBuilder conditionDesc(String conditionDesc) { this.conditionDesc = conditionDesc; return this; }
            public EmergencyResponseDTOBuilder emergencyType(String emergencyType) { this.emergencyType = emergencyType; return this; }
            public EmergencyResponseDTOBuilder criticality(String criticality) { this.criticality = criticality; return this; }
            public EmergencyResponseDTOBuilder status(String status) { this.status = status; return this; }
            public EmergencyResponseDTOBuilder sourceLat(Double sourceLat) { this.sourceLat = sourceLat; return this; }
            public EmergencyResponseDTOBuilder sourceLng(Double sourceLng) { this.sourceLng = sourceLng; return this; }
            public EmergencyResponseDTOBuilder assignedHospitalId(Long assignedHospitalId) { this.assignedHospitalId = assignedHospitalId; return this; }
            public EmergencyResponseDTOBuilder hospitalName(String hospitalName) { this.hospitalName = hospitalName; return this; }
            public EmergencyResponseDTOBuilder assignedAmbulanceId(Long assignedAmbulanceId) { this.assignedAmbulanceId = assignedAmbulanceId; return this; }
            public EmergencyResponseDTOBuilder ambulanceVehicleNumber(String ambulanceVehicleNumber) { this.ambulanceVehicleNumber = ambulanceVehicleNumber; return this; }
            public EmergencyResponseDTOBuilder routes(List<RouteDTOs.RouteOptionDTO> routes) { this.routes = routes; return this; }
            public EmergencyResponseDTOBuilder greenCorridorActive(Boolean greenCorridorActive) { this.greenCorridorActive = greenCorridorActive; return this; }
            public EmergencyResponseDTOBuilder createdAt(String createdAt) { this.createdAt = createdAt; return this; }

            public EmergencyResponseDTO build() {
                return new EmergencyResponseDTO(emergencyId, patientName, patientAge, conditionDesc, emergencyType, criticality, status, sourceLat, sourceLng, assignedHospitalId, hospitalName, assignedAmbulanceId, ambulanceVehicleNumber, routes, greenCorridorActive, createdAt);
            }
        }
    }
}
