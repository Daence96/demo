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

        // Crear el JSON interno más profundo
        Map<String, Object> jsonInterno = new LinkedHashMap<>();
        jsonInterno.put("packageId", packageId);
        jsonInterno.put("respuesta", "respuesta del api");
        String jsonInternoString = objectMapper.writeValueAsString(jsonInterno);

        // Crear el body con la clave "json"
        Map<String, Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("json", jsonInternoString);
        String bodyString = objectMapper.writeValueAsString(bodyMap);

        // Crear el header
        Map<String, String> header = new LinkedHashMap<>();
        header.put("name", "Authorization");
        header.put("value", "Bearer 5bb4a4026f8f1869f8d4610a5cec17befad592a312948becf677b1222059acf5");

        // Crear la respuesta completa
        Map<String, Object> respuestaInterna = new LinkedHashMap<>();
        respuestaInterna.put("parameterQuery", new ArrayList<>());
        respuestaInterna.put("parameterHeader", Arrays.asList(header));
        respuestaInterna.put("body", bodyString);

        // Esperar 5 segundos antes de enviar la respuesta
        Thread.sleep(12000);

        return objectMapper.writeValueAsString(respuestaInterna);
    }
}
