package com.intranetstemdo.intranet.chatbot;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BuscadorFaq {

    public record Resultado(Faq faq, double score) {}

    private static final int LONGITUD_RAIZ = 7;

    private static final Set<String> PALABRAS_VACIAS = Set.of(
        "de", "la", "el", "en", "y", "a", "los", "las", "del", "se", "que", "por",
        "un", "una", "con", "para", "es", "al", "lo", "como", "mas", "pero", "su",
        "sus", "le", "ya", "o", "este", "si", "me", "mi", "mis", "hay", "donde",
        "cuando", "quiero", "puedo", "necesito", "hacer", "ser");

    public Optional<Resultado> mejor(String consulta, List<Faq> faqs) {
        Set<String> palabrasConsulta = tokens(consulta);
        if (palabrasConsulta.isEmpty() || faqs.isEmpty()) {
            return Optional.empty();
        }

        // Palabras de cada FAQ (pregunta + palabras clave) y en cuántas FAQ aparece cada palabra
        Map<Faq, Set<String>> palabrasPorFaq = new LinkedHashMap<>();
        Map<String, Integer> frecuenciaDocumento = new HashMap<>();
        for (Faq faq : faqs) {
            String texto = faq.getPregunta() + " "
                    + (faq.getPalabrasClave() == null ? "" : faq.getPalabrasClave());
            Set<String> palabras = tokens(texto);
            palabrasPorFaq.put(faq, palabras);
            palabras.forEach(p -> frecuenciaDocumento.merge(p, 1, Integer::sum));
        }

        // Solo cuentan las palabras de la consulta que existen en alguna FAQ
        Set<String> conocidas = palabrasConsulta.stream()
                .filter(frecuenciaDocumento::containsKey)
                .collect(Collectors.toSet());
        if (conocidas.isEmpty()) {
            return Optional.empty();
        }

        int totalFaqs = faqs.size();
        double pesoTotal = conocidas.stream()
                .mapToDouble(p -> peso(totalFaqs, frecuenciaDocumento.get(p)))
                .sum();

        Resultado mejor = null;
        for (Map.Entry<Faq, Set<String>> entrada : palabrasPorFaq.entrySet()) {
            double pesoCoincidente = conocidas.stream()
                    .filter(entrada.getValue()::contains)
                    .mapToDouble(p -> peso(totalFaqs, frecuenciaDocumento.get(p)))
                    .sum();
            double score = pesoCoincidente / pesoTotal;
            if (mejor == null || score > mejor.score()) {
                mejor = new Resultado(entrada.getKey(), score);
            }
        }
        return Optional.ofNullable(mejor);
    }

    // Las palabras raras (que aparecen en pocas FAQ) valen más que las comunes
    private static double peso(int totalFaqs, int frecuencia) {
        return Math.log(1.0 + (double) totalFaqs / frecuencia);
    }

    static Set<String> tokens(String texto) {
        String limpio = Normalizer.normalize(texto.toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")          // quita tildes (y la ~ de la ñ)
                .replaceAll("[^a-z0-9 ]", " ");    // quita signos de puntuación
        return Arrays.stream(limpio.split("\\s+"))
                .filter(t -> t.length() > 1 && !PALABRAS_VACIAS.contains(t))
                .map(t -> t.length() > LONGITUD_RAIZ ? t.substring(0, LONGITUD_RAIZ) : t)
                .collect(Collectors.toSet());
    }
}
