package com.intranetstemdo.intranet.chatbot;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intranetstemdo.intranet.chatbot.dto.RespuestaChat;

import java.util.Optional;

@Service
public class ChatService {

    private static final double UMBRAL = 0.5;

    private static final String SIN_RESPUESTA =
            "No he encontrado una respuesta a tu duda. "
            + "Puedes escribir a soporte@empresa.com o llamar a la extensión 1234.";

    private final FaqRepository faqRepository;
    private final ConsultaChatRepository consultaRepository;
    private final BuscadorFaq buscador;

    public ChatService(FaqRepository faqRepository,
                       ConsultaChatRepository consultaRepository,
                       BuscadorFaq buscador) {
        this.faqRepository = faqRepository;
        this.consultaRepository = consultaRepository;
        this.buscador = buscador;
    }

    @Transactional
    public RespuestaChat responder(String consulta) {
        String texto = consulta.trim();

        Optional<BuscadorFaq.Resultado> resultado =
                buscador.mejor(texto, faqRepository.findByActivaTrue())
                        .filter(r -> r.score() >= UMBRAL);

        // Se registra siempre la consulta; las no respondidas indican qué FAQ faltan
        consultaRepository.save(new ConsultaChat(texto, resultado.isPresent()));

        return resultado
                .map(r -> new RespuestaChat(r.faq().getRespuesta(), true))
                .orElseGet(() -> new RespuestaChat(SIN_RESPUESTA, false));
    }
}
