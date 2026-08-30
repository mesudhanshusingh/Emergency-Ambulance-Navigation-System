package com.amburoute.controller;

import com.amburoute.dto.ChatDTOs;
import com.amburoute.service.AiIntegrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatbotController {

    private final AiIntegrationService aiIntegrationService;

    public ChatbotController(AiIntegrationService aiIntegrationService) {
        this.aiIntegrationService = aiIntegrationService;
    }

    @PostMapping("/message")
    public ResponseEntity<ChatDTOs.ChatMessageResponse> processChatMessage(@RequestBody ChatDTOs.ChatMessageRequest request) {
        return ResponseEntity.ok(aiIntegrationService.getAiChatResponse(request.getMessage(), request.getContextEmergencyType()));
    }
}
