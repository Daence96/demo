package com.example.alexandriaDummy.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class DummyController {

    @PostMapping(value = "/procesar", produces = "application/xml")
    public String procesar(@RequestBody Map<String, Object> json) throws InterruptedException {
        // Obtener el packageId del JSON
        Object packageId = json.get("packageId");

        // Crear la respuesta XML
        String respuestaXml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <xml>
              <packageId>%s</packageId>
              <respuesta>respuesta del api</respuesta>
            </xml>""".formatted(packageId);

        // Esperar 12 segundos antes de enviar la respuesta
        Thread.sleep(12000);

        return respuestaXml;
    }
}
