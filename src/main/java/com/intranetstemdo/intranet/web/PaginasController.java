package com.intranetstemdo.intranet.web;

import com.intranetstemdo.intranet.chatbot.Faq;
import com.intranetstemdo.intranet.chatbot.FaqRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PaginasController {

    private final FaqRepository faqRepository;

    public PaginasController(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ---- Las 5 secciones de la nueva IA (de 14 pestañas a 6) ----

    @GetMapping("/sobre-stemdo")
    public String sobreStemdo() {
        return "secciones/sobre-stemdo";
    }

    @GetMapping("/info-util")
    public String infoUtil() {
        return "secciones/info-util";
    }

    @GetMapping("/crecer-beneficios")
    public String crecerBeneficios() {
        return "secciones/crecer-beneficios";
    }

    @GetMapping("/comunidad")
    public String comunidad() {
        return "secciones/comunidad";
    }

    @GetMapping("/bienestar")
    public String bienestar() {
        return "secciones/bienestar";
    }

    // ---- FAQs: alimentadas por la misma tabla que usa el chatbot ----

    @GetMapping("/faqs")
    public String faqs(@RequestParam(name = "q", required = false) String q, Model model) {
        List<Faq> todas = faqRepository.findByActivaTrueOrderByTemaAscPreguntaAsc();

        List<Faq> filtradas = todas;
        if (q != null && !q.isBlank()) {
            String texto = q.trim().toLowerCase();
            filtradas = todas.stream()
                    .filter(f -> f.getPregunta().toLowerCase().contains(texto)
                            || f.getRespuesta().toLowerCase().contains(texto)
                            || (f.getTema() != null && f.getTema().toLowerCase().contains(texto)))
                    .collect(Collectors.toList());
        }

        Map<String, List<Faq>> porTema = filtradas.stream()
                .collect(Collectors.groupingBy(
                        f -> f.getTema() == null ? "General" : f.getTema(),
                        LinkedHashMap::new,
                        Collectors.toList()));

        model.addAttribute("porTema", porTema);
        model.addAttribute("total", todas.size());
        model.addAttribute("totalFiltradas", filtradas.size());
        model.addAttribute("q", q == null ? "" : q);
        return "secciones/faqs";
    }
}
