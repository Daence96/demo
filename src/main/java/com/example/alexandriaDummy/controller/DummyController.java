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
        // Obtener el packageId
        Object packageId = json.get("packageId");

        // Crear nuevo mapa para la respuesta interna
        Map<String, Object> respuestaInterna = new LinkedHashMap<>();
        respuestaInterna.put("packageId", packageId);
        respuestaInterna.put("respuesta", "respuesta del api");

        // Convertir esa respuesta interna a JSON string
        String jsonString = objectMapper.writeValueAsString(respuestaInterna);

        // Crear el resultado final con clave "json"
        Map<String, Object> respuestaFinal = new LinkedHashMap<>();
        respuestaFinal.put("json", jsonString);

        return respuestaFinal;
    }
}
