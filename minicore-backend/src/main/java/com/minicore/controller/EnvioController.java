package com.minicore.controller;

import com.minicore.dto.ReporteRepartidorDTO;
import com.minicore.service.EnvioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * CONTROLLER — expone el endpoint REST que el frontend Angular consume.
 *
 * Ruta: GET /api/envios/reporte?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD
 *
 * La configuración CORS (qué orígenes pueden llamar a este backend) vive
 * en {@link com.minicore.config.CorsConfig}, así funciona tanto en local
 * (http://localhost:4200) como en producción (Render).
 */
@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    /**
     * Devuelve el reporte de costos por repartidor en el rango de fechas.
     *
     * @param fechaInicio fecha de inicio en formato ISO (YYYY-MM-DD)
     * @param fechaFin    fecha de fin en formato ISO (YYYY-MM-DD)
     * @return lista JSON de ReporteRepartidorDTO
     */
    @GetMapping("/reporte")
    public ResponseEntity<List<ReporteRepartidorDTO>> getReporte(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        if (fechaFin.isBefore(fechaInicio)) {
            return ResponseEntity.badRequest().build();
        }

        List<ReporteRepartidorDTO> reporte = envioService.generarReporte(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
