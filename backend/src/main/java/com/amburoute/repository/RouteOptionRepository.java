package com.amburoute.repository;

import com.amburoute.entity.RouteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RouteOptionRepository extends JpaRepository<RouteOption, Long> {
    List<RouteOption> findByEmergencyId(Long emergencyId);
}
