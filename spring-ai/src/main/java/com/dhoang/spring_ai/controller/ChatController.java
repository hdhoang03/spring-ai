package com.dhoang.spring_ai.controller;

import com.dhoang.spring_ai.dto.ChatRequest;
import com.dhoang.spring_ai.service.ChatService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    String chat(@RequestBody ChatRequest request){
        return chatService.chat(request);
    }

    @PostMapping("/chat-with-image")
    String ChatWithImage(@RequestParam("file") MultipartFile file,
                         @RequestParam("message") String message){
        return chatService.chatWithImage(file, message);
    }
}