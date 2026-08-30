package com.amburoute.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "hospitals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 20)
    private String emergencyStatus; // ACTIVE, BUSY, FULL

    private Double rating;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HospitalBed> beds;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Hospital() {}

    public Hospital(Long id, String name, Double latitude, Double longitude, String address, String phone, String emergencyStatus, Double rating, List<HospitalBed> beds, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.phone = phone;
        this.emergencyStatus = emergencyStatus;
        this.rating = rating;
        this.beds = beds;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmergencyStatus() { return emergencyStatus; }
    public void setEmergencyStatus(String emergencyStatus) { this.emergencyStatus = emergencyStatus; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public List<HospitalBed> getBeds() { return beds; }
    public void setBeds(List<HospitalBed> beds) { this.beds = beds; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static HospitalBuilder builder() { return new HospitalBuilder(); }

    public static class HospitalBuilder {
        private Long id;
        private String name;
        private Double latitude;
        private Double longitude;
        private String address;
        private String phone;
        private String emergencyStatus;
        private Double rating;
        private List<HospitalBed> beds;
        private LocalDateTime createdAt;

        public HospitalBuilder id(Long id) { this.id = id; return this; }
        public HospitalBuilder name(String name) { this.name = name; return this; }
        public HospitalBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
        public HospitalBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
        public HospitalBuilder address(String address) { this.address = address; return this; }
        public HospitalBuilder phone(String phone) { this.phone = phone; return this; }
        public HospitalBuilder emergencyStatus(String emergencyStatus) { this.emergencyStatus = emergencyStatus; return this; }
        public HospitalBuilder rating(Double rating) { this.rating = rating; return this; }
        public HospitalBuilder beds(List<HospitalBed> beds) { this.beds = beds; return this; }
        public HospitalBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Hospital build() {
            return new Hospital(id, name, latitude, longitude, address, phone, emergencyStatus, rating, beds, createdAt);
        }
    }
}
