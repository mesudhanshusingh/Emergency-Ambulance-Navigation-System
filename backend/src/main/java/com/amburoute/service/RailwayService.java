package com.amburoute.service;

import com.amburoute.dto.RailwayDTOs;
import com.amburoute.entity.RailwayCrossing;
import com.amburoute.entity.TrainSchedule;
import com.amburoute.repository.RailwayCrossingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RailwayService {

    private final RailwayCrossingRepository railwayCrossingRepository;

    public RailwayService(RailwayCrossingRepository railwayCrossingRepository) {
        this.railwayCrossingRepository = railwayCrossingRepository;
    }

    public List<RailwayDTOs.RailwayCrossingDTO> getAllCrossings() {
        List<RailwayCrossing> crossings = railwayCrossingRepository.findAll();
        List<RailwayDTOs.RailwayCrossingDTO> dtoList = new ArrayList<>();

        for (RailwayCrossing c : crossings) {
            List<RailwayDTOs.TrainScheduleDTO> schedDTOs = new ArrayList<>();
            if (c.getSchedules() != null) {
                for (TrainSchedule s : c.getSchedules()) {
                    schedDTOs.add(RailwayDTOs.TrainScheduleDTO.builder()
                            .id(s.getId())
                            .trainNumber(s.getTrainNumber())
                            .expectedArrivalMins(s.getExpectedArrivalMins())
                            .gateClosureMins(s.getGateClosureMins())
                            .status(s.getStatus())
                            .build());
                }
            }

            dtoList.add(RailwayDTOs.RailwayCrossingDTO.builder()
                    .id(c.getId())
                    .crossingName(c.getCrossingName())
                    .latitude(c.getLatitude())
                    .longitude(c.getLongitude())
                    .status(c.getStatus())
                    .defaultRisk(c.getDefaultRisk())
                    .schedules(schedDTOs)
                    .build());
        }

        return dtoList;
    }
}
