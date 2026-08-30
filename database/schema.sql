-- ========================================================
-- AMBUROUTE DATABASE SCHEMA (MySQL 8.0+ & H2 Compatible)
-- Intelligent Emergency Ambulance Navigation & Response Platform
-- ========================================================

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL, -- PATIENT, AMBULANCE_DRIVER, HOSPITAL, ADMIN
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS hospitals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    emergency_status VARCHAR(20) DEFAULT 'ACTIVE', -- ACTIVE, BUSY, FULL
    rating DOUBLE DEFAULT 4.5,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS hospital_beds (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hospital_id BIGINT NOT NULL,
    bed_type VARCHAR(50) NOT NULL, -- ICU, EMERGENCY, GENERAL, VENTILATOR, SPECIALIST
    total_capacity INT NOT NULL,
    available_count INT NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ambulances (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_number VARCHAR(50) NOT NULL UNIQUE,
    driver_id BIGINT,
    status VARCHAR(30) DEFAULT 'AVAILABLE', -- AVAILABLE, ON_CALL, IN_TRANSIT, MAINTENANCE
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    speed DOUBLE DEFAULT 0.0,
    heading DOUBLE DEFAULT 0.0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (driver_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS emergency_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100) NOT NULL,
    patient_age INT,
    condition_desc TEXT,
    emergency_type VARCHAR(50) NOT NULL, -- CARDIAC, ACCIDENT_TRAUMA, RESPIRATORY, STROKE, PREGNANCY, OTHER
    criticality VARCHAR(20) NOT NULL, -- CRITICAL, HIGH, MODERATE
    status VARCHAR(30) DEFAULT 'DISPATCHED', -- ACTIVATED, DISPATCHED, EN_ROUTE, ARRIVED, COMPLETED, CANCELLED
    source_lat DOUBLE NOT NULL,
    source_lng DOUBLE NOT NULL,
    assigned_hospital_id BIGINT,
    assigned_ambulance_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (assigned_hospital_id) REFERENCES hospitals(id) ON DELETE SET NULL,
    FOREIGN KEY (assigned_ambulance_id) REFERENCES ambulances(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS routes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    emergency_id BIGINT NOT NULL,
    route_name VARCHAR(100) NOT NULL, -- FASTEST, SAFE, ALTERNATE
    distance_km DOUBLE NOT NULL,
    eta_minutes INT NOT NULL,
    risk_level VARCHAR(20) NOT NULL, -- LOW, MODERATE, HIGH
    traffic_density VARCHAR(20) NOT NULL, -- LIGHT, MODERATE, HEAVY
    includes_railway BOOLEAN DEFAULT FALSE,
    active_selected BOOLEAN DEFAULT FALSE,
    waypoints TEXT, -- JSON coordinates string
    FOREIGN KEY (emergency_id) REFERENCES emergency_requests(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS railway_crossings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crossing_name VARCHAR(150) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    status VARCHAR(30) DEFAULT 'OPEN', -- OPEN, CLOSING, CLOSED
    default_risk VARCHAR(20) DEFAULT 'LOW'
);

CREATE TABLE IF NOT EXISTS train_schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crossing_id BIGINT NOT NULL,
    train_number VARCHAR(50) NOT NULL,
    expected_arrival_mins INT NOT NULL, -- Mins from current time
    gate_closure_mins INT NOT NULL,
    status VARCHAR(30) DEFAULT 'APPROACHING', -- APPROACHING, PASSED, SCHEDULED
    FOREIGN KEY (crossing_id) REFERENCES railway_crossings(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS hospital_reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    emergency_id BIGINT NOT NULL,
    hospital_id BIGINT NOT NULL,
    bed_type VARCHAR(50) NOT NULL,
    patient_name VARCHAR(100) NOT NULL,
    status VARCHAR(30) DEFAULT 'CONFIRMED', -- PENDING, CONFIRMED, COMPLETED, CANCELLED
    reserved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emergency_id) REFERENCES emergency_requests(id) ON DELETE CASCADE,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS traffic_alerts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    emergency_id BIGINT,
    location_name VARCHAR(150) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    severity VARCHAR(20) NOT NULL, -- HIGH, MEDIUM, LOW
    green_corridor_active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
