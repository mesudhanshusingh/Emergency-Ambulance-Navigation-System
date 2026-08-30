package com.amburoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public class HospitalDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BedInfoDTO {
        private String bedType;
        private Integer totalCapacity;
        private Integer availableCount;

        public BedInfoDTO() {}

        public BedInfoDTO(String bedType, Integer totalCapacity, Integer availableCount) {
            this.bedType = bedType;
            this.totalCapacity = totalCapacity;
            this.availableCount = availableCount;
        }

        public String getBedType() { return bedType; }
        public void setBedType(String bedType) { this.bedType = bedType; }

        public Integer getTotalCapacity() { return totalCapacity; }
        public void setTotalCapacity(Integer totalCapacity) { this.totalCapacity = totalCapacity; }

        public Integer getAvailableCount() { return availableCount; }
        public void setAvailableCount(Integer availableCount) { this.availableCount = availableCount; }

        public static BedInfoDTOBuilder builder() { return new BedInfoDTOBuilder(); }

        public static class BedInfoDTOBuilder {
            private String bedType;
            private Integer totalCapacity;
            private Integer availableCount;

            public BedInfoDTOBuilder bedType(String bedType) { this.bedType = bedType; return this; }
            public BedInfoDTOBuilder totalCapacity(Integer totalCapacity) { this.totalCapacity = totalCapacity; return this; }
            public BedInfoDTOBuilder availableCount(Integer availableCount) { this.availableCount = availableCount; return this; }

            public BedInfoDTO build() { return new BedInfoDTO(bedType, totalCapacity, availableCount); }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HospitalDTO {
        private Long id;
        private String name;
        private Double latitude;
        private Double longitude;
        private String address;
        private String phone;
        private String emergencyStatus;
        private Double rating;
        private Double distanceKm;
        private Integer matchPercentage;
        private String recommendationTier;
        private String explanation;
        private List<BedInfoDTO> beds;

        public HospitalDTO() {}

        public HospitalDTO(Long id, String name, Double latitude, Double longitude, String address, String phone, String emergencyStatus, Double rating, Double distanceKm, Integer matchPercentage, String recommendationTier, String explanation, List<BedInfoDTO> beds) {
            this.id = id;
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = address;
            this.phone = phone;
            this.emergencyStatus = emergencyStatus;
            this.rating = rating;
            this.distanceKm = distanceKm;
            this.matchPercentage = matchPercentage;
            this.recommendationTier = recommendationTier;
            this.explanation = explanation;
            this.beds = beds;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }

        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getEmergencyStatus() { return emergencyStatus; }
        public void setEmergencyStatus(String emergencyStatus) { this.emergencyStatus = emergencyStatus; }

        public Double getRating() { return rating; }
        public void setRating(Double rating) { this.rating = rating; }

        public Double getDistanceKm() { return distanceKm; }
        public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

        public Integer getMatchPercentage() { return matchPercentage; }
        public void setMatchPercentage(Integer matchPercentage) { this.matchPercentage = matchPercentage; }

        public String getRecommendationTier() { return recommendationTier; }
        public void setRecommendationTier(String recommendationTier) { this.recommendationTier = recommendationTier; }

        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }

        public List<BedInfoDTO> getBeds() { return beds; }
        public void setBeds(List<BedInfoDTO> beds) { this.beds = beds; }

        public static HospitalDTOBuilder builder() { return new HospitalDTOBuilder(); }

        public static class HospitalDTOBuilder {
            private Long id;
            private String name;
            private Double latitude;
            private Double longitude;
            private String address;
            private String phone;
            private String emergencyStatus;
            private Double rating;
            private Double distanceKm;
            private Integer matchPercentage;
            private String recommendationTier;
            private String explanation;
            private List<BedInfoDTO> beds;

            public HospitalDTOBuilder id(Long id) { this.id = id; return this; }
            public HospitalDTOBuilder name(String name) { this.name = name; return this; }
            public HospitalDTOBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
            public HospitalDTOBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
            public HospitalDTOBuilder address(String address) { this.address = address; return this; }
            public HospitalDTOBuilder phone(String phone) { this.phone = phone; return this; }
            public HospitalDTOBuilder emergencyStatus(String emergencyStatus) { this.emergencyStatus = emergencyStatus; return this; }
            public HospitalDTOBuilder rating(Double rating) { this.rating = rating; return this; }
            public HospitalDTOBuilder distanceKm(Double distanceKm) { this.distanceKm = distanceKm; return this; }
            public HospitalDTOBuilder matchPercentage(Integer matchPercentage) { this.matchPercentage = matchPercentage; return this; }
            public HospitalDTOBuilder recommendationTier(String recommendationTier) { this.recommendationTier = recommendationTier; return this; }
            public HospitalDTOBuilder explanation(String explanation) { this.explanation = explanation; return this; }
            public HospitalDTOBuilder beds(List<BedInfoDTO> beds) { this.beds = beds; return this; }

            public HospitalDTO build() {
                return new HospitalDTO(id, name, latitude, longitude, address, phone, emergencyStatus, rating, distanceKm, matchPercentage, recommendationTier, explanation, beds);
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BedReservationRequest {
        private Long emergencyId;
        private Long hospitalId;
        private String bedType;
        private String patientName;

        public Long getEmergencyId() { return emergencyId; }
        public void setEmergencyId(Long emergencyId) { this.emergencyId = emergencyId; }

        public Long getHospitalId() { return hospitalId; }
        public void setHospitalId(Long hospitalId) { this.hospitalId = hospitalId; }

        public String getBedType() { return bedType; }
        public void setBedType(String bedType) { this.bedType = bedType; }

        public String getPatientName() { return patientName; }
        public void setPatientName(String patientName) { this.patientName = patientName; }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BedReservationResponse {
        private Long reservationId;
        private Long emergencyId;
        private String hospitalName;
        private String bedType;
        private String patientName;
        private String status;
        private String reservedAt;

        public BedReservationResponse() {}

        public BedReservationResponse(Long reservationId, Long emergencyId, String hospitalName, String bedType, String patientName, String status, String reservedAt) {
            this.reservationId = reservationId;
            this.emergencyId = emergencyId;
            this.hospitalName = hospitalName;
            this.bedType = bedType;
            this.patientName = patientName;
            this.status = status;
            this.reservedAt = reservedAt;
        }

        public Long getReservationId() { return reservationId; }
        public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

        public Long getEmergencyId() { return emergencyId; }
        public void setEmergencyId(Long emergencyId) { this.emergencyId = emergencyId; }

        public String getHospitalName() { return hospitalName; }
        public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

        public String getBedType() { return bedType; }
        public void setBedType(String bedType) { this.bedType = bedType; }

        public String getPatientName() { return patientName; }
        public void setPatientName(String patientName) { this.patientName = patientName; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getReservedAt() { return reservedAt; }
        public void setReservedAt(String reservedAt) { this.reservedAt = reservedAt; }

        public static BedReservationResponseBuilder builder() { return new BedReservationResponseBuilder(); }

        public static class BedReservationResponseBuilder {
            private Long reservationId;
            private Long emergencyId;
            private String hospitalName;
            private String bedType;
            private String patientName;
            private String status;
            private String reservedAt;

            public BedReservationResponseBuilder reservationId(Long reservationId) { this.reservationId = reservationId; return this; }
            public BedReservationResponseBuilder emergencyId(Long emergencyId) { this.emergencyId = emergencyId; return this; }
            public BedReservationResponseBuilder hospitalName(String hospitalName) { this.hospitalName = hospitalName; return this; }
            public BedReservationResponseBuilder bedType(String bedType) { this.bedType = bedType; return this; }
            public BedReservationResponseBuilder patientName(String patientName) { this.patientName = patientName; return this; }
            public BedReservationResponseBuilder status(String status) { this.status = status; return this; }
            public BedReservationResponseBuilder reservedAt(String reservedAt) { this.reservedAt = reservedAt; return this; }

            public BedReservationResponse build() {
                return new BedReservationResponse(reservationId, emergencyId, hospitalName, bedType, patientName, status, reservedAt);
            }
        }
    }
}
