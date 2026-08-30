from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.api.endpoints import router as api_router

app = FastAPI(
    title="AmbuRoute AI Intelligence Service",
    description="Python FastAPI service providing AI Hospital Recommendations, Railway Crossing Risk Matrix Evaluation, and Emergency Symptom Triage.",
    version="1.0.0"
)

# Enable CORS for frontend and Spring Boot backend
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(api_router)

@app.get("/")
def root():
    return {
        "service": "AmbuRoute AI Service",
        "status": "ONLINE",
        "version": "1.0.0",
        "docs": "/docs"
    }

@app.get("/health")
def health_check():
    return {"status": "UP", "ai_models": "READY"}
