package com.amburoute.repository;

import com.amburoute.entity.HospitalBed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalBedRepository extends JpaRepository<HospitalBed, Long> {
    List<HospitalBed> findByHospitalId(Long hospitalId);
    Optional<HospitalBed> findByHospitalIdAndBedType(Long hospitalId, String bedType);
}
