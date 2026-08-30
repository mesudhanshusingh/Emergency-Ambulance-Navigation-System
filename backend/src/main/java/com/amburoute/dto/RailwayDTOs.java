package com.amburoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public class RailwayDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RailwayCrossingDTO {
        private Long id;
        private String crossingName;
        private Double latitude;
        private Double longitude;
        private String status;
        private String defaultRisk;
        private List<TrainScheduleDTO> schedules;

        public RailwayCrossingDTO() {}

        public RailwayCrossingDTO(Long id, String crossingName, Double latitude, Double longitude, String status, String defaultRisk, List<TrainScheduleDTO> schedules) {
            this.id = id;
            this.crossingName = crossingName;
            this.latitude = latitude;
            this.longitude = longitude;
            this.status = status;
            this.defaultRisk = defaultRisk;
            this.schedules = schedules;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getCrossingName() { return crossingName; }
        public void setCrossingName(String crossingName) { this.crossingName = crossingName; }

        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }

        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getDefaultRisk() { return defaultRisk; }
        public void setDefaultRisk(String defaultRisk) { this.defaultRisk = defaultRisk; }

        public List<TrainScheduleDTO> getSchedules() { return schedules; }
        public void setSchedules(List<TrainScheduleDTO> schedules) { this.schedules = schedules; }

        public static RailwayCrossingDTOBuilder builder() { return new RailwayCrossingDTOBuilder(); }

        public static class RailwayCrossingDTOBuilder {
            private Long id;
            private String crossingName;
            private Double latitude;
            private Double longitude;
            private String status;
            private String defaultRisk;
            private List<TrainScheduleDTO> schedules;

            public RailwayCrossingDTOBuilder id(Long id) { this.id = id; return this; }
            public RailwayCrossingDTOBuilder crossingName(String name) { this.crossingName = name; return this; }
            public RailwayCrossingDTOBuilder latitude(Double lat) { this.latitude = lat; return this; }
            public RailwayCrossingDTOBuilder longitude(Double lng) { this.longitude = lng; return this; }
            public RailwayCrossingDTOBuilder status(String status) { this.status = status; return this; }
            public RailwayCrossingDTOBuilder defaultRisk(String risk) { this.defaultRisk = risk; return this; }
            public RailwayCrossingDTOBuilder schedules(List<TrainScheduleDTO> scheds) { this.schedules = scheds; return this; }

            public RailwayCrossingDTO build() {
                return new RailwayCrossingDTO(id, crossingName, latitude, longitude, status, defaultRisk, schedules);
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TrainScheduleDTO {
        private Long id;
        private String trainNumber;
        private Integer expectedArrivalMins;
        private Integer gateClosureMins;
        private String status;

        public TrainScheduleDTO() {}

        public TrainScheduleDTO(Long id, String trainNumber, Integer expectedArrivalMins, Integer gateClosureMins, String status) {
            this.id = id;
            this.trainNumber = trainNumber;
            this.expectedArrivalMins = expectedArrivalMins;
            this.gateClosureMins = gateClosureMins;
            this.status = status;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTrainNumber() { return trainNumber; }
        public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

        public Integer getExpectedArrivalMins() { return expectedArrivalMins; }
        public void setExpectedArrivalMins(Integer expectedArrivalMins) { this.expectedArrivalMins = expectedArrivalMins; }

        public Integer getGateClosureMins() { return gateClosureMins; }
        public void setGateClosureMins(Integer gateClosureMins) { this.gateClosureMins = gateClosureMins; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public static TrainScheduleDTOBuilder builder() { return new TrainScheduleDTOBuilder(); }

        public static class TrainScheduleDTOBuilder {
            private Long id;
            private String trainNumber;
            private Integer expectedArrivalMins;
            private Integer gateClosureMins;
            private String status;

            public TrainScheduleDTOBuilder id(Long id) { this.id = id; return this; }
            public TrainScheduleDTOBuilder trainNumber(String num) { this.trainNumber = num; return this; }
            public TrainScheduleDTOBuilder expectedArrivalMins(Integer arr) { this.expectedArrivalMins = arr; return this; }
            public TrainScheduleDTOBuilder gateClosureMins(Integer cls) { this.gateClosureMins = cls; return this; }
            public TrainScheduleDTOBuilder status(String status) { this.status = status; return this; }

            public TrainScheduleDTO build() {
                return new TrainScheduleDTO(id, trainNumber, expectedArrivalMins, gateClosureMins, status);
            }
        }
    }
}
