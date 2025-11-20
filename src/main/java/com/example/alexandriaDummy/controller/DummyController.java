package com.example.alexandriaDummy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class DummyController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/procesar")
    public ResponseEntity<String> procesar(
            @RequestBody Map<String, Object> json,
            @RequestHeader(value = "url_callback", required = false) String urlCallback,
            @RequestHeader(value = "callback_url", required = false) String callbackUrl,
            @RequestHeader(value = "callbackUrl", required = false) String callbackUrlAlt) throws Exception {
        
        // Determinar cual header de callback está presente
        String finalCallbackUrl = urlCallback != null ? urlCallback : 
                                  callbackUrl != null ? callbackUrl : 
                                  callbackUrlAlt;

        // Obtener el packageId y correlationId del JSON
        Object packageId = json.get("packageId");
        Object correlationId = json.get("correlationId");

        // Crear el XML interno
        String xmlInterno = String.format(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xml><packageId>%s</packageId><respuesta>respuesta del api</respuesta></xml>",
            packageId
        );

        // Crear la respuesta con correlationId y response
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("correlationId", correlationId != null ? correlationId : "");
        respuesta.put("response", xmlInterno);

        String respuestaJson = objectMapper.writeValueAsString(respuesta);

        if (finalCallbackUrl != null && !finalCallbackUrl.isEmpty()) {
            // Enviar respuesta asíncrona después de 15 segundos
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(15000); // 15 segundos
                    restTemplate.postForEntity(finalCallbackUrl, respuestaJson, String.class);
                    System.out.println("Respuesta enviada a callback URL: " + finalCallbackUrl);
                } catch (Exception e) {
                    System.err.println("Error enviando callback: " + e.getMessage());
                }
            });

            // Retornar respuesta inmediata indicando que se procesará
            Map<String, Object> respuestaInmediata = new LinkedHashMap<>();
            respuestaInmediata.put("status", "accepted");
            respuestaInmediata.put("message", "Request accepted. Response will be sent to callback URL in 15 seconds");
            return ResponseEntity.accepted().body(objectMapper.writeValueAsString(respuestaInmediata));
        } else {
            // Si no hay callback URL, comportamiento original (esperar y retornar)
            Thread.sleep(15000);
            return ResponseEntity.ok(respuestaJson);
        }
    }
}
