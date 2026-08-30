package com.amburoute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private String email;
        private String password;
        private String fullName;
        private String role; // PATIENT, AMBULANCE_DRIVER, HOSPITAL, ADMIN
        private String phone;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class JwtResponse {
        private String token;
        private String tokenType;
        private Long id;
        private String email;
        private String fullName;
        private String role;

        public JwtResponse() {}

        public JwtResponse(String token, String tokenType, Long id, String email, String fullName, String role) {
            this.token = token;
            this.tokenType = tokenType;
            this.id = id;
            this.email = email;
            this.fullName = fullName;
            this.role = role;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public String getTokenType() { return tokenType; }
        public void setTokenType(String tokenType) { this.tokenType = tokenType; }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public static JwtResponseBuilder builder() {
            return new JwtResponseBuilder();
        }

        public static class JwtResponseBuilder {
            private String token;
            private String tokenType;
            private Long id;
            private String email;
            private String fullName;
            private String role;

            public JwtResponseBuilder token(String token) { this.token = token; return this; }
            public JwtResponseBuilder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
            public JwtResponseBuilder id(Long id) { this.id = id; return this; }
            public JwtResponseBuilder email(String email) { this.email = email; return this; }
            public JwtResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
            public JwtResponseBuilder role(String role) { this.role = role; return this; }

            public JwtResponse build() {
                return new JwtResponse(token, tokenType, id, email, fullName, role);
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserDTO {
        private Long id;
        private String email;
        private String fullName;
        private String role;
        private String phone;

        public UserDTO() {}

        public UserDTO(Long id, String email, String fullName, String role, String phone) {
            this.id = id;
            this.email = email;
            this.fullName = fullName;
            this.role = role;
            this.phone = phone;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public static UserDTOBuilder builder() {
            return new UserDTOBuilder();
        }

        public static class UserDTOBuilder {
            private Long id;
            private String email;
            private String fullName;
            private String role;
            private String phone;

            public UserDTOBuilder id(Long id) { this.id = id; return this; }
            public UserDTOBuilder email(String email) { this.email = email; return this; }
            public UserDTOBuilder fullName(String fullName) { this.fullName = fullName; return this; }
            public UserDTOBuilder role(String role) { this.role = role; return this; }
            public UserDTOBuilder phone(String phone) { this.phone = phone; return this; }

            public UserDTO build() {
                return new UserDTO(id, email, fullName, role, phone);
            }
        }
    }
}
