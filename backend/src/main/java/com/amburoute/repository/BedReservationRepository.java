package com.amburoute.repository;

import com.amburoute.entity.BedReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BedReservationRepository extends JpaRepository<BedReservation, Long> {
    List<BedReservation> findByHospitalId(Long hospitalId);
    List<BedReservation> findByEmergencyId(Long emergencyId);
}
