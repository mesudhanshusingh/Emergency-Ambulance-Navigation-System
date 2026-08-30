import math
from app.models.schemas import RouteEvaluateRequest, RouteEvaluateResponse

def evaluate_railway_crossing_risk(req: RouteEvaluateRequest) -> RouteEvaluateResponse:
    # 1. Calculate Haversine distance from ambulance to crossing
    R = 6371.0
    dlat = math.radians(req.crossing_lat - req.ambulance_lat)
    dlon = math.radians(req.crossing_lng - req.ambulance_lng)
    a = (math.sin(dlat / 2) ** 2 +
         math.cos(math.radians(req.ambulance_lat)) * math.cos(math.radians(req.crossing_lat)) * math.sin(dlon / 2) ** 2)
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    dist_to_crossing = R * c
    
    # 2. Calculate Ambulance ETA at crossing (minutes)
    speed_kpm = req.ambulance_speed_kmh / 60.0 # km per min
    amb_eta_mins = max(1, int(round(dist_to_crossing / speed_kpm))) if speed_kpm > 0 else 5
    
    train_eta_mins = req.train_arrival_mins
    gate_closure_mins = req.gate_closure_mins
    
    # 3. Calculate Overlap Risk Matrix
    # Train gate closes at (train_eta_mins - 2) and stays closed for gate_closure_mins
    gate_close_start = max(0, train_eta_mins - 2)
    gate_close_end = train_eta_mins + gate_closure_mins
    
    # Check if ambulance arrival falls within gate closure window
    if gate_close_start <= amb_eta_mins <= gate_close_end or abs(amb_eta_mins - train_eta_mins) <= 3:
        risk_level = "HIGH"
        risk_score = 0.92
        explanation = f"Train EXP-12677 is scheduled to arrive in {train_eta_mins} mins. Gate will close at {req.crossing_name} right as ambulance reaches in {amb_eta_mins} mins. Severe bottleneck expected!"
        rec_alternate = True
        add_eta = 3
    elif abs(amb_eta_mins - train_eta_mins) <= 7:
        risk_level = "MODERATE"
        risk_score = 0.55
        explanation = f"Train approaching in {train_eta_mins} mins. Ambulance ETA is {amb_eta_mins} mins. Slight delay possible if train speed decelerates."
        rec_alternate = False
        add_eta = 2
    else:
        risk_level = "LOW"
        risk_score = 0.15
        explanation = f"Crossing clear. Ambulance ETA is {amb_eta_mins} mins, train ETA is {train_eta_mins} mins. Gate open for safe passage."
        rec_alternate = False
        add_eta = 0

    return RouteEvaluateResponse(
        crossing_name=req.crossing_name,
        ambulance_eta_mins=amb_eta_mins,
        train_eta_mins=train_eta_mins,
        crossing_risk_level=risk_level,
        risk_score=risk_score,
        risk_explanation=explanation,
        alternate_route_recommended=rec_alternate,
        alternate_eta_addition_mins=add_eta
    )
