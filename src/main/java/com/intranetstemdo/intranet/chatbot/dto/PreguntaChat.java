package com.intranetstemdo.intranet.chatbot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PreguntaChat(
        @NotBlank @Size(max = 500) String texto
) {}