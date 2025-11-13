package com.example.alexandriaDummy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class DummyController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/procesar")
    public String procesar(@RequestBody Map<String, Object> json) throws Exception {
        // Obtener el packageId
        Object packageId = json.get("packageId");

        // Crear el JSON interno
        Map<String, Object> jsonInterno = new LinkedHashMap<>();
        jsonInterno.put("packageId", packageId);
        jsonInterno.put("respuesta", "respuesta del api");
        String jsonInternoString = objectMapper.writeValueAsString(jsonInterno);

        // Crear el wrapper con la clave "json"
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("json", jsonInternoString);

        // Esperar 12 segundos antes de enviar la respuesta
        Thread.sleep(12000);

        return objectMapper.writeValueAsString(respuesta);
    }
}
