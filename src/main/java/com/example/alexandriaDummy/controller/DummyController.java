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
        // Obtener el packageId del JSON
        Object packageId = json.get("packageId");

        // Crear el XML interno
        String xmlInterno = String.format(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xml><packageId>%s</packageId><respuesta>respuesta del api</respuesta></xml>",
            packageId
        );

        // Crear la respuesta con correlationId y response
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("correlationId", "");
        respuesta.put("response", xmlInterno);

        // Esperar 12 segundos antes de enviar la respuesta
        Thread.sleep(12000);

        return objectMapper.writeValueAsString(respuesta);
    }
}
