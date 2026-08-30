/* ========================================================
   AMBUROUTE PLATFORM - FRONTEND APPLICATION JS
   ======================================================== */

const API_BASE = "http://localhost:8080/api";
const AI_BASE = "http://localhost:8000";

// Global State
let currentUser = null;
let mainMap = null;
let driverMap = null;
let ambulanceMarker = null;
let currentRoutePolyline = null;
let currentActiveEmergency = null;
let currentHospitals = [];
let chartTrends = null;
let chartIcu = null;

// GPS Navigation Animation Timer
let gpsNavInterval = null;
let isGpsNavigating = false;
let navStepIndex = 0;

// Coordinates setup (Metro area centered around 12.9716, 77.5946)
const MOCK_PATIENT_COORDS = [12.9690, 77.5850];
const MOCK_HOSPITAL_COORDS = [12.9785, 77.5990];
const MOCK_RAILWAY_COORDS = [12.9730, 77.5950];

// Simulated Route Coordinates
const ROUTE_A_DIRECT = [
    [12.9690, 77.5850],
    [12.9730, 77.5950], // Railway Gate LC-42
    [12.9785, 77.5990]  // Hospital
];

const ROUTE_B_SAFE_ALTERNATE = [
    [12.9690, 77.5850],
    [12.9600, 77.5900], // Bypass Flyover
    [12.9700, 77.6000],
    [12.9785, 77.5990]  // Hospital
];

// Turn-by-Turn GPS Maneuvers Data
const GPS_MANEUVERS = [
    {
        icon: "⬆",
        distance: "In 400 meters",
        instruction: "Head North on MG Road Sector 4",
        subtext: "Green Corridor Signals Synchronized",
        speed: 48,
        distRem: "4.8 km",
        eta: "7 mins",
        alertTitle: "Route B via South Flyover Bypass",
        alertDesc: "✅ Safely bypassed closed Railway Gate LC-42"
    },
    {
        icon: "↱",
        distance: "In 200 meters",
        instruction: "Turn Right onto South Flyover Bypass",
        subtext: "Bypasses closed Railway Gate LC-42",
        speed: 58,
        distRem: "3.2 km",
        eta: "5 mins",
        alertTitle: "🚨 GREEN CORRIDOR ACTIVE - FLYOVER CLEAR",
        alertDesc: "Traffic bottleneck cleared by AmbuRoute beacon"
    },
    {
        icon: "↰",
        distance: "In 300 meters",
        instruction: "Turn Left onto Hospital Expressway",
        subtext: "Clear Emergency Corridor Ahead",
        speed: 64,
        distRem: "1.4 km",
        eta: "2 mins",
        alertTitle: "🏥 APPROACHING HOSPITAL TRAUMA BAY",
        alertDesc: "Hospital ER prepped, ICU Bed #4 Ready"
    },
    {
        icon: "🏥",
        distance: "Destination Arrived",
        instruction: "Arrived at City Emergency Center",
        subtext: "Transfer Patient to Trauma Bay #1",
        speed: 0,
        distRem: "0.0 km",
        eta: "ARRIVED",
        alertTitle: "✅ PATIENT SAFELY ARRIVED AT HOSPITAL",
        alertDesc: "Emergency transfer complete. Total time saved: 6 mins."
    }
];

// Sample Mock Hospitals Cache (10 Major Indian Hospitals)
const MOCK_HOSPITALS_DATA = [
    {
        id: 1,
        name: "AIIMS Emergency & Trauma Center (New Delhi)",
        distanceKm: 3.2,
        matchPercentage: 98,
        recommendationTier: "HIGHLY_RECOMMENDED",
        explanation: "Premier National Institute! 12 ICU beds & 18 Emergency beds ready, full trauma team active.",
        rating: 4.9,
        address: "Sri Aurobindo Marg, Ansari Nagar, New Delhi",
        phone: "+911126588500",
        beds: [
            { bedType: "ICU", availableCount: 12, totalCapacity: 40 },
            { bedType: "EMERGENCY", availableCount: 18, totalCapacity: 60 },
            { bedType: "VENTILATOR", availableCount: 8, totalCapacity: 20 },
            { bedType: "SPECIALIST", availableCount: 15, totalCapacity: 30 },
            { bedType: "GENERAL", availableCount: 45, totalCapacity: 200 }
        ],
        incoming: [
            { patientName: "Anil Sharma", age: 54, condition: "Cardiac Emergency / Chest Pain", eta: "4 mins", ambulance: "KA-01-EQ-1001", status: "EN_ROUTE" }
        ]
    },
    {
        id: 2,
        name: "Fatima Hospital (Gorakhpur)",
        distanceKm: 4.5,
        matchPercentage: 95,
        recommendationTier: "HIGHLY_RECOMMENDED",
        explanation: "Top Specialty Hospital in Eastern UP! 6 ICU beds available, 24x7 emergency & cardiac care.",
        rating: 4.8,
        address: "Medical College Road, Basharatpur, Gorakhpur, UP",
        phone: "+915512501444",
        beds: [
            { bedType: "ICU", availableCount: 6, totalCapacity: 20 },
            { bedType: "EMERGENCY", availableCount: 10, totalCapacity: 35 },
            { bedType: "VENTILATOR", availableCount: 4, totalCapacity: 10 },
            { bedType: "SPECIALIST", availableCount: 5, totalCapacity: 15 },
            { bedType: "GENERAL", availableCount: 25, totalCapacity: 100 }
        ],
        incoming: []
    },
    {
        id: 3,
        name: "IGIMS Super Specialty & Trauma Center (Patna)",
        distanceKm: 5.1,
        matchPercentage: 94,
        recommendationTier: "HIGHLY_RECOMMENDED",
        explanation: "Leading Bihar Super Specialty Institute! 8 ICU beds & 12 ER beds available, advanced cardiology.",
        rating: 4.7,
        address: "Sheikhpura, Bailey Road, Patna, Bihar",
        phone: "+916122297631",
        beds: [
            { bedType: "ICU", availableCount: 8, totalCapacity: 25 },
            { bedType: "EMERGENCY", availableCount: 12, totalCapacity: 40 },
            { bedType: "VENTILATOR", availableCount: 5, totalCapacity: 15 },
            { bedType: "SPECIALIST", availableCount: 7, totalCapacity: 20 },
            { bedType: "GENERAL", availableCount: 30, totalCapacity: 120 }
        ],
        incoming: []
    },
    {
        id: 4,
        name: "PMCH Emergency & Acute Trauma Center (Patna)",
        distanceKm: 5.8,
        matchPercentage: 92,
        recommendationTier: "HIGHLY_RECOMMENDED",
        explanation: "Historic Government Trauma Center! 5 ICU beds & 15 Emergency beds ready.",
        rating: 4.6,
        address: "Ashok Rajpath, Mahendru, Patna, Bihar",
        phone: "+916122300080",
        beds: [
            { bedType: "ICU", availableCount: 5, totalCapacity: 30 },
            { bedType: "EMERGENCY", availableCount: 15, totalCapacity: 50 },
            { bedType: "VENTILATOR", availableCount: 3, totalCapacity: 12 },
            { bedType: "SPECIALIST", availableCount: 8, totalCapacity: 25 },
            { bedType: "GENERAL", availableCount: 50, totalCapacity: 180 }
        ],
        incoming: []
    },
    {
        id: 5,
        name: "Apollo Emergency & Heart Institute",
        distanceKm: 6.2,
        matchPercentage: 91,
        recommendationTier: "HIGHLY_RECOMMENDED",
        explanation: "World-class private cardiac care! 9 ICU beds & 6 Ventilators available.",
        rating: 4.9,
        address: "Sarita Vihar, Delhi Mathura Road, New Delhi",
        phone: "+911126925858",
        beds: [
            { bedType: "ICU", availableCount: 9, totalCapacity: 30 },
            { bedType: "EMERGENCY", availableCount: 14, totalCapacity: 45 },
            { bedType: "VENTILATOR", availableCount: 6, totalCapacity: 15 },
            { bedType: "SPECIALIST", availableCount: 10, totalCapacity: 25 },
            { bedType: "GENERAL", availableCount: 35, totalCapacity: 130 }
        ],
        incoming: []
    },
    {
        id: 6,
        name: "Fortis Escorts Heart & Trauma Center",
        distanceKm: 6.9,
        matchPercentage: 89,
        recommendationTier: "SUITABLE",
        explanation: "Specialized Cardiac & Vascular Center! 7 ICU beds available.",
        rating: 4.8,
        address: "Okhla Road, Opp Holy Family Hospital, New Delhi",
        phone: "+911147135000",
        beds: [
            { bedType: "ICU", availableCount: 7, totalCapacity: 22 },
            { bedType: "EMERGENCY", availableCount: 11, totalCapacity: 35 },
            { bedType: "VENTILATOR", availableCount: 4, totalCapacity: 12 },
            { bedType: "SPECIALIST", availableCount: 6, totalCapacity: 18 },
            { bedType: "GENERAL", availableCount: 28, totalCapacity: 90 }
        ],
        incoming: []
    },
    {
        id: 7,
        name: "Max Super Specialty Trauma Care",
        distanceKm: 7.4,
        matchPercentage: 88,
        recommendationTier: "SUITABLE",
        explanation: "Comprehensive Trauma & Emergency Care! 10 ICU beds & 16 ER beds ready.",
        rating: 4.7,
        address: "1 Press Enclave Road, Saket, New Delhi",
        phone: "+911126515050",
        beds: [
            { bedType: "ICU", availableCount: 10, totalCapacity: 28 },
            { bedType: "EMERGENCY", availableCount: 16, totalCapacity: 40 },
            { bedType: "VENTILATOR", availableCount: 5, totalCapacity: 14 },
            { bedType: "SPECIALIST", availableCount: 9, totalCapacity: 22 },
            { bedType: "GENERAL", availableCount: 40, totalCapacity: 110 }
        ],
        incoming: []
    },
    {
        id: 8,
        name: "Medanta The Medicity Emergency & Critical Care",
        distanceKm: 8.0,
        matchPercentage: 96,
        recommendationTier: "HIGHLY_RECOMMENDED",
        explanation: "Advanced multi-specialty institute! 11 ICU beds & 20 Emergency beds ready.",
        rating: 4.9,
        address: "CH Baktawar Singh Road, Sector 38, Gurugram, Haryana",
        phone: "+911244141414",
        beds: [
            { bedType: "ICU", availableCount: 11, totalCapacity: 35 },
            { bedType: "EMERGENCY", availableCount: 20, totalCapacity: 50 },
            { bedType: "VENTILATOR", availableCount: 7, totalCapacity: 18 },
            { bedType: "SPECIALIST", availableCount: 12, totalCapacity: 30 },
            { bedType: "GENERAL", availableCount: 60, totalCapacity: 150 }
        ],
        incoming: []
    },
    {
        id: 9,
        name: "Manipal Emergency & Cardiac Institute",
        distanceKm: 8.7,
        matchPercentage: 86,
        recommendationTier: "SUITABLE",
        explanation: "Leading Emergency & Critical Care Hospital! 6 ICU beds available.",
        rating: 4.6,
        address: "98 HAL Old Airport Road, Kodihalli, Bengaluru",
        phone: "+918025024444",
        beds: [
            { bedType: "ICU", availableCount: 6, totalCapacity: 20 },
            { bedType: "EMERGENCY", availableCount: 9, totalCapacity: 30 },
            { bedType: "VENTILATOR", availableCount: 3, totalCapacity: 10 },
            { bedType: "SPECIALIST", availableCount: 5, totalCapacity: 15 },
            { bedType: "GENERAL", availableCount: 22, totalCapacity: 85 }
        ],
        incoming: []
    },
    {
        id: 10,
        name: "Sir Ganga Ram Emergency Hospital",
        distanceKm: 9.3,
        matchPercentage: 87,
        recommendationTier: "SUITABLE",
        explanation: "Premier Multi-Specialty Hospital! 8 ICU beds & 13 ER beds available.",
        rating: 4.8,
        address: "Sir Ganga Ram Hospital Marg, Rajinder Nagar, New Delhi",
        phone: "+911125750000",
        beds: [
            { bedType: "ICU", availableCount: 8, totalCapacity: 25 },
            { bedType: "EMERGENCY", availableCount: 13, totalCapacity: 38 },
            { bedType: "VENTILATOR", availableCount: 4, totalCapacity: 12 },
            { bedType: "SPECIALIST", availableCount: 7, totalCapacity: 20 },
            { bedType: "GENERAL", availableCount: 32, totalCapacity: 100 }
        ],
        incoming: []
    }
];

// 1. INITIALIZATION & VIEW SWITCHING WITH PHONE BACK-BUTTON SUPPORT
document.addEventListener("DOMContentLoaded", () => {
    initLeafletMaps();
    loadDefaultHospitals();
    initAdminCharts();
    checkSavedSession();
    
    // Set initial history state
    const hash = window.location.hash.replace("#", "") || "landing";
    history.replaceState({ view: hash }, '', `#${hash}`);
});

function checkSavedSession() {
    const saved = localStorage.getItem("amburoute_user");
    if (saved) {
        try {
            currentUser = JSON.parse(saved);
            applyUserSession(currentUser);
        } catch(e) {}
    }
}

// MOBILE HAMBURGER MENU DRAWER HANDLERS
function toggleMobileMenu() {
    const drawer = document.getElementById("mobile-menu-drawer");
    const overlay = document.getElementById("mobile-menu-overlay");
    if (drawer && overlay) {
        drawer.classList.toggle("active");
        overlay.classList.toggle("active");
    }
}

function closeMobileMenu() {
    const drawer = document.getElementById("mobile-menu-drawer");
    const overlay = document.getElementById("mobile-menu-overlay");
    if (drawer && overlay) {
        drawer.classList.remove("active");
        overlay.classList.remove("active");
    }
}

function handleNavBackBtn() {
    if (window.history.length > 1) {
        window.history.back();
    } else {
        switchView("landing");
    }
}

function switchView(viewName, isPopState = false) {
    if (viewName === 'admin' && (!currentUser || currentUser.role !== 'ADMIN')) {
        alert("🔒 ACCESS RESTRICTED!\nAdmin Command Center is only accessible when logged in as sudhanshuadmin@login.");
        openAuthModal();
        return;
    }

    // Push state into browser history for 1-step phone back button support
    if (!isPopState) {
        history.pushState({ view: viewName }, '', `#${viewName}`);
    }

    // Hide all view sections
    document.querySelectorAll(".view-section").forEach(sec => sec.classList.remove("active"));
    
    // Clear active states on desktop nav links
    document.querySelectorAll(".nav-link").forEach(lnk => {
        lnk.classList.remove("active");
        lnk.classList.remove("active-admin");
    });

    // Clear active states on mobile drawer nav items
    document.querySelectorAll(".drawer-nav-item").forEach(item => {
        item.classList.remove("active");
        item.classList.remove("active-admin");
    });

    // Show target section
    const targetSection = document.getElementById(`view-${viewName}`);
    if (targetSection) {
        targetSection.classList.add("active");
    }

    // Toggle Back Button next to AmbuRoute Logo
    const backBtn = document.getElementById("nav-back-btn");
    if (backBtn) {
        if (viewName === "landing") {
            backBtn.style.display = "none";
        } else {
            backBtn.style.display = "inline-flex";
        }
    }

    // Highlight desktop nav link
    const activeNavBtn = document.getElementById(`nav-${viewName}`);
    if (activeNavBtn) {
        if (viewName === 'admin') {
            activeNavBtn.classList.add("active-admin");
        } else {
            activeNavBtn.classList.add("active");
        }
    }

    // Highlight mobile drawer item
    const activeDrawerItem = document.getElementById(`drawer-nav-${viewName}`);
    if (activeDrawerItem) {
        if (viewName === 'admin') {
            activeDrawerItem.classList.add("active-admin");
        } else {
            activeDrawerItem.classList.add("active");
        }
    }

    if (viewName === 'driver') {
        initDriverMap();
    } else if (viewName === 'dashboard') {
        initMainMap();
    }

    setTimeout(() => {
        if (mainMap) mainMap.invalidateSize();
        if (driverMap) driverMap.invalidateSize();
    }, 200);
}

// Phone / Browser Back Button Event Handler
window.addEventListener("popstate", (e) => {
    if (e.state && e.state.view) {
        switchView(e.state.view, true);
    } else {
        const hash = window.location.hash.replace("#", "");
        if (hash && document.getElementById(`view-${hash}`)) {
            switchView(hash, true);
        } else {
            switchView("landing", true);
        }
    }
});

// 2. AUTHENTICATION & ROLE-BASED ACCESS CONTROL
function handleAuthBtnClick() {
    if (currentUser) {
        handleLogout();
    } else {
        openAuthModal();
    }
}

function openAuthModal() {
    document.getElementById("modal-auth").classList.add("active");
}
function closeAuthModal() {
    document.getElementById("modal-auth").classList.remove("active");
}

function switchAuthTab(tabName) {
    const loginForm = document.getElementById("login-form");
    const regForm = document.getElementById("register-form");
    const loginBtn = document.getElementById("tab-btn-login");
    const regBtn = document.getElementById("tab-btn-register");

    if (tabName === 'login') {
        loginForm.style.display = "block";
        regForm.style.display = "none";
        loginBtn.classList.add("active");
        regBtn.classList.remove("active");
    } else {
        loginForm.style.display = "none";
        regForm.style.display = "block";
        loginBtn.classList.remove("active");
        regBtn.classList.add("active");
    }
}

function handleLoginSubmit(e) {
    e.preventDefault();
    const email = document.getElementById("login-email").value.trim();
    const pass = document.getElementById("login-password").value.trim();

    if (email.toLowerCase() === "sudhanshuadmin@login" && pass === "12345") {
        const user = {
            email: "sudhanshuadmin@login",
            fullName: "Sudhanshu (System Admin)",
            role: "ADMIN"
        };
        applyUserSession(user);
        closeAuthModal();
        alert("🛡️ ADMIN ACCESS UNLOCKED!\nLogged in as sudhanshuadmin@login. Admin Command Center is now unlocked in the menu.");
        switchView("dashboard");
        return;
    }

    const user = {
        email: email,
        fullName: email.split("@")[0],
        role: "PATIENT"
    };
    applyUserSession(user);
    closeAuthModal();
    alert(`👋 Welcome back, ${user.fullName}!\nLogged in as Patient.`);
    switchView("dashboard");
}

function handleRegisterSubmit(e) {
    e.preventDefault();
    const name = document.getElementById("reg-name").value.trim();
    const email = document.getElementById("reg-email").value.trim();

    const user = {
        email: email,
        fullName: name,
        role: "PATIENT"
    };
    applyUserSession(user);
    closeAuthModal();
    alert(`🎉 Account created successfully!\nWelcome to AmbuRoute, ${name}. Admin features are restricted to authorized admins.`);
    switchView("dashboard");
}

function applyUserSession(user) {
    currentUser = user;
    localStorage.setItem("amburoute_user", JSON.stringify(user));

    const adminNavItem = document.getElementById("admin-nav-item");
    const drawerAdminNavItem = document.getElementById("drawer-nav-admin");
    
    const authBtn = document.getElementById("auth-btn");
    const drawerAuthBtn = document.getElementById("drawer-auth-btn");

    const roleBadge = document.getElementById("current-role-badge");
    const drawerRoleBadge = document.getElementById("drawer-role-badge");

    if (user.role === "ADMIN") {
        if (adminNavItem) adminNavItem.style.display = "inline-block";
        if (drawerAdminNavItem) drawerAdminNavItem.style.display = "flex";
        
        if (authBtn) authBtn.innerText = `Sign Out (${user.fullName})`;
        if (drawerAuthBtn) drawerAuthBtn.innerText = `Sign Out (${user.fullName})`;

        if (roleBadge) {
            roleBadge.innerText = "Role: ADMIN";
            roleBadge.style.background = "rgba(245, 158, 11, 0.2)";
            roleBadge.style.borderColor = "var(--warning-amber)";
            roleBadge.style.color = "var(--warning-amber)";
        }
        if (drawerRoleBadge) {
            drawerRoleBadge.innerText = "Role: ADMIN";
            drawerRoleBadge.style.background = "rgba(245, 158, 11, 0.2)";
            drawerRoleBadge.style.borderColor = "var(--warning-amber)";
            drawerRoleBadge.style.color = "var(--warning-amber)";
        }
    } else {
        if (adminNavItem) adminNavItem.style.display = "none";
        if (drawerAdminNavItem) drawerAdminNavItem.style.display = "none";

        if (authBtn) authBtn.innerText = `Sign Out (${user.fullName})`;
        if (drawerAuthBtn) drawerAuthBtn.innerText = `Sign Out (${user.fullName})`;

        if (roleBadge) {
            roleBadge.innerText = "Role: PATIENT";
            roleBadge.style.background = "rgba(59, 130, 246, 0.2)";
            roleBadge.style.borderColor = "var(--info-blue)";
            roleBadge.style.color = "var(--info-blue)";
        }
        if (drawerRoleBadge) {
            drawerRoleBadge.innerText = "Role: PATIENT";
            drawerRoleBadge.style.background = "rgba(59, 130, 246, 0.2)";
            drawerRoleBadge.style.borderColor = "var(--info-blue)";
            drawerRoleBadge.style.color = "var(--info-blue)";
        }
    }
}

function handleLogout() {
    currentUser = null;
    localStorage.removeItem("amburoute_user");

    const adminNavItem = document.getElementById("admin-nav-item");
    const drawerAdminNavItem = document.getElementById("drawer-nav-admin");

    const authBtn = document.getElementById("auth-btn");
    const drawerAuthBtn = document.getElementById("drawer-auth-btn");

    const roleBadge = document.getElementById("current-role-badge");
    const drawerRoleBadge = document.getElementById("drawer-role-badge");

    if (adminNavItem) adminNavItem.style.display = "none";
    if (drawerAdminNavItem) drawerAdminNavItem.style.display = "none";

    if (authBtn) authBtn.innerText = "Sign In / Register";
    if (drawerAuthBtn) drawerAuthBtn.innerText = "Sign In / Register";

    if (roleBadge) {
        roleBadge.innerText = "Role: GUEST";
        roleBadge.style.background = "rgba(255,255,255,0.08)";
        roleBadge.style.borderColor = "var(--border-glass)";
        roleBadge.style.color = "var(--text-secondary)";
    }
    if (drawerRoleBadge) {
        drawerRoleBadge.innerText = "Role: GUEST";
        drawerRoleBadge.style.background = "rgba(255,255,255,0.08)";
        drawerRoleBadge.style.borderColor = "var(--border-glass)";
        drawerRoleBadge.style.color = "var(--text-secondary)";
    }

    alert("ℹ️ Signed out successfully.");
    switchView("landing");
}

// 3. LEAFLET MAP INTEGRATION
function initMainMap() {
    const mapEl = document.getElementById("leaflet-map");
    if (mapEl && !mainMap) {
        mainMap = L.map("leaflet-map").setView(MOCK_PATIENT_COORDS, 14);
        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
            attribution: '© OpenStreetMap contributors | AmbuRoute'
        }).addTo(mainMap);

        L.marker(MOCK_PATIENT_COORDS).addTo(mainMap)
            .bindPopup("<b>📍 Patient Distress Location</b><br>MG Road Sector 4").openPopup();

        L.marker(MOCK_HOSPITAL_COORDS).addTo(mainMap)
            .bindPopup("<b>🏥 City Emergency Super Specialty Hospital</b><br>ICU Available: 4 Beds");

        L.circleMarker(MOCK_RAILWAY_COORDS, {
            color: '#ef4444',
            fillColor: '#ef4444',
            fillOpacity: 0.8,
            radius: 10
        }).addTo(mainMap).bindPopup("<b>🚆 Railway Gate LC-42</b><br>⚠️ Train Approaching (Gate Closed)");

        currentRoutePolyline = L.polyline(ROUTE_A_DIRECT, { color: '#ef4444', weight: 5, dashArray: '8, 8' }).addTo(mainMap);
    }
}

function initDriverMap() {
    const driverEl = document.getElementById("driver-map");
    if (!driverEl) return;

    if (!driverMap) {
        driverMap = L.map("driver-map").setView(MOCK_PATIENT_COORDS, 14);
        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
            attribution: '© OpenStreetMap contributors | AmbuRoute Driver HUD'
        }).addTo(driverMap);

        ambulanceMarker = L.marker(MOCK_PATIENT_COORDS).addTo(driverMap)
            .bindPopup("<b>🚑 Ambulance KA-01-EQ-1001</b><br>Driver: Rajesh Kumar<br>Status: EN_ROUTE").openPopup();

        L.marker(MOCK_HOSPITAL_COORDS).addTo(driverMap)
            .bindPopup("<b>🏥 Destination: City Emergency Hospital</b><br>ICU Bed #4 Reserved");

        L.circleMarker(MOCK_RAILWAY_COORDS, {
            color: '#ef4444',
            fillColor: '#ef4444',
            fillOpacity: 0.9,
            radius: 12
        }).addTo(driverMap).bindPopup("<b>🚆 Railway Gate LC-42</b><br>⚠️ GATE CLOSED - Route B Bypass Active");

        L.polyline(ROUTE_B_SAFE_ALTERNATE, { color: '#10b981', weight: 6 }).addTo(driverMap);
    }

    setTimeout(() => {
        if (driverMap) driverMap.invalidateSize();
    }, 150);
}

function initLeafletMaps() {
    initMainMap();
    initDriverMap();
}

function recenterDriverMap() {
    if (driverMap && ambulanceMarker) {
        const coords = ambulanceMarker.getLatLng();
        driverMap.setView(coords, 15);
        ambulanceMarker.openPopup();
    }
}

// 4. GPS TURN-BY-TURN NAVIGATION ENGINE FOR DRIVER VIEW
function toggleDriverGpsNavigation() {
    const btn = document.getElementById("driver-nav-toggle-btn");
    
    if (isGpsNavigating) {
        clearInterval(gpsNavInterval);
        isGpsNavigating = false;
        if (btn) {
            btn.innerHTML = "▶ Start GPS Navigation";
            btn.className = "btn btn-success";
        }
    } else {
        isGpsNavigating = true;
        if (btn) {
            btn.innerHTML = "⏸ Pause Navigation";
            btn.className = "btn btn-outline";
        }

        gpsNavInterval = setInterval(() => {
            advanceGpsNavigationStep();
        }, 2500);
    }
}

function advanceGpsNavigationStep() {
    if (!ambulanceMarker) return;

    navStepIndex = (navStepIndex + 1) % ROUTE_B_SAFE_ALTERNATE.length;
    const coords = ROUTE_B_SAFE_ALTERNATE[navStepIndex];
    const maneuver = GPS_MANEUVERS[navStepIndex];

    ambulanceMarker.setLatLng(coords);
    if (driverMap) driverMap.panTo(coords);

    const iconEl = document.getElementById("turn-icon");
    const distEl = document.getElementById("turn-distance");
    const instEl = document.getElementById("turn-instruction");
    const subEl = document.getElementById("turn-subtext");

    if (iconEl) iconEl.innerText = maneuver.icon;
    if (distEl) distEl.innerText = maneuver.distance;
    if (instEl) instEl.innerText = maneuver.instruction;
    if (subEl) subEl.innerText = maneuver.subtext;

    const titleEl = document.getElementById("driver-alert-title");
    const descEl = document.getElementById("driver-alert-desc");
    const speedEl = document.getElementById("driver-speed-disp");
    const etaEl = document.getElementById("driver-eta-disp");

    if (titleEl) titleEl.innerText = maneuver.alertTitle;
    if (descEl) descEl.innerText = maneuver.alertDesc;
    if (speedEl) speedEl.innerText = `${maneuver.speed} km/h`;
    if (etaEl) etaEl.innerText = `${maneuver.distRem} | ${maneuver.eta}`;

    if (navStepIndex === ROUTE_B_SAFE_ALTERNATE.length - 1) {
        clearInterval(gpsNavInterval);
        isGpsNavigating = false;
        const btn = document.getElementById("driver-nav-toggle-btn");
        if (btn) {
            btn.innerHTML = "🔄 Restart Route Navigation";
            btn.className = "btn btn-success";
        }
    }
}

// 5. EMERGENCY ACTIVATION WIZARD
function openEmergencyModal() {
    document.getElementById("modal-emergency").classList.add("active");
}
function closeEmergencyModal() {
    document.getElementById("modal-emergency").classList.remove("active");
}

function handleEmergencySubmit(e) {
    e.preventDefault();
    const name = document.getElementById("em-patient-name").value;
    const age = document.getElementById("em-patient-age").value;
    const type = document.getElementById("em-type").value;
    const crit = document.getElementById("em-criticality").value;
    const cond = document.getElementById("em-condition").value;

    const payload = {
        patientName: name,
        patientAge: parseInt(age),
        emergencyType: type,
        criticality: crit,
        conditionDesc: cond,
        sourceLat: MOCK_PATIENT_COORDS[0],
        sourceLng: MOCK_PATIENT_COORDS[1]
    };

    fetch(`${API_BASE}/emergency/activate`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    })
    .then(res => res.json())
    .then(data => {
        closeEmergencyModal();
        alert(`🚨 EMERGENCY ACTIVATED!\nAssigned Hospital: ${data.hospitalName}\nAmbulance: ${data.ambulanceVehicleNumber}\nGreen Corridor: ACTIVE`);
        switchView("dashboard");
    })
    .catch(() => {
        closeEmergencyModal();
        alert(`🚨 EMERGENCY ACTIVATED (Demo Mode)!\nAssigned Hospital: City Emergency Super Specialty\nAmbulance: KA-01-EQ-1001\nGreen Corridor: ACTIVE`);
        switchView("dashboard");
    });
}

// 6. REROUTING & RAILWAY INTELLIGENCE
function selectAlternateRoute() {
    if (mainMap && currentRoutePolyline) {
        mainMap.removeLayer(currentRoutePolyline);
        currentRoutePolyline = L.polyline(ROUTE_B_SAFE_ALTERNATE, { color: '#10b981', weight: 6 }).addTo(mainMap);
    }

    const alertBox = document.getElementById("railway-alert-box");
    if (alertBox) {
        alertBox.style.background = "linear-gradient(135deg, rgba(16, 185, 129, 0.15) 0%, rgba(59, 130, 246, 0.15) 100%)";
        alertBox.style.borderColor = "var(--accent-green)";
        document.getElementById("railway-alert-text").innerHTML = "✅ <strong>Alternate Route B Selected:</strong> Avoided Railway Crossing Gate LC-42 closure. ETA: 14 min.";
    }

    alert("✅ Alternate Route Activated!\nNavigating via South Flyover Bypass. Railway crossing risk eliminated.");
}

// 7. HOSPITALS & ICU BED SEARCH
function loadDefaultHospitals() {
    fetch(`${API_BASE}/hospitals`)
        .then(res => res.json())
        .then(data => {
            currentHospitals = data;
            renderHospitalsList(data);
        })
        .catch(() => {
            currentHospitals = MOCK_HOSPITALS_DATA;
            renderHospitalsList(MOCK_HOSPITALS_DATA);
        });
}

function searchAiHospitals() {
    const text = document.getElementById("ai-symptom-input").value;
    fetch(`${API_BASE}/hospitals/recommend?lat=12.9690&lng=77.5850&emergencyType=CARDIAC&criticality=CRITICAL&conditionDesc=${encodeURIComponent(text)}`)
        .then(res => res.json())
        .then(data => renderHospitalsList(data))
        .catch(() => loadDefaultHospitals());
}

function renderHospitalsList(hospitals) {
    const container = document.getElementById("hospitals-list-container");
    if (!container) return;

    container.innerHTML = hospitals.map(h => `
        <div class="dash-card glass-panel" style="padding: 1.5rem; ${h.recommendationTier === 'HIGHLY_RECOMMENDED' ? 'border: 1.5px solid var(--accent-green);' : ''}" onclick="openHospitalDetailModal(${h.id})">
            <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 0.75rem;">
                <h3 style="font-size: 1.1rem; font-weight: 700; color: var(--text-primary);">${h.name}</h3>
                <span class="badge ${h.matchPercentage >= 85 ? 'badge-success' : 'badge-warning'}">${h.matchPercentage || 90}% Match</span>
            </div>
            <p style="font-size: 0.85rem; color: var(--text-secondary); margin-bottom: 0.5rem;">📍 ${h.address || 'Central District'} | Distance: ${h.distanceKm || 4.2} km</p>
            <p style="font-size: 0.85rem; color: var(--text-primary); margin-bottom: 1rem;">💡 ${h.explanation || 'Verified Emergency Capabilities'}</p>
            <div style="display: flex; justify-content: space-between; align-items: center;" onclick="event.stopPropagation()">
                <span style="font-size: 0.9rem; font-weight: 600; color: var(--accent-green);">🟢 Live ER & Beds Ready</span>
                <button class="btn btn-primary" style="padding: 0.4rem 0.85rem; font-size: 0.85rem;" onclick="openHospitalDetailModal(${h.id})">View ER & Beds ▶</button>
            </div>
        </div>
    `).join("");
}

// 8. HOSPITAL DETAIL MODAL
function openHospitalDetailModal(hospitalId) {
    const hospital = (currentHospitals && currentHospitals.length) 
        ? currentHospitals.find(h => h.id === hospitalId) 
        : MOCK_HOSPITALS_DATA.find(h => h.id === hospitalId) || MOCK_HOSPITALS_DATA[0];

    if (!hospital) return;

    document.getElementById("hosp-detail-name").innerText = hospital.name;
    document.getElementById("hosp-detail-address").innerText = `📍 ${hospital.address || 'Metropolitan Emergency District'} | Phone: ${hospital.phone || '+918022114455'} | ⭐ ${hospital.rating || 4.8}`;

    const bedsContainer = document.getElementById("hosp-detail-beds");
    const bedList = hospital.beds || [
        { bedType: "ICU", availableCount: 4, totalCapacity: 25 },
        { bedType: "EMERGENCY", availableCount: 8, totalCapacity: 40 },
        { bedType: "VENTILATOR", availableCount: 2, totalCapacity: 12 }
    ];

    bedsContainer.innerHTML = bedList.map(b => `
        <div style="background: rgba(255, 255, 255, 0.05); padding: 0.85rem; border-radius: 8px; border: 1px solid var(--border-glass); text-align: center;">
            <div style="font-size: 0.75rem; color: var(--text-secondary); text-transform: uppercase;">${b.bedType} Beds</div>
            <div style="font-size: 1.5rem; font-weight: 800; color: ${b.availableCount > 0 ? 'var(--accent-green)' : 'var(--primary-red)'};">
                ${b.availableCount} / ${b.totalCapacity}
            </div>
        </div>
    `).join("");

    const incomingContainer = document.getElementById("hosp-detail-incoming");
    const incomingList = hospital.incoming || [
        { patientName: "Anil Sharma", age: 54, condition: "Severe Cardiac Event", eta: "4 mins", ambulance: "KA-01-EQ-1001" }
    ];

    if (incomingList.length > 0) {
        incomingContainer.innerHTML = incomingList.map(inc => `
            <div class="glass-panel" style="padding: 1rem; border: 1.5px solid var(--primary-red); margin-bottom: 0.75rem;">
                <div style="display: flex; justify-content: space-between; margin-bottom: 0.35rem;">
                    <strong style="color: var(--primary-red);">🚨 INCOMING PATIENT (${inc.patientName}, Age ${inc.age})</strong>
                    <span class="badge badge-critical">ETA: ${inc.eta}</span>
                </div>
                <p style="font-size: 0.85rem; color: var(--text-secondary);">Condition: ${inc.condition} | Assigned: Ambulance ${inc.ambulance}</p>
                <div style="margin-top: 0.5rem; font-size: 0.8rem; color: var(--accent-green);">
                    ✅ Trauma Team Prepped | ICU Bed #4 Reserved
                </div>
            </div>
        `).join("");
    } else {
        incomingContainer.innerHTML = `<div style="padding: 1rem; background: rgba(255,255,255,0.03); border-radius: 8px; font-size: 0.85rem; color: var(--text-secondary); text-align: center;">No active incoming ambulances right now. ER trauma bay clear.</div>`;
    }

    const reserveBtn = document.getElementById("hosp-detail-reserve-btn");
    reserveBtn.onclick = () => {
        closeHospitalDetailModal();
        openBedModal(hospital.id, hospital.name);
    };

    document.getElementById("modal-hospital-detail").classList.add("active");
}

function closeHospitalDetailModal() {
    document.getElementById("modal-hospital-detail").classList.remove("active");
}

// 9. BED RESERVATION MODAL
function openBedModal(hospitalId, hospitalName) {
    document.getElementById("reserve-hospital-id").value = hospitalId;
    document.getElementById("reserve-hospital-name").value = hospitalName;
    document.getElementById("modal-reserve-bed").classList.add("active");
}
function closeBedModal() {
    document.getElementById("modal-reserve-bed").classList.remove("active");
}
function handleBedSubmit(e) {
    e.preventDefault();
    closeBedModal();
    alert("✅ HOSPITAL BED RESERVATION CONFIRMED!\nEmergency room trauma team notified of pre-arrival.");
}

// 10. EMERGENCY CHATBOT WIDGET
function toggleChat() {
    const modal = document.getElementById("chat-modal");
    if (modal) modal.classList.toggle("active");
}

function sendChatMessage() {
    const input = document.getElementById("chat-input-field");
    const text = input.value.trim();
    if (!text) return;

    const chatBody = document.getElementById("chat-body");
    chatBody.innerHTML += `<div class="msg-bubble msg-user">${text}</div>`;
    input.value = "";
    chatBody.scrollTop = chatBody.scrollHeight;

    fetch(`${API_BASE}/chat/message`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message: text })
    })
    .then(res => res.json())
    .then(data => appendBotReply(data.reply))
    .catch(() => {
        if (text.toLowerCase().includes("chest") || text.toLowerCase().includes("heart")) {
            appendBotReply("🚨 CRITICAL WARNING: Chest pain may indicate a cardiac emergency. Click 🚨 ACTIVATE EMERGENCY immediately!");
        } else {
            appendBotReply("Please describe patient symptoms in detail. For life threatening crises, activate Emergency dispatch.");
        }
    });
}

function appendBotReply(replyText) {
    const chatBody = document.getElementById("chat-body");
    chatBody.innerHTML += `<div class="msg-bubble msg-bot">${replyText}</div>`;
    chatBody.scrollTop = chatBody.scrollHeight;
}

// 11. ADMIN ANALYTICS CHARTS
function initAdminCharts() {
    const ctxTrends = document.getElementById("chart-trends");
    if (ctxTrends && !chartTrends) {
        chartTrends = new Chart(ctxTrends, {
            type: 'line',
            data: {
                labels: ['00:00', '04:00', '08:00', '12:00', '16:00', '20:00'],
                datasets: [{
                    label: 'Emergency Response Volume',
                    data: [4, 2, 9, 14, 18, 11],
                    borderColor: '#ef4444',
                    backgroundColor: 'rgba(239, 68, 68, 0.1)',
                    fill: true,
                    tension: 0.4
                }]
            },
            options: { responsive: true, plugins: { legend: { labels: { color: '#94a3b8' } } } }
        });
    }

    const ctxIcu = document.getElementById("chart-icu");
    if (ctxIcu && !chartIcu) {
        chartIcu = new Chart(ctxIcu, {
            type: 'doughnut',
            data: {
                labels: ['Available ICU Beds', 'Occupied ICU Beds'],
                datasets: [{
                    data: [12, 48],
                    backgroundColor: ['#10b981', '#1e293b']
                }]
            },
            options: { responsive: true, plugins: { legend: { labels: { color: '#94a3b8' } } } }
        });
    }
}

// 12. ⚡ ONE-CLICK AUTOMATED DEMO ENGINE
function startAutomatedDemo() {
    applyUserSession({ email: "sudhanshuadmin@login", fullName: "Sudhanshu (System Admin)", role: "ADMIN" });
    
    alert("⚡ STARTING AUTOMATED END-TO-END DEMO SEQUENCE...\nAmbuRoute will simulate an entire emergency workflow automatically.");
    
    switchView("dashboard");

    setTimeout(() => {
        openEmergencyModal();
        document.getElementById("em-patient-name").value = "Anil Sharma (Mother Emergency Demo)";
        document.getElementById("em-condition").value = "Severe acute cardiac distress, train crossing ahead detected";

        setTimeout(() => {
            closeEmergencyModal();
            alert("🚨 STEP 1: Emergency Activated!\nAmbulance KA-01-EQ-1001 Dispatched.\nGreen Corridor Activated.");

            setTimeout(() => {
                const alertBox = document.getElementById("railway-alert-box");
                if (alertBox) {
                    alertBox.style.animation = "pulse 1s infinite";
                }
                alert("🚆 STEP 2: Railway Crossing Risk Detected!\nGate LC-42 closure expected in 4 min right as ambulance arrives.");

                setTimeout(() => {
                    selectAlternateRoute();
                    if (alertBox) alertBox.style.animation = "none";

                    setTimeout(() => {
                        switchView("driver");
                        toggleDriverGpsNavigation();

                        setTimeout(() => {
                            switchView("hospitals");
                            openHospitalDetailModal(1);
                            
                            setTimeout(() => {
                                switchView("admin");
                                alert("🎉 DEMO COMPLETE!\nSuccessfully demonstrated:\n1. Emergency Activation\n2. AI Hospital Matching\n3. Railway Risk Bypass Reroute\n4. Green Corridor Clearing\n5. GPS Turn-by-Turn Navigation HUD\n6. Admin Command Center (Logged in as sudhanshuadmin@login).");
                            }, 3000);
                        }, 2500);
                    }, 2000);
                }, 2000);
            }, 2000);
        }, 1500);
    }, 1000);
}
