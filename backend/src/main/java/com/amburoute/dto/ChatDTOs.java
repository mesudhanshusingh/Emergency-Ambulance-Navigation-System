package com.amburoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ChatDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatMessageRequest {
        private String message;
        private String contextEmergencyType;

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public String getContextEmergencyType() { return contextEmergencyType; }
        public void setContextEmergencyType(String contextEmergencyType) { this.contextEmergencyType = contextEmergencyType; }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChatMessageResponse {
        private String reply;
        private String detectedEmergencyType;
        private String suggestedCriticality;
        private Boolean recommendEmergencyActivation;
        private String medicalDisclaimer;

        public ChatMessageResponse() {}

        public ChatMessageResponse(String reply, String detectedEmergencyType, String suggestedCriticality, Boolean recommendEmergencyActivation, String medicalDisclaimer) {
            this.reply = reply;
            this.detectedEmergencyType = detectedEmergencyType;
            this.suggestedCriticality = suggestedCriticality;
            this.recommendEmergencyActivation = recommendEmergencyActivation;
            this.medicalDisclaimer = medicalDisclaimer;
        }

        public String getReply() { return reply; }
        public void setReply(String reply) { this.reply = reply; }

        public String getDetectedEmergencyType() { return detectedEmergencyType; }
        public void setDetectedEmergencyType(String detectedEmergencyType) { this.detectedEmergencyType = detectedEmergencyType; }

        public String getSuggestedCriticality() { return suggestedCriticality; }
        public void setSuggestedCriticality(String suggestedCriticality) { this.suggestedCriticality = suggestedCriticality; }

        public Boolean getRecommendEmergencyActivation() { return recommendEmergencyActivation; }
        public void setRecommendEmergencyActivation(Boolean recommendEmergencyActivation) { this.recommendEmergencyActivation = recommendEmergencyActivation; }

        public String getMedicalDisclaimer() { return medicalDisclaimer; }
        public void setMedicalDisclaimer(String medicalDisclaimer) { this.medicalDisclaimer = medicalDisclaimer; }

        public static ChatMessageResponseBuilder builder() {
            return new ChatMessageResponseBuilder();
        }

        public static class ChatMessageResponseBuilder {
            private String reply;
            private String detectedEmergencyType;
            private String suggestedCriticality;
            private Boolean recommendEmergencyActivation;
            private String medicalDisclaimer;

            public ChatMessageResponseBuilder reply(String reply) { this.reply = reply; return this; }
            public ChatMessageResponseBuilder detectedEmergencyType(String type) { this.detectedEmergencyType = type; return this; }
            public ChatMessageResponseBuilder suggestedCriticality(String crit) { this.suggestedCriticality = crit; return this; }
            public ChatMessageResponseBuilder recommendEmergencyActivation(Boolean rec) { this.recommendEmergencyActivation = rec; return this; }
            public ChatMessageResponseBuilder medicalDisclaimer(String disc) { this.medicalDisclaimer = disc; return this; }

            public ChatMessageResponse build() {
                return new ChatMessageResponse(reply, detectedEmergencyType, suggestedCriticality, recommendEmergencyActivation, medicalDisclaimer);
            }
        }
    }
}
