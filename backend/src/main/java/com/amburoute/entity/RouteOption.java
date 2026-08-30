package com.amburoute.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "routes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emergency_id", nullable = false)
    @JsonIgnore
    private EmergencyRequest emergency;

    @Column(nullable = false, length = 100)
    private String routeName; // FASTEST, SAFE, ALTERNATE

    @Column(nullable = false)
    private Double distanceKm;

    @Column(nullable = false)
    private Integer etaMinutes;

    @Column(nullable = false, length = 20)
    private String riskLevel; // LOW, MODERATE, HIGH

    @Column(nullable = false, length = 20)
    private String trafficDensity; // LIGHT, MODERATE, HEAVY

    private Boolean includesRailway;
    private Boolean activeSelected;

    @Column(columnDefinition = "TEXT")
    private String waypoints; // JSON coordinates string

    public RouteOption() {}

    public RouteOption(Long id, EmergencyRequest emergency, String routeName, Double distanceKm, Integer etaMinutes, String riskLevel, String trafficDensity, Boolean includesRailway, Boolean activeSelected, String waypoints) {
        this.id = id;
        this.emergency = emergency;
        this.routeName = routeName;
        this.distanceKm = distanceKm;
        this.etaMinutes = etaMinutes;
        this.riskLevel = riskLevel;
        this.trafficDensity = trafficDensity;
        this.includesRailway = includesRailway;
        this.activeSelected = activeSelected;
        this.waypoints = waypoints;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EmergencyRequest getEmergency() { return emergency; }
    public void setEmergency(EmergencyRequest emergency) { this.emergency = emergency; }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Integer getEtaMinutes() { return etaMinutes; }
    public void setEtaMinutes(Integer etaMinutes) { this.etaMinutes = etaMinutes; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public String getTrafficDensity() { return trafficDensity; }
    public void setTrafficDensity(String trafficDensity) { this.trafficDensity = trafficDensity; }

    public Boolean getIncludesRailway() { return includesRailway; }
    public void setIncludesRailway(Boolean includesRailway) { this.includesRailway = includesRailway; }

    public Boolean getActiveSelected() { return activeSelected; }
    public void setActiveSelected(Boolean activeSelected) { this.activeSelected = activeSelected; }

    public String getWaypoints() { return waypoints; }
    public void setWaypoints(String waypoints) { this.waypoints = waypoints; }

    public static RouteOptionBuilder builder() { return new RouteOptionBuilder(); }

    public static class RouteOptionBuilder {
        private Long id;
        private EmergencyRequest emergency;
        private String routeName;
        private Double distanceKm;
        private Integer etaMinutes;
        private String riskLevel;
        private String trafficDensity;
        private Boolean includesRailway;
        private Boolean activeSelected;
        private String waypoints;

        public RouteOptionBuilder id(Long id) { this.id = id; return this; }
        public RouteOptionBuilder emergency(EmergencyRequest emergency) { this.emergency = emergency; return this; }
        public RouteOptionBuilder routeName(String routeName) { this.routeName = routeName; return this; }
        public RouteOptionBuilder distanceKm(Double distanceKm) { this.distanceKm = distanceKm; return this; }
        public RouteOptionBuilder etaMinutes(Integer etaMinutes) { this.etaMinutes = etaMinutes; return this; }
        public RouteOptionBuilder riskLevel(String riskLevel) { this.riskLevel = riskLevel; return this; }
        public RouteOptionBuilder trafficDensity(String trafficDensity) { this.trafficDensity = trafficDensity; return this; }
        public RouteOptionBuilder includesRailway(Boolean includesRailway) { this.includesRailway = includesRailway; return this; }
        public RouteOptionBuilder activeSelected(Boolean activeSelected) { this.activeSelected = activeSelected; return this; }
        public RouteOptionBuilder waypoints(String waypoints) { this.waypoints = waypoints; return this; }

        public RouteOption build() {
            return new RouteOption(id, emergency, routeName, distanceKm, etaMinutes, riskLevel, trafficDensity, includesRailway, activeSelected, waypoints);
        }
    }
}
