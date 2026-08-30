package com.amburoute.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "railway_crossings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RailwayCrossing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String crossingName;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(length = 30)
    private String status; // OPEN, CLOSING, CLOSED

    @Column(length = 20)
    private String defaultRisk;

    @OneToMany(mappedBy = "crossing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TrainSchedule> schedules;

    public RailwayCrossing() {}

    public RailwayCrossing(Long id, String crossingName, Double latitude, Double longitude, String status, String defaultRisk, List<TrainSchedule> schedules) {
        this.id = id;
        this.crossingName = crossingName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.defaultRisk = defaultRisk;
        this.schedules = schedules;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCrossingName() { return crossingName; }
    public void setCrossingName(String crossingName) { this.crossingName = crossingName; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDefaultRisk() { return defaultRisk; }
    public void setDefaultRisk(String defaultRisk) { this.defaultRisk = defaultRisk; }

    public List<TrainSchedule> getSchedules() { return schedules; }
    public void setSchedules(List<TrainSchedule> schedules) { this.schedules = schedules; }

    public static RailwayCrossingBuilder builder() { return new RailwayCrossingBuilder(); }

    public static class RailwayCrossingBuilder {
        private Long id;
        private String crossingName;
        private Double latitude;
        private Double longitude;
        private String status;
        private String defaultRisk;
        private List<TrainSchedule> schedules;

        public RailwayCrossingBuilder id(Long id) { this.id = id; return this; }
        public RailwayCrossingBuilder crossingName(String crossingName) { this.crossingName = crossingName; return this; }
        public RailwayCrossingBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
        public RailwayCrossingBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
        public RailwayCrossingBuilder status(String status) { this.status = status; return this; }
        public RailwayCrossingBuilder defaultRisk(String defaultRisk) { this.defaultRisk = defaultRisk; return this; }
        public RailwayCrossingBuilder schedules(List<TrainSchedule> schedules) { this.schedules = schedules; return this; }

        public RailwayCrossing build() {
            return new RailwayCrossing(id, crossingName, latitude, longitude, status, defaultRisk, schedules);
        }
    }
}
