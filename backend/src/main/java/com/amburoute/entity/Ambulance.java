package com.amburoute.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ambulances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ambulance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String vehicleNumber;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    @Column(length = 30)
    private String status; // AVAILABLE, ON_CALL, IN_TRANSIT, MAINTENANCE

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private Double speed;
    private Double heading;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Ambulance() {}

    public Ambulance(Long id, String vehicleNumber, User driver, String status, Double latitude, Double longitude, Double speed, Double heading, LocalDateTime updatedAt) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.driver = driver;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.heading = heading;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public User getDriver() { return driver; }
    public void setDriver(User driver) { this.driver = driver; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getSpeed() { return speed; }
    public void setSpeed(Double speed) { this.speed = speed; }

    public Double getHeading() { return heading; }
    public void setHeading(Double heading) { this.heading = heading; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static AmbulanceBuilder builder() { return new AmbulanceBuilder(); }

    public static class AmbulanceBuilder {
        private Long id;
        private String vehicleNumber;
        private User driver;
        private String status;
        private Double latitude;
        private Double longitude;
        private Double speed;
        private Double heading;
        private LocalDateTime updatedAt;

        public AmbulanceBuilder id(Long id) { this.id = id; return this; }
        public AmbulanceBuilder vehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; return this; }
        public AmbulanceBuilder driver(User driver) { this.driver = driver; return this; }
        public AmbulanceBuilder status(String status) { this.status = status; return this; }
        public AmbulanceBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
        public AmbulanceBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
        public AmbulanceBuilder speed(Double speed) { this.speed = speed; return this; }
        public AmbulanceBuilder heading(Double heading) { this.heading = heading; return this; }
        public AmbulanceBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Ambulance build() {
            return new Ambulance(id, vehicleNumber, driver, status, latitude, longitude, speed, heading, updatedAt);
        }
    }
}
