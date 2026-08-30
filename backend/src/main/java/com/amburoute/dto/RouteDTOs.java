package com.amburoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RouteDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RouteOptionDTO {
        private Long id;
        private String routeName; // FASTEST, SAFE, ALTERNATE
        private Double distanceKm;
        private Integer etaMinutes;
        private String riskLevel; // LOW, MODERATE, HIGH
        private String trafficDensity;
        private Boolean includesRailway;
        private Boolean activeSelected;
        private String waypoints;

        public RouteOptionDTO() {}

        public RouteOptionDTO(Long id, String routeName, Double distanceKm, Integer etaMinutes, String riskLevel, String trafficDensity, Boolean includesRailway, Boolean activeSelected, String waypoints) {
            this.id = id;
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

        public static RouteOptionDTOBuilder builder() { return new RouteOptionDTOBuilder(); }

        public static class RouteOptionDTOBuilder {
            private Long id;
            private String routeName;
            private Double distanceKm;
            private Integer etaMinutes;
            private String riskLevel;
            private String trafficDensity;
            private Boolean includesRailway;
            private Boolean activeSelected;
            private String waypoints;

            public RouteOptionDTOBuilder id(Long id) { this.id = id; return this; }
            public RouteOptionDTOBuilder routeName(String name) { this.routeName = name; return this; }
            public RouteOptionDTOBuilder distanceKm(Double dist) { this.distanceKm = dist; return this; }
            public RouteOptionDTOBuilder etaMinutes(Integer eta) { this.etaMinutes = eta; return this; }
            public RouteOptionDTOBuilder riskLevel(String risk) { this.riskLevel = risk; return this; }
            public RouteOptionDTOBuilder trafficDensity(String density) { this.trafficDensity = density; return this; }
            public RouteOptionDTOBuilder includesRailway(Boolean inc) { this.includesRailway = inc; return this; }
            public RouteOptionDTOBuilder activeSelected(Boolean active) { this.activeSelected = active; return this; }
            public RouteOptionDTOBuilder waypoints(String waypoints) { this.waypoints = waypoints; return this; }

            public RouteOptionDTO build() {
                return new RouteOptionDTO(id, routeName, distanceKm, etaMinutes, riskLevel, trafficDensity, includesRailway, activeSelected, waypoints);
            }
        }
    }
}
