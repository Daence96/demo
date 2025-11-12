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
        Object bodyRaw = json.get("body");

        if (bodyRaw == null) {
            throw new IllegalArgumentException("El JSON debe contener la clave 'body'");
        }

        Map<String, Object> body;

        // Si el body llega como JSON string (ej: "{ \"packageId\": \"123\" }"), parsearlo
        if (bodyRaw instanceof String) {
            body = objectMapper.readValue((String) bodyRaw, Map.class);
        }
        // Si ya es un mapa, usarlo directamente
        else if (bodyRaw instanceof Map) {
            body = (Map<String, Object>) bodyRaw;
        } else {
            throw new IllegalArgumentException("Formato de 'body' no reconocido");
        }

        // Obtener el packageId
        Object packageId = body.get("packageId");

        // Crear respuesta interna
        Map<String, Object> respuestaInterna = new LinkedHashMap<>();
        respuestaInterna.put("packageId", packageId);
        respuestaInterna.put("respuesta", "respuesta del api");

        // Convertir la respuesta a string
        String jsonString = objectMapper.writeValueAsString(respuestaInterna);

        // Devolver la estructura esperada
        Map<String, Object> respuestaFinal = new LinkedHashMap<>();
        respuestaFinal.put("json", jsonString);

        return respuestaFinal;
    }
}
