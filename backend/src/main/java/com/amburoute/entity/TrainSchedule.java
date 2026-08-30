package com.amburoute.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "train_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crossing_id", nullable = false)
    @JsonIgnore
    private RailwayCrossing crossing;

    @Column(nullable = false, length = 50)
    private String trainNumber;

    @Column(nullable = false)
    private Integer expectedArrivalMins;

    @Column(nullable = false)
    private Integer gateClosureMins;

    @Column(length = 30)
    private String status; // APPROACHING, PASSED, SCHEDULED

    public TrainSchedule() {}

    public TrainSchedule(Long id, RailwayCrossing crossing, String trainNumber, Integer expectedArrivalMins, Integer gateClosureMins, String status) {
        this.id = id;
        this.crossing = crossing;
        this.trainNumber = trainNumber;
        this.expectedArrivalMins = expectedArrivalMins;
        this.gateClosureMins = gateClosureMins;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public RailwayCrossing getCrossing() { return crossing; }
    public void setCrossing(RailwayCrossing crossing) { this.crossing = crossing; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public Integer getExpectedArrivalMins() { return expectedArrivalMins; }
    public void setExpectedArrivalMins(Integer expectedArrivalMins) { this.expectedArrivalMins = expectedArrivalMins; }

    public Integer getGateClosureMins() { return gateClosureMins; }
    public void setGateClosureMins(Integer gateClosureMins) { this.gateClosureMins = gateClosureMins; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public static TrainScheduleBuilder builder() { return new TrainScheduleBuilder(); }

    public static class TrainScheduleBuilder {
        private Long id;
        private RailwayCrossing crossing;
        private String trainNumber;
        private Integer expectedArrivalMins;
        private Integer gateClosureMins;
        private String status;

        public TrainScheduleBuilder id(Long id) { this.id = id; return this; }
        public TrainScheduleBuilder crossing(RailwayCrossing crossing) { this.crossing = crossing; return this; }
        public TrainScheduleBuilder trainNumber(String trainNumber) { this.trainNumber = trainNumber; return this; }
        public TrainScheduleBuilder expectedArrivalMins(Integer expectedArrivalMins) { this.expectedArrivalMins = expectedArrivalMins; return this; }
        public TrainScheduleBuilder gateClosureMins(Integer gateClosureMins) { this.gateClosureMins = gateClosureMins; return this; }
        public TrainScheduleBuilder status(String status) { this.status = status; return this; }

        public TrainSchedule build() {
            return new TrainSchedule(id, crossing, trainNumber, expectedArrivalMins, gateClosureMins, status);
        }
    }
}
