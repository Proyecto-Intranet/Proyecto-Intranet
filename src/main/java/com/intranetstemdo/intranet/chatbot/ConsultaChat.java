package com.intranetstemdo.intranet.chatbot;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consulta_chat")
public class ConsultaChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String texto;

    @Column(nullable = false)
    private boolean respondida;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    protected ConsultaChat() {
        // constructor sin argumentos que exige JPA
    }

    public ConsultaChat(String texto, boolean respondida) {
        this.texto = texto;
        this.respondida = respondida;
    }

    public Long getId() { return id; }
    public String getTexto() { return texto; }
    public boolean isRespondida() { return respondida; }
    public LocalDateTime getFecha() { return fecha; }
}
