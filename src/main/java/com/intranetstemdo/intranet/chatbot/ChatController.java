package com.intranetstemdo.intranet.chatbot;

import com.intranetstemdo.intranet.chatbot.dto.PreguntaChat;
import com.intranetstemdo.intranet.chatbot.dto.RespuestaChat;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public RespuestaChat preguntar(@Valid @RequestBody PreguntaChat pregunta) {
        return chatService.responder(pregunta.texto());
    }
}