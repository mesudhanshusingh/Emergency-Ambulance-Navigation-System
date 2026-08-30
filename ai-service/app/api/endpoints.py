from fastapi import APIRouter
from app.models.schemas import (
    RecommendationRequest, RecommendationResponse,
    RouteEvaluateRequest, RouteEvaluateResponse,
    ChatMessageRequest, ChatMessageResponse
)
from app.services.hospital_recommender import calculate_hospital_matches
from app.services.route_optimizer import evaluate_railway_crossing_risk
from app.services.triage_assistant import process_triage_message

router = APIRouter()

@router.post("/ai/hospital-recommendation", response_model=RecommendationResponse)
def recommend_hospitals(req: RecommendationRequest):
    return calculate_hospital_matches(req)

@router.post("/ai/route-score", response_model=RouteEvaluateResponse)
def evaluate_route_risk(req: RouteEvaluateRequest):
    return evaluate_railway_crossing_risk(req)

@router.post("/ai/chat-triage", response_model=ChatMessageResponse)
def chat_triage(req: ChatMessageRequest):
    return process_triage_message(req)
