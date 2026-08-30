package com.amburoute.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "traffic_alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrafficAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long emergencyId;

    @Column(nullable = false, length = 150)
    private String locationName;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false, length = 20)
    private String severity; // HIGH, MEDIUM, LOW

    private Boolean greenCorridorActive;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public TrafficAlert() {}

    public TrafficAlert(Long id, Long emergencyId, String locationName, Double latitude, Double longitude, String severity, Boolean greenCorridorActive, LocalDateTime createdAt) {
        this.id = id;
        this.emergencyId = emergencyId;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.severity = severity;
        this.greenCorridorActive = greenCorridorActive;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmergencyId() { return emergencyId; }
    public void setEmergencyId(Long emergencyId) { this.emergencyId = emergencyId; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public Boolean getGreenCorridorActive() { return greenCorridorActive; }
    public void setGreenCorridorActive(Boolean greenCorridorActive) { this.greenCorridorActive = greenCorridorActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static TrafficAlertBuilder builder() { return new TrafficAlertBuilder(); }

    public static class TrafficAlertBuilder {
        private Long id;
        private Long emergencyId;
        private String locationName;
        private Double latitude;
        private Double longitude;
        private String severity;
        private Boolean greenCorridorActive;
        private LocalDateTime createdAt;

        public TrafficAlertBuilder id(Long id) { this.id = id; return this; }
        public TrafficAlertBuilder emergencyId(Long emergencyId) { this.emergencyId = emergencyId; return this; }
        public TrafficAlertBuilder locationName(String locationName) { this.locationName = locationName; return this; }
        public TrafficAlertBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
        public TrafficAlertBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
        public TrafficAlertBuilder severity(String severity) { this.severity = severity; return this; }
        public TrafficAlertBuilder greenCorridorActive(Boolean greenCorridorActive) { this.greenCorridorActive = greenCorridorActive; return this; }
        public TrafficAlertBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public TrafficAlert build() {
            return new TrafficAlert(id, emergencyId, locationName, latitude, longitude, severity, greenCorridorActive, createdAt);
        }
    }
}
