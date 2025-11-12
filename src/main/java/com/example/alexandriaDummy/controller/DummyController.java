package com.example.alexandriaDummy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class DummyController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/procesar")
    public Map<String, Object> procesar(@RequestBody Map<String, Object> json) throws Exception {
        // Verifica que exista el campo "body"
        if (!json.containsKey("body")) {
            throw new IllegalArgumentException("El JSON debe contener la clave 'body'");
        }

        // Extrae el cuerpo (body)
        Map<String, Object> body = (Map<String, Object>) json.get("body");

        // Obtiene el packageId desde el body
        Object packageId = body.get("packageId");

        // Crea un mapa para la respuesta interna
        Map<String, Object> respuestaInterna = new LinkedHashMap<>();
        respuestaInterna.put("packageId", packageId);
        respuestaInterna.put("respuesta", "respuesta del api");

        // Convierte la respuesta interna a JSON string
        String jsonString = objectMapper.writeValueAsString(respuestaInterna);

        // Crea el resultado final con la clave "json"
        Map<String, Object> respuestaFinal = new LinkedHashMap<>();
        respuestaFinal.put("json", jsonString);

        return respuestaFinal;
    }
}
