package com.example.arqs4;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/figuras")
public class FiguraController {

    private final FiguraFactoryService figuraFactoryService;
    private final RestTemplate restTemplate = new RestTemplate();

    public FiguraController(FiguraFactoryService figuraFactoryService) {
        this.figuraFactoryService = figuraFactoryService;
    }

    @PostMapping(value = "/compilar", consumes = {"text/plain", "*/*"})
    public ResponseEntity<String> compilarFiguraGroovy(@RequestBody String codigoGroovy) {
        try {
            figuraFactoryService.registrarFiguraGroovy(codigoGroovy);
            return ResponseEntity.ok("Clase Groovy compilada y registrada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al compilar Groovy: " + e.getMessage());
        }
    }

    @PostMapping("/consumir-externo")
    public ResponseEntity<String> consumirFiguraExterna(@RequestParam String url) {
        try {
            String codigoGroovy = restTemplate.getForObject(url, String.class);
            figuraFactoryService.registrarFiguraGroovy(codigoGroovy);
            return ResponseEntity.ok("Código obtenido del servicio externo y compilado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al consumir el servicio externo: " + e.getMessage());
        }
    }

    @PostMapping("/crear/{nombre}")
    public ResponseEntity<?> crearFigura(@PathVariable String nombre, @RequestBody List<Object> params) {
        try {
            // Conversión a Float para coincidir con las clases nativas y dinámicas
            Object[] args = params.stream().map(p -> {
                if (p instanceof Number) return ((Number) p).floatValue();
                return p;
            }).toArray();

            Figura figura = figuraFactoryService.instanciarFigura(nombre, args);

            return ResponseEntity.ok(Map.of(
                    "tipo", nombre,
                    "area", figura.calcularArea(),
                    "perimetro", figura.calcularPerimetro()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al instanciar la figura: " + e.getMessage());
        }
    }
}