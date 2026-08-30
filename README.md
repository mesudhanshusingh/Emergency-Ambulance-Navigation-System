# 🚑 AMBUROUTE
### Intelligent Emergency Ambulance Navigation & Response Platform

> **"Every Second Matters."**  
> *A full-stack emergency response system designed to eliminate avoidable ambulance delays caused by traffic congestion, uncoordinated hospital bed availability, and railway crossing gate closures.*

---

## 💡 Real-World Inspiration & Problem Statement

AmbuRoute was born out of a real-life emergency experience where a critical patient transfer was halted at a closed railway crossing due to an oncoming train. In life-threatening scenarios—such as cardiac arrests, severe trauma, or acute respiratory distress—a delay of even 3 to 5 minutes at a railway gate or traffic bottleneck can be fatal.

**AmbuRoute bridges this gap** by creating a unified, real-time coordination platform that integrates:
1. **Patient Emergency Activation** & Instant Ambulance Dispatch.
2. **AI-Driven Hospital Recommendation** based on patient condition, medical specialty, and live ICU bed availability.
3. **Railway Crossing Intelligence**, predicting train arrival timing vs ambulance ETA to automatically calculate safe alternate routes.
4. **Green Corridor Signal Synchronization & Alert Broadcast** to clear upcoming traffic hotspots.
5. **Hospital Pre-Arrival Trauma Alerts** & Bed Reservation Workflow.
6. **Mobile-Responsive Smartphone UI** with 1-step Header Back Navigation and Slide-Out Hamburger Menu (`☰ Menu`).

---

## 🏗️ Architecture & Technology Stack

```
                                  +---------------------------------------+
                                  |         AmbuRoute Web Frontend        |
                                  |  (HTML5, Glassmorphic UI, JS, Leaflet)|
                                  +-------------------+-------------------+
                                                      |
                                       REST APIs / WebSockets Telemetry
                                                      |
                                                      v
                                  +---------------------------------------+
                                  |     Java Spring Boot 3.1 Backend      |
                                  | (Spring Security JWT, JPA, Web REST)  |
                                  +---------+-------------------+---------+
                                            |                   |
                     Fallback / Live HTTP   |                   | Data Persistence
                                            v                   v
                        +-----------------------+     +-------------------+
                        |  Python FastAPI AI    |     |   H2 / MySQL DB   |
                        | (Triage & Route Risk) |     |  (Pre-populated)  |
                        +-----------------------+     +-------------------+
```

- **Frontend**: Vanilla HTML5, Modern CSS3 (Glassmorphic Design, Responsive Mobile Drawer, Emergency Dark/Light UI), JavaScript (ES6+), Leaflet.js Maps, Chart.js Analytics.
- **Backend**: Java 17 / JDK 21 / JDK 26 compatible, Spring Boot 3.1.5, Spring Data JPA, Spring Security (JWT with `AntPathRequestMatcher`), H2 In-Memory Database / MySQL.
- **AI Service**: Python 3.10+, FastAPI, PyDantic, Heuristic Optimization Engine with safe local Java fallback logic in `AiIntegrationService`.
- **Database**: H2 / MySQL Database (Pre-seeded with 5 Hospitals, 10 Ambulances, 4 Railway Crossings, and Train Schedules).

---

## ⚡ Key Features

- **🚨 Prominent Emergency Activation**: 1-click wizard requesting patient condition, location, and severity, triggering instant dispatch and Green Corridor synchronization.
- **🚆 Railway Crossing Intelligence**: Dynamically evaluates the overlap matrix:
  $$\text{Gate Risk} = \text{Ambulance ETA at Crossing} \approx \text{Train ETA at Crossing}$$
  If overlap risk is detected ($\le 3$ min margin), AmbuRoute prompts **"USE ALTERNATE ROUTE"** to bypass closed gates.
- **🏥 AI Hospital Recommendation & Bed Reservation**: Evaluates hospitals by weighted distance, active ICU capacity, and specialty match. Allows instant ICU bed reservations.
- **🟢 Green Corridor Emergency Alerts**: Informs traffic authorities and broadcasts alerts to opted-in commuters along the route.
- **📍 Live Telemetry & Driver HUD**: Interactive Leaflet maps with moving ambulance markers and Heads-Up Display alerts.
- **📱 Responsive Mobile Experience**:
  - **Header Back Button (`⬅️ Back`)**: Placed beside the AmbuRoute logo for quick 1-step backward navigation.
  - **Mobile Hamburger Drawer (`☰ Menu`)**: Responsive side-drawer navigation on smartphones and narrow screens while keeping standard top navigation links on laptop/PC screens.
- **🤖 AI Emergency Assistant Chatbot**: Interactive triage guide with symptom classifier and medical disclaimers.
- **📊 Admin Command Center**: Real-time Chart.js visual analytics for response times, incident volume, and ICU utilization.
- **⚡ 1-Click Automated Demo Engine**: Built-in automated simulation mode for seamless presentation.

---

## 🔑 Demo Credentials

All passwords are set to `password123`:

| Role | Email | Use Case |
| :--- | :--- | :--- |
| **PATIENT** | `patient@amburoute.com` | Emergency activation, hospital search, tracking |
| **AMBULANCE DRIVER** | `driver@amburoute.com` | Driver HUD navigation & GPS telemetry simulation |
| **HOSPITAL ADMIN** | `hospital@amburoute.com` | Hospital ER incoming queue & bed management |
| **SYSTEM ADMIN** | `admin@amburoute.com` | Metropolitan Command Center & Chart.js analytics |

---

## 🚀 How to Run the Project

### 1. Prerequisites
- **Node.js / Web Browser** (For frontend)
- **Java 17 / JDK 21 / JDK 26 & Maven** (For Spring Boot backend)
- **Python 3.10+** (For FastAPI AI service)

---

### 2. Running the Spring Boot Backend

```bash
cd backend

# Compile backend classes
mvn clean compile

# Run Spring Boot backend application
mvn spring-boot:run
```

- **Backend Base URL**: `http://localhost:8080/api`
- **H2 Console**: `http://localhost:8080/h2-console`  
  - *JDBC URL*: `jdbc:h2:mem:amburoute_db`
  - *User*: `sa`
  - *Password*: *(leave empty)*

*Note: The backend automatically pre-populates seed data for hospitals, ambulances, railway crossings, and users from `schema.sql` and `data.sql` upon startup.*

---

### 3. Running the Frontend

The frontend requires no build steps. You can open `frontend/index.html` directly in your browser or run a local HTTP server:

```bash
# Option A: Open directly in browser
double-click frontend/index.html

# Option B: Run via Python HTTP server
cd frontend
python -m http.server 3000
```
Access at: `http://localhost:3000` (or open `index.html` directly in any web browser).

---

### 4. Running the Python AI Service (Optional)

```bash
cd ai-service
pip install -r requirements.txt
python run.py
```
- **FastAPI AI Base URL**: `http://localhost:8000`  
- **Swagger API Docs**: `http://localhost:8000/docs`

*Note: If the Python AI service is offline, the Spring Boot backend automatically falls back to its built-in heuristic recommendation engine without interrupting user workflow.*

---

## 🛠️ Real APIs vs. Simulation & Fallbacks

1. **Railway Data**: Uses real-time simulation schedules (`train_schedules` & risk calculation engine).
2. **Traffic Alerts / Green Corridor**: Uses opted-in user push alerts & traffic control dashboard notifications.
3. **GPS Telemetry**: Offers interactive simulation movement along polyline waypoints (`simMoveAmbulance()`).
4. **AI Service Fallback**: If the Python FastAPI service is offline, Spring Boot executes robust local heuristic fallback scoring.

---

## 📄 License
This project is created for emergency response research and college demonstration purposes. Built for faster emergency response.
