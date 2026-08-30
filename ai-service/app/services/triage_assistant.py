from app.models.schemas import ChatMessageRequest, ChatMessageResponse

def process_triage_message(req: ChatMessageRequest) -> ChatMessageResponse:
    text = req.message.lower()
    
    # Keyword detection engine for emergency classification
    cardiac_keywords = ["chest pain", "heart", "cardiac", "stroke", "arm pain", "shortness of breath", "sweating"]
    trauma_keywords = ["accident", "bleed", "fracture", "fall", "head injury", "trauma", "cut", "wound"]
    respiratory_keywords = ["asthma", "breathing", "choking", "oxygen", "gasping", "wheezing"]
    pregnancy_keywords = ["labor", "pregnant", "contractions", "water broke", "maternity"]
    
    detected_type = "OTHER"
    suggested_crit = "MODERATE"
    rec_activate = False
    
    if any(k in text for k in cardiac_keywords):
        detected_type = "CARDIAC"
        suggested_crit = "CRITICAL"
        rec_activate = True
        reply = (
            "🚨 CRITICAL WARNING: Chest pain, shortness of breath, or radiating arm discomfort may indicate a severe cardiac event or stroke.\n\n"
            "Recommended Immediate Steps:\n"
            "1. Activate AmbuRoute Emergency immediately using the red 🚨 button above.\n"
            "2. Keep the patient seated or semi-reclined and calm.\n"
            "3. Do NOT give heavy foods or drinks.\n"
            "4. Stay on the line with local 108/911 emergency dispatchers."
        )
    elif any(k in text for k in trauma_keywords):
        detected_type = "ACCIDENT_TRAUMA"
        suggested_crit = "HIGH"
        rec_activate = True
        reply = (
            "⚠️ HIGH URGENCY: Trauma or severe bleeding requires immediate emergency navigation to a verified Trauma Center.\n\n"
            "Recommended First Aid Steps:\n"
            "1. Apply firm, steady pressure with a clean cloth to any active bleeding site.\n"
            "2. Do NOT move the patient if neck or spinal injury is suspected.\n"
            "3. Click 🚨 ACTIVATE EMERGENCY to dispatch nearest available ambulance."
        )
    elif any(k in text for k in respiratory_keywords):
        detected_type = "RESPIRATORY"
        suggested_crit = "HIGH"
        rec_activate = True
        reply = (
            "⚠️ RESPIRATORY ALERT: Acute breathing distress requires rapid oxygen support and ICU availability.\n\n"
            "Recommended Actions:\n"
            "1. Sit the patient upright and loosen tight clothing around the neck.\n"
            "2. Activate Emergency mode now so our algorithm matches hospitals with active ventilator capacity."
        )
    elif any(k in text for k in pregnancy_keywords):
        detected_type = "PREGNANCY"
        suggested_crit = "HIGH"
        rec_activate = True
        reply = (
            "🏥 MATERNITY EMERGENCY: Labor contractions or emergency obstetric distress detected.\n\n"
            "AmbuRoute will recommend top rated maternity hospitals with available NICU/Emergency beds."
        )
    else:
        reply = (
            "Hello, I am the AmbuRoute Emergency Assistant.\n\n"
            "Please describe the patient's symptoms (e.g., 'severe chest pain', 'road accident', 'difficulty breathing').\n"
            "If this is a life-threatening crisis, please click 🚨 ACTIVATE EMERGENCY immediately!"
        )

    return ChatMessageResponse(
        reply=reply,
        detected_emergency_type=detected_type,
        suggested_criticality=suggested_crit,
        recommend_emergency_activation=rec_activate,
        medical_disclaimer="⚠️ Disclaimer: AmbuRoute AI Assistant provides decision-support information only and does NOT replace professional medical diagnosis or qualified emergency services."
    )
