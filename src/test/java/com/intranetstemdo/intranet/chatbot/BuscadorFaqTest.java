package com.intranetstemdo.intranet.chatbot;

import org.junit.jupiter.api.Test;

import com.intranetstemdo.intranet.chatbot.BuscadorFaq;
import com.intranetstemdo.intranet.chatbot.Faq;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BuscadorFaqTest {

    private final BuscadorFaq buscador = new BuscadorFaq();

    private Faq faq(String pregunta, String palabrasClave) {
        Faq faq = new Faq();
        faq.setPregunta(pregunta);
        faq.setRespuesta("respuesta");
        faq.setPalabrasClave(palabrasClave);
        return faq;
    }

    private final List<Faq> faqs = List.of(
            faq("¿Cómo solicito vacaciones?", "vacaciones días libres permiso ausencia"),
            faq("¿Cómo restablezco mi contraseña?", "contraseña clave password acceso olvidé"));

    @Test
    void encuentraLaFaqPorSinonimo() {
        var resultado = buscador.mejor("quiero pedir días libres", faqs);

        assertTrue(resultado.isPresent());
        assertEquals("¿Cómo solicito vacaciones?", resultado.get().faq().getPregunta());
    }

    @Test
    void ignoraTildesYMayusculas() {
        var resultado = buscador.mejor("OLVIDÉ MI CONTRASEÑA", faqs);

        assertTrue(resultado.isPresent());
        assertEquals("¿Cómo restablezco mi contraseña?", resultado.get().faq().getPregunta());
    }

    @Test
    void noDevuelveNadaSiNoHayCoincidencia() {
        assertTrue(buscador.mejor("receta de paella", faqs).isEmpty());
    }

    @Test
    void noFallaConConsultasVacias() {
        assertTrue(buscador.mejor("   ", faqs).isEmpty());
        assertTrue(buscador.mejor("la de el", faqs).isEmpty());
    }
}
