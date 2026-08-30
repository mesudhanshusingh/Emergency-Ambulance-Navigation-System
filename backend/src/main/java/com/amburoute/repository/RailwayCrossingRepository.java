package com.amburoute.repository;

import com.amburoute.entity.RailwayCrossing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RailwayCrossingRepository extends JpaRepository<RailwayCrossing, Long> {
}
