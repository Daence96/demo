package com.example.alexandriaDummy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class DummyController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value = "/procesar", consumes = {"application/json", "text/plain"})
    public Map<String, Object> procesar(@RequestBody Map<String, Object> json) {
        Map<String, Object> respuestaFinal = new LinkedHashMap<>();

        try {
            // Extraer el campo "body" (que a su vez contiene el packageId)
            Object bodyObj = json.get("body");

            if (bodyObj instanceof Map) {
                Map<String, Object> body = (Map<String, Object>) bodyObj;
                Object packageId = body.get("packageId");

                // Crear respuesta interna
                Map<String, Object> respuestaInterna = new LinkedHashMap<>();
                respuestaInterna.put("packageId", packageId);
                respuestaInterna.put("respuesta", "respuesta del api");

                // Convertir a JSON string
                String jsonString = objectMapper.writeValueAsString(respuestaInterna);

                respuestaFinal.put("json", jsonString);
            } else {
                respuestaFinal.put("error", "No se encontró un objeto 'body' válido en el JSON recibido");
                respuestaFinal.put("bodyRecibido", json);
            }
        } catch (Exception e) {
            respuestaFinal.put("error", "Error procesando JSON");
            respuestaFinal.put("detalle", e.getMessage());
            respuestaFinal.put("jsonRecibido", json);
        }

        return respuestaFinal;
    }
}
