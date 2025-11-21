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
            @RequestHeader Map<String, String> headers) throws Exception {
        
        // Loguear todos los headers recibidos
        System.out.println("========== HEADERS RECIBIDOS ==========");
        headers.forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
        System.out.println("=======================================");
        
        // Loguear el body recibido
        System.out.println("========== BODY RECIBIDO ==========");
        System.out.println(objectMapper.writeValueAsString(json));
        System.out.println("===================================");
        
        // Obtener replyAddress del JSON
        String replyAddress = (String) json.get("replyAddress");
        
        System.out.println("Reply Address: " + (replyAddress != null ? replyAddress : "NINGUNA"));

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

        if (replyAddress != null && !replyAddress.isEmpty()) {
            // Enviar respuesta asíncrona después de 15 segundos
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(15000); // 15 segundos
                    restTemplate.postForEntity(replyAddress, respuestaJson, String.class);
                    System.out.println("Respuesta enviada a replyAddress: " + replyAddress);
                } catch (Exception e) {
                    System.err.println("Error enviando respuesta a replyAddress: " + e.getMessage());
                }
            });

            // Retornar respuesta inmediata indicando que se procesará
            Map<String, Object> respuestaInmediata = new LinkedHashMap<>();
            respuestaInmediata.put("status", "accepted");
            respuestaInmediata.put("message", "Request accepted. Response will be sent to replyAddress in 15 seconds");
            return ResponseEntity.accepted().body(objectMapper.writeValueAsString(respuestaInmediata));
        } else {
            // Si no hay replyAddress, comportamiento original (esperar y retornar)
            Thread.sleep(15000);
            return ResponseEntity.ok(respuestaJson);
        }
    }
}
