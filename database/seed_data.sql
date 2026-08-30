-- ========================================================
-- AMBUROUTE DEMO SEED DATA
-- Pre-populates Hospitals, Bed Availability, Railway Crossings,
-- Ambulances, and Demo Users
-- ========================================================

-- Demo Users (BCrypt hashes for '12345' and 'password123')
INSERT INTO users (id, email, password, full_name, role, phone) VALUES
(1, 'sudhanshuadmin@login', '$2a$10$8.UnVuG9HHg71W4qB.xK3uJ3K8J/N4R3gZ6V3A3G1T2W3Y4Z5X6Y7', 'Sudhanshu (System Admin)', 'ADMIN', '+919876543210'),
(2, 'patient@amburoute.com', '$2a$10$8.UnVuG9HHg71W4qB.xK3uJ3K8J/N4R3gZ6V3A3G1T2W3Y4Z5X6Y7', 'Anil Sharma', 'PATIENT', '+919876543211'),
(3, 'driver@amburoute.com', '$2a$10$8.UnVuG9HHg71W4qB.xK3uJ3K8J/N4R3gZ6V3A3G1T2W3Y4Z5X6Y7', 'Rajesh Kumar (Ambulance 01)', 'AMBULANCE_DRIVER', '+919876543212'),
(4, 'hospital@amburoute.com', '$2a$10$8.UnVuG9HHg71W4qB.xK3uJ3K8J/N4R3gZ6V3A3G1T2W3Y4Z5X6Y7', 'City Emergency Center Admin', 'HOSPITAL', '+919876543213');

-- Hospitals in Metropolis Area
INSERT INTO hospitals (id, name, latitude, longitude, address, phone, emergency_status, rating) VALUES
(1, 'City Emergency & Cardiac Super Specialty', 12.9785, 77.5990, '77 MG Road, Central District', '+918022114455', 'ACTIVE', 4.9),
(2, 'Apex Trauma Care & ICU Center', 12.9650, 77.6080, '12 Indiranagar 100ft Rd', '+918022115566', 'ACTIVE', 4.7),
(3, 'Metro Healthcare General Hospital', 12.9850, 77.5800, '45 Malleshwaram 18th Cross', '+918022116677', 'ACTIVE', 4.4),
(4, 'St. Jude Heart & Respiratory Institute', 12.9520, 77.5720, '89 Jayanagar 4th Block', '+918022117788', 'ACTIVE', 4.8),
(5, 'Sunrise Community Emergency Hospital', 12.9400, 77.6200, '10 Koramangala 80ft Rd', '+918022118899', 'BUSY', 4.2);

-- Hospital Bed Inventory
INSERT INTO hospital_beds (id, hospital_id, bed_type, total_capacity, available_count) VALUES
(1, 1, 'ICU', 25, 4),
(2, 1, 'EMERGENCY', 40, 8),
(3, 1, 'GENERAL', 100, 15),
(4, 1, 'VENTILATOR', 12, 2),
(5, 1, 'SPECIALIST', 10, 5),

(6, 2, 'ICU', 20, 1),
(7, 2, 'EMERGENCY', 30, 5),
(8, 2, 'GENERAL', 80, 12),
(9, 2, 'VENTILATOR', 8, 1),
(10, 2, 'SPECIALIST', 8, 3),

(11, 3, 'ICU', 15, 0),
(12, 3, 'EMERGENCY', 25, 3),
(13, 3, 'GENERAL', 70, 9),
(14, 3, 'VENTILATOR', 5, 0),
(15, 3, 'SPECIALIST', 6, 2),

(16, 4, 'ICU', 18, 5),
(17, 4, 'EMERGENCY', 35, 7),
(18, 4, 'GENERAL', 90, 22),
(19, 4, 'VENTILATOR', 10, 4),
(20, 4, 'SPECIALIST', 12, 6),

(21, 5, 'ICU', 10, 2),
(22, 5, 'EMERGENCY', 15, 2),
(23, 5, 'GENERAL', 50, 4),
(24, 5, 'VENTILATOR', 4, 1),
(25, 5, 'SPECIALIST', 4, 1);

-- Ambulances
INSERT INTO ambulances (id, vehicle_number, driver_id, status, latitude, longitude, speed, heading) VALUES
(1, 'KA-01-EQ-1001', 3, 'AVAILABLE', 12.9690, 77.5850, 0.0, 90.0),
(2, 'KA-01-EQ-1002', NULL, 'AVAILABLE', 12.9750, 77.6050, 0.0, 180.0),
(3, 'KA-01-EQ-1003', NULL, 'ON_CALL', 12.9600, 77.5900, 45.0, 45.0),
(4, 'KA-01-EQ-1004', NULL, 'AVAILABLE', 12.9810, 77.5920, 0.0, 0.0),
(5, 'KA-01-EQ-1005', NULL, 'AVAILABLE', 12.9500, 77.6100, 0.0, 270.0),
(6, 'KA-01-EQ-1006', NULL, 'AVAILABLE', 12.9450, 77.5800, 0.0, 90.0),
(7, 'KA-01-EQ-1007', NULL, 'AVAILABLE', 12.9880, 77.6150, 0.0, 135.0),
(8, 'KA-01-EQ-1008', NULL, 'AVAILABLE', 12.9350, 77.6000, 0.0, 315.0),
(9, 'KA-01-EQ-1009', NULL, 'AVAILABLE', 12.9900, 77.5750, 0.0, 180.0),
(10, 'KA-01-EQ-1010', NULL, 'AVAILABLE', 12.9550, 77.6300, 0.0, 90.0);

-- Railway Crossings
INSERT INTO railway_crossings (id, crossing_name, latitude, longitude, status, default_risk) VALUES
(1, 'Central Junction LC-42 (MG Road Gate)', 12.9730, 77.5950, 'CLOSING', 'HIGH'),
(2, 'Cantonment Rail Gate LC-19', 12.9820, 77.6010, 'OPEN', 'LOW'),
(3, 'East Yard Railway Gate LC-88', 12.9620, 77.6120, 'CLOSED', 'HIGH'),
(4, 'South Bypass Rail Crossing LC-05', 12.9480, 77.5880, 'OPEN', 'LOW');

-- Train Schedules
INSERT INTO train_schedules (id, crossing_id, train_number, expected_arrival_mins, gate_closure_mins, status) VALUES
(1, 1, 'EXP-12677 Intercity Express', 4, 10, 'APPROACHING'),
(2, 2, 'FRT-9920 Cargo Goods Train', 25, 12, 'SCHEDULED'),
(3, 3, 'PASS-56201 Passenger Special', 1, 8, 'APPROACHING'),
(4, 4, 'EXP-12608 Lalbagh Express', 40, 10, 'SCHEDULED');
