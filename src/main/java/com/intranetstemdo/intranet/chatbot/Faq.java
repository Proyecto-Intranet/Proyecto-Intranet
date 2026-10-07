package com.intranetstemdo.intranet.chatbot;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "faq")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String pregunta;

    @NotBlank
    @Column(nullable = false, length = 4000)
    private String respuesta;

    @Column(name = "palabras_clave", length = 500)
    private String palabrasClave;

    @Column(length = 100)
    private String tema;

    @Column(nullable = false)
    private boolean activa = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public String getPalabrasClave() { return palabrasClave; }
    public void setPalabrasClave(String palabrasClave) { this.palabrasClave = palabrasClave; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
}