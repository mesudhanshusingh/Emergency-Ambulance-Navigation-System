package com.amburoute.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hospital_reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BedReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emergency_id", nullable = false)
    private EmergencyRequest emergency;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @Column(nullable = false, length = 50)
    private String bedType;

    @Column(nullable = false, length = 100)
    private String patientName;

    @Column(length = 30)
    private String status; // PENDING, CONFIRMED, COMPLETED, CANCELLED

    @Column(name = "reserved_at", insertable = false, updatable = false)
    private LocalDateTime reservedAt;

    public BedReservation() {}

    public BedReservation(Long id, EmergencyRequest emergency, Hospital hospital, String bedType, String patientName, String status, LocalDateTime reservedAt) {
        this.id = id;
        this.emergency = emergency;
        this.hospital = hospital;
        this.bedType = bedType;
        this.patientName = patientName;
        this.status = status;
        this.reservedAt = reservedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EmergencyRequest getEmergency() { return emergency; }
    public void setEmergency(EmergencyRequest emergency) { this.emergency = emergency; }

    public Hospital getHospital() { return hospital; }
    public void setHospital(Hospital hospital) { this.hospital = hospital; }

    public String getBedType() { return bedType; }
    public void setBedType(String bedType) { this.bedType = bedType; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getReservedAt() { return reservedAt; }
    public void setReservedAt(LocalDateTime reservedAt) { this.reservedAt = reservedAt; }

    public static BedReservationBuilder builder() { return new BedReservationBuilder(); }

    public static class BedReservationBuilder {
        private Long id;
        private EmergencyRequest emergency;
        private Hospital hospital;
        private String bedType;
        private String patientName;
        private String status;
        private LocalDateTime reservedAt;

        public BedReservationBuilder id(Long id) { this.id = id; return this; }
        public BedReservationBuilder emergency(EmergencyRequest emergency) { this.emergency = emergency; return this; }
        public BedReservationBuilder hospital(Hospital hospital) { this.hospital = hospital; return this; }
        public BedReservationBuilder bedType(String bedType) { this.bedType = bedType; return this; }
        public BedReservationBuilder patientName(String patientName) { this.patientName = patientName; return this; }
        public BedReservationBuilder status(String status) { this.status = status; return this; }
        public BedReservationBuilder reservedAt(LocalDateTime reservedAt) { this.reservedAt = reservedAt; return this; }

        public BedReservation build() {
            return new BedReservation(id, emergency, hospital, bedType, patientName, status, reservedAt);
        }
    }
}
