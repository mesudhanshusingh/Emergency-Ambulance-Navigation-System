INSERT INTO users (id, email, password, full_name, role, phone) VALUES
(1, 'sudhanshuadmin@login', '$2a$10$8.UnVuG9HHg71W4qB.xK3uJ3K8J/N4R3gZ6V3A3G1T2W3Y4Z5X6Y7', 'Sudhanshu (System Admin)', 'ADMIN', '+919876543210'),
(2, 'patient@amburoute.com', '$2a$10$8.UnVuG9HHg71W4qB.xK3uJ3K8J/N4R3gZ6V3A3G1T2W3Y4Z5X6Y7', 'Anil Sharma', 'PATIENT', '+919876543211'),
(3, 'driver@amburoute.com', '$2a$10$8.UnVuG9HHg71W4qB.xK3uJ3K8J/N4R3gZ6V3A3G1T2W3Y4Z5X6Y7', 'Rajesh Kumar (Ambulance 01)', 'AMBULANCE_DRIVER', '+919876543212'),
(4, 'hospital@amburoute.com', '$2a$10$8.UnVuG9HHg71W4qB.xK3uJ3K8J/N4R3gZ6V3A3G1T2W3Y4Z5X6Y7', 'AIIMS Emergency Admin', 'HOSPITAL', '+919876543213');

INSERT INTO hospitals (id, name, latitude, longitude, address, phone, emergency_status, rating) VALUES
(1, 'AIIMS Emergency & Trauma Center (New Delhi)', 28.5672, 77.2100, 'Sri Aurobindo Marg, Ansari Nagar, New Delhi', '+911126588500', 'ACTIVE', 4.9),
(2, 'Fatima Hospital (Gorakhpur)', 26.7820, 83.3850, 'Medical College Road, Basharatpur, Gorakhpur, UP', '+915512501444', 'ACTIVE', 4.8),
(3, 'IGIMS Super Specialty & Trauma Center (Patna)', 25.6120, 85.0880, 'Sheikhpura, Bailey Road, Patna, Bihar', '+916122297631', 'ACTIVE', 4.7),
(4, 'PMCH Emergency & Acute Trauma Center (Patna)', 25.6200, 85.1550, 'Ashok Rajpath, Mahendru, Patna, Bihar', '+916122300080', 'ACTIVE', 4.6),
(5, 'Apollo Emergency & Heart Institute', 28.5355, 77.2850, 'Sarita Vihar, Delhi Mathura Road, New Delhi', '+911126925858', 'ACTIVE', 4.9),
(6, 'Fortis Escorts Heart & Trauma Center', 28.5600, 77.2720, 'Okhla Road, Opp Holy Family Hospital, New Delhi', '+911147135000', 'ACTIVE', 4.8),
(7, 'Max Super Specialty Trauma Care', 28.5280, 77.2120, '1 Press Enclave Road, Saket, New Delhi', '+911126515050', 'ACTIVE', 4.7),
(8, 'Medanta The Medicity Emergency & Critical Care', 28.4380, 77.0420, 'CH Baktawar Singh Road, Sector 38, Gurugram, Haryana', '+911244141414', 'ACTIVE', 4.9),
(9, 'Manipal Emergency & Cardiac Institute', 12.9580, 77.6480, '98 HAL Old Airport Road, Kodihalli, Bengaluru', '+918025024444', 'ACTIVE', 4.6),
(10, 'Sir Ganga Ram Emergency Hospital', 28.6380, 77.1890, 'Sir Ganga Ram Hospital Marg, Rajinder Nagar, New Delhi', '+911125750000', 'ACTIVE', 4.8);

INSERT INTO hospital_beds (id, hospital_id, bed_type, total_capacity, available_count) VALUES
-- 1. AIIMS Delhi
(1, 1, 'ICU', 40, 12),
(2, 1, 'EMERGENCY', 60, 18),
(3, 1, 'GENERAL', 200, 45),
(4, 1, 'VENTILATOR', 20, 8),
(5, 1, 'SPECIALIST', 30, 15),

-- 2. Fatima Hospital Gorakhpur
(6, 2, 'ICU', 20, 6),
(7, 2, 'EMERGENCY', 35, 10),
(8, 2, 'GENERAL', 100, 25),
(9, 2, 'VENTILATOR', 10, 4),
(10, 2, 'SPECIALIST', 15, 5),

-- 3. IGIMS Patna
(11, 3, 'ICU', 25, 8),
(12, 3, 'EMERGENCY', 40, 12),
(13, 3, 'GENERAL', 120, 30),
(14, 3, 'VENTILATOR', 15, 5),
(15, 3, 'SPECIALIST', 20, 7),

-- 4. PMCH Patna
(16, 4, 'ICU', 30, 5),
(17, 4, 'EMERGENCY', 50, 15),
(18, 4, 'GENERAL', 180, 50),
(19, 4, 'VENTILATOR', 12, 3),
(20, 4, 'SPECIALIST', 25, 8),

-- 5. Apollo Emergency
(21, 5, 'ICU', 30, 9),
(22, 5, 'EMERGENCY', 45, 14),
(23, 5, 'GENERAL', 130, 35),
(24, 5, 'VENTILATOR', 15, 6),
(25, 5, 'SPECIALIST', 25, 10),

-- 6. Fortis Escorts
(26, 6, 'ICU', 22, 7),
(27, 6, 'EMERGENCY', 35, 11),
(28, 6, 'GENERAL', 90, 28),
(29, 6, 'VENTILATOR', 12, 4),
(30, 6, 'SPECIALIST', 18, 6),

-- 7. Max Saket
(31, 7, 'ICU', 28, 10),
(32, 7, 'EMERGENCY', 40, 16),
(33, 7, 'GENERAL', 110, 40),
(34, 7, 'VENTILATOR', 14, 5),
(35, 7, 'SPECIALIST', 22, 9),

-- 8. Medanta Gurugram
(36, 8, 'ICU', 35, 11),
(37, 8, 'EMERGENCY', 50, 20),
(38, 8, 'GENERAL', 150, 60),
(39, 8, 'VENTILATOR', 18, 7),
(40, 8, 'SPECIALIST', 30, 12),

-- 9. Manipal Bengaluru
(41, 9, 'ICU', 20, 6),
(42, 9, 'EMERGENCY', 30, 9),
(43, 9, 'GENERAL', 85, 22),
(44, 9, 'VENTILATOR', 10, 3),
(45, 9, 'SPECIALIST', 15, 5),

-- 10. Sir Ganga Ram Delhi
(46, 10, 'ICU', 25, 8),
(47, 10, 'EMERGENCY', 38, 13),
(48, 10, 'GENERAL', 100, 32),
(49, 10, 'VENTILATOR', 12, 4),
(50, 10, 'SPECIALIST', 20, 7);

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

INSERT INTO railway_crossings (id, crossing_name, latitude, longitude, status, default_risk) VALUES
(1, 'Central Junction LC-42 (MG Road Gate)', 12.9730, 77.5950, 'CLOSING', 'HIGH'),
(2, 'Cantonment Rail Gate LC-19', 12.9820, 77.6010, 'OPEN', 'LOW'),
(3, 'East Yard Railway Gate LC-88', 12.9620, 77.6120, 'CLOSED', 'HIGH'),
(4, 'South Bypass Rail Crossing LC-05', 12.9480, 77.5880, 'OPEN', 'LOW');

INSERT INTO train_schedules (id, crossing_id, train_number, expected_arrival_mins, gate_closure_mins, status) VALUES
(1, 1, 'EXP-12677 Intercity Express', 4, 10, 'APPROACHING'),
(2, 2, 'FRT-9920 Cargo Goods Train', 25, 12, 'SCHEDULED'),
(3, 3, 'PASS-56201 Passenger Special', 1, 8, 'APPROACHING'),
(4, 4, 'EXP-12608 Lalbagh Express', 40, 10, 'SCHEDULED');
