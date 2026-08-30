package com.amburoute.repository;

import com.amburoute.entity.TrafficAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrafficAlertRepository extends JpaRepository<TrafficAlert, Long> {
    List<TrafficAlert> findByEmergencyId(Long emergencyId);
}
