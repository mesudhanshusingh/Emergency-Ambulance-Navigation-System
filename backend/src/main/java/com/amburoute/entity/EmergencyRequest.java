package com.amburoute.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String patientName;

    private Integer patientAge;

    @Column(columnDefinition = "TEXT")
    private String conditionDesc;

    @Column(nullable = false, length = 50)
    private String emergencyType; // CARDIAC, ACCIDENT_TRAUMA, RESPIRATORY, STROKE, PREGNANCY, OTHER

    @Column(nullable = false, length = 20)
    private String criticality; // CRITICAL, HIGH, MODERATE

    @Column(length = 30)
    private String status; // ACTIVATED, DISPATCHED, EN_ROUTE, ARRIVED, COMPLETED, CANCELLED

    @Column(nullable = false)
    private Double sourceLat;

    @Column(nullable = false)
    private Double sourceLng;

    @ManyToOne
    @JoinColumn(name = "assigned_hospital_id")
    private Hospital assignedHospital;

    @ManyToOne
    @JoinColumn(name = "assigned_ambulance_id")
    private Ambulance assignedAmbulance;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public EmergencyRequest() {}

    public EmergencyRequest(Long id, String patientName, Integer patientAge, String conditionDesc, String emergencyType, String criticality, String status, Double sourceLat, Double sourceLng, Hospital assignedHospital, Ambulance assignedAmbulance, LocalDateTime createdAt) {
        this.id = id;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.conditionDesc = conditionDesc;
        this.emergencyType = emergencyType;
        this.criticality = criticality;
        this.status = status;
        this.sourceLat = sourceLat;
        this.sourceLng = sourceLng;
        this.assignedHospital = assignedHospital;
        this.assignedAmbulance = assignedAmbulance;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Hospital getAssignedHospital() { return assignedHospital; }
    public void setAssignedHospital(Hospital assignedHospital) { this.assignedHospital = assignedHospital; }

    public Ambulance getAssignedAmbulance() { return assignedAmbulance; }
    public void setAssignedAmbulance(Ambulance assignedAmbulance) { this.assignedAmbulance = assignedAmbulance; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static EmergencyRequestBuilder builder() { return new EmergencyRequestBuilder(); }

    public static class EmergencyRequestBuilder {
        private Long id;
        private String patientName;
        private Integer patientAge;
        private String conditionDesc;
        private String emergencyType;
        private String criticality;
        private String status;
        private Double sourceLat;
        private Double sourceLng;
        private Hospital assignedHospital;
        private Ambulance assignedAmbulance;
        private LocalDateTime createdAt;

        public EmergencyRequestBuilder id(Long id) { this.id = id; return this; }
        public EmergencyRequestBuilder patientName(String patientName) { this.patientName = patientName; return this; }
        public EmergencyRequestBuilder patientAge(Integer patientAge) { this.patientAge = patientAge; return this; }
        public EmergencyRequestBuilder conditionDesc(String conditionDesc) { this.conditionDesc = conditionDesc; return this; }
        public EmergencyRequestBuilder emergencyType(String emergencyType) { this.emergencyType = emergencyType; return this; }
        public EmergencyRequestBuilder criticality(String criticality) { this.criticality = criticality; return this; }
        public EmergencyRequestBuilder status(String status) { this.status = status; return this; }
        public EmergencyRequestBuilder sourceLat(Double sourceLat) { this.sourceLat = sourceLat; return this; }
        public EmergencyRequestBuilder sourceLng(Double sourceLng) { this.sourceLng = sourceLng; return this; }
        public EmergencyRequestBuilder assignedHospital(Hospital hospital) { this.assignedHospital = hospital; return this; }
        public EmergencyRequestBuilder assignedAmbulance(Ambulance ambulance) { this.assignedAmbulance = ambulance; return this; }
        public EmergencyRequestBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public EmergencyRequest build() {
            return new EmergencyRequest(id, patientName, patientAge, conditionDesc, emergencyType, criticality, status, sourceLat, sourceLng, assignedHospital, assignedAmbulance, createdAt);
        }
    }
}
