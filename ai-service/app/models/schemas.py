from pydantic import BaseModel, Field
from typing import List, Optional

class HospitalInput(BaseModel):
    id: int
    name: str
    latitude: float
    longitude: float
    icu_available: int
    emergency_available: int
    rating: float
    has_cardiac_specialty: bool = True
    has_trauma_specialty: bool = True
    has_respiratory_specialty: bool = True

class RecommendationRequest(BaseModel):
    patient_condition: str
    emergency_type: str  # CARDIAC, ACCIDENT_TRAUMA, RESPIRATORY, STROKE, PREGNANCY, OTHER
    criticality: str     # CRITICAL, HIGH, MODERATE
    patient_lat: float
    patient_lng: float
    hospitals: List[HospitalInput]

class HospitalRecommendationScore(BaseModel):
    hospital_id: int
    hospital_name: str
    distance_km: float
    match_percentage: int
    icu_available: int
    emergency_available: int
    recommendation_tier: str  # HIGHLY_RECOMMENDED, SUITABLE, FALLBACK
    explanation: str

class RecommendationResponse(BaseModel):
    recommended_hospitals: List[HospitalRecommendationScore]
    medical_disclaimer: str

class RouteEvaluateRequest(BaseModel):
    ambulance_lat: float
    ambulance_lng: float
    dest_lat: float
    dest_lng: float
    ambulance_speed_kmh: float = 50.0
    crossing_name: str
    crossing_lat: float
    crossing_lng: float
    train_arrival_mins: int
    gate_closure_mins: int = 10

class RouteEvaluateResponse(BaseModel):
    crossing_name: str
    ambulance_eta_mins: int
    train_eta_mins: int
    crossing_risk_level: str  # HIGH, MODERATE, LOW
    risk_score: float
    risk_explanation: str
    alternate_route_recommended: bool
    alternate_eta_addition_mins: int

class ChatMessageRequest(BaseModel):
    message: str
    context_emergency_type: Optional[str] = None

class ChatMessageResponse(BaseModel):
    reply: str
    detected_emergency_type: Optional[str] = None
    suggested_criticality: Optional[str] = None
    recommend_emergency_activation: bool = False
    medical_disclaimer: str
