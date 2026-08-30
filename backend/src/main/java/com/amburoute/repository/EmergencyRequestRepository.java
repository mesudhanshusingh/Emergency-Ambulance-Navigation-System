package com.amburoute.repository;

import com.amburoute.entity.EmergencyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmergencyRequestRepository extends JpaRepository<EmergencyRequest, Long> {
    List<EmergencyRequest> findByStatusIn(List<String> statuses);
    List<EmergencyRequest> findByAssignedHospitalId(Long hospitalId);
}
