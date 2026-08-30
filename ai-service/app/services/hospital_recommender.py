import math
from typing import List
from app.models.schemas import (
    RecommendationRequest,
    RecommendationResponse,
    HospitalRecommendationScore,
    HospitalInput
)

def haversine_distance(lat1: float, lon1: float, lat2: float, lon2: float) -> float:
    R = 6371.0 # Earth radius in km
    dlat = math.radians(lat2 - lat1)
    dlon = math.radians(lon2 - lon1)
    a = (math.sin(dlat / 2) ** 2 +
         math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) * math.sin(dlon / 2) ** 2)
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    return round(R * c, 2)

def calculate_hospital_matches(request: RecommendationRequest) -> RecommendationResponse:
    scores: List[HospitalRecommendationScore] = []
    
    for h in request.hospitals:
        dist = haversine_distance(request.patient_lat, request.patient_lng, h.latitude, h.longitude)
        
        # 1. Distance Penalty Score (max 40 pts)
        # 0 km = 40 pts, 10+ km = 5 pts
        dist_score = max(5.0, 40.0 - (dist * 3.5))
        
        # 2. Bed & ICU Availability Score (max 35 pts)
        if request.criticality == "CRITICAL" or request.emergency_type in ["CARDIAC", "STROKE", "ACCIDENT_TRAUMA"]:
            bed_score = min(35.0, (h.icu_available * 8.0) + (h.emergency_available * 2.0))
        else:
            bed_score = min(35.0, (h.emergency_available * 5.0) + (h.icu_available * 3.0))
            
        # 3. Medical Specialty Match Score (max 15 pts)
        specialty_score = 15.0
        em_type = request.emergency_type.upper()
        if em_type == "CARDIAC" and not h.has_cardiac_specialty:
            specialty_score = 5.0
        elif em_type == "ACCIDENT_TRAUMA" and not h.has_trauma_specialty:
            specialty_score = 5.0
        elif em_type == "RESPIRATORY" and not h.has_respiratory_specialty:
            specialty_score = 5.0
            
        # 4. Rating Score (max 10 pts)
        rating_score = (h.rating / 5.0) * 10.0
        
        total_raw = dist_score + bed_score + specialty_score + rating_score
        match_pct = int(min(98, max(45, round(total_raw))))
        
        # Determine Recommendation Tier & Explanation
        if match_pct >= 85 and h.icu_available > 0:
            tier = "HIGHLY_RECOMMENDED"
            explanation = f"Optimal match! Distance: {dist} km, {h.icu_available} ICU beds available, specialized for {request.emergency_type.lower()} emergencies."
        elif match_pct >= 70:
            tier = "SUITABLE"
            explanation = f"Suitable choice. Distance: {dist} km, {h.emergency_available} emergency beds ready."
        else:
            tier = "FALLBACK"
            explanation = f"Secondary fallback. Distance: {dist} km, limited ICU capacity ({h.icu_available} ICU beds)."
            
        scores.append(HospitalRecommendationScore(
            hospital_id=h.id,
            hospital_name=h.name,
            distance_km=dist,
            match_percentage=match_pct,
            icu_available=h.icu_available,
            emergency_available=h.emergency_available,
            recommendation_tier=tier,
            explanation=explanation
        ))
        
    # Sort descending by match percentage
    scores.sort(key=lambda x: x.match_percentage, reverse=True)
    
    return RecommendationResponse(
        recommended_hospitals=scores,
        medical_disclaimer="AI hospital recommendation is for triage optimization & spatial routing support only. In medical emergencies, consult certified emergency medical dispatchers."
    )
