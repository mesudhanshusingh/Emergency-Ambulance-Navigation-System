package com.amburoute.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "hospital_beds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalBed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    @JsonIgnore
    private Hospital hospital;

    @Column(nullable = false, length = 50)
    private String bedType; // ICU, EMERGENCY, GENERAL, VENTILATOR, SPECIALIST

    @Column(nullable = false)
    private Integer totalCapacity;

    @Column(nullable = false)
    private Integer availableCount;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public HospitalBed() {}

    public HospitalBed(Long id, Hospital hospital, String bedType, Integer totalCapacity, Integer availableCount, LocalDateTime updatedAt) {
        this.id = id;
        this.hospital = hospital;
        this.bedType = bedType;
        this.totalCapacity = totalCapacity;
        this.availableCount = availableCount;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Hospital getHospital() { return hospital; }
    public void setHospital(Hospital hospital) { this.hospital = hospital; }

    public String getBedType() { return bedType; }
    public void setBedType(String bedType) { this.bedType = bedType; }

    public Integer getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(Integer totalCapacity) { this.totalCapacity = totalCapacity; }

    public Integer getAvailableCount() { return availableCount; }
    public void setAvailableCount(Integer availableCount) { this.availableCount = availableCount; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PreUpdate
    @PrePersist
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static HospitalBedBuilder builder() { return new HospitalBedBuilder(); }

    public static class HospitalBedBuilder {
        private Long id;
        private Hospital hospital;
        private String bedType;
        private Integer totalCapacity;
        private Integer availableCount;
        private LocalDateTime updatedAt;

        public HospitalBedBuilder id(Long id) { this.id = id; return this; }
        public HospitalBedBuilder hospital(Hospital hospital) { this.hospital = hospital; return this; }
        public HospitalBedBuilder bedType(String bedType) { this.bedType = bedType; return this; }
        public HospitalBedBuilder totalCapacity(Integer totalCapacity) { this.totalCapacity = totalCapacity; return this; }
        public HospitalBedBuilder availableCount(Integer availableCount) { this.availableCount = availableCount; return this; }
        public HospitalBedBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public HospitalBed build() {
            return new HospitalBed(id, hospital, bedType, totalCapacity, availableCount, updatedAt);
        }
    }
}
