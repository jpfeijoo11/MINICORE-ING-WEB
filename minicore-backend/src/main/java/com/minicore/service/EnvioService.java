package com.minicore.service;

import com.minicore.dto.ReporteRepartidorDTO;
import com.minicore.model.Envio;
import com.minicore.model.Repartidor;
import com.minicore.repository.EnvioRepository;
import com.minicore.repository.RepartidorRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SERVICE — contiene la lógica de negocio del reporte.
 *
 * Pasos:
 *  1. Obtener todos los repartidores registrados.
 *  2. Obtener los envíos dentro del rango de fechas.
 *  3. Agrupar los envíos por repartidor.
 *  4. Para cada repartidor calcular:
 *       costo total = Σ (peso_kg × tarifa_por_kg de la zona del envío)
 *  5. Devolver la lista de DTOs (incluye repartidores sin envíos).
 */
@Service
public class EnvioService {

    private final EnvioRepository       envioRepository;
    private final RepartidorRepository  repartidorRepository;

    public EnvioService(EnvioRepository envioRepository,
                        RepartidorRepository repartidorRepository) {
        this.envioRepository      = envioRepository;
        this.repartidorRepository = repartidorRepository;
    }

    /**
     * Genera el reporte de costos por repartidor para el rango dado.
     *
     * @param fechaInicio primer día del período (inclusive)
     * @param fechaFin    último día del período (inclusive)
     * @return lista de DTOs ordenada por nombre de repartidor
     */
    public List<ReporteRepartidorDTO> generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {

        // 1. Todos los repartidores (para incluir los que no tienen envíos)
        List<Repartidor> todosLosRepartidores = repartidorRepository.findAll();

        // 2. Envíos filtrados por rango de fechas
        List<Envio> enviosFiltrados = envioRepository.findEnviosByRangoDeFechas(fechaInicio, fechaFin);

        // 3. Agrupar envíos por id de repartidor
        Map<Integer, List<Envio>> enviosPorRepartidor = enviosFiltrados.stream()
                .collect(Collectors.groupingBy(e -> e.getRepartidor().getIdRepartidor()));

        // 4. Construir un DTO por cada repartidor
        List<ReporteRepartidorDTO> reporte = new ArrayList<>();

        for (Repartidor repartidor : todosLosRepartidores) {
            List<Envio> susEnvios = enviosPorRepartidor
                    .getOrDefault(repartidor.getIdRepartidor(), Collections.emptyList());

            long totalEnvios = susEnvios.size();

            BigDecimal totalKg    = BigDecimal.ZERO;
            BigDecimal costoTotal = BigDecimal.ZERO;
            Set<String> zonasSet  = new LinkedHashSet<>();

            for (Envio envio : susEnvios) {
                BigDecimal peso   = envio.getPesoKg();
                BigDecimal tarifa = envio.getZona().getTarifaPorKg();

                totalKg    = totalKg.add(peso);
                costoTotal = costoTotal.add(peso.multiply(tarifa));
                zonasSet.add(envio.getZona().getNombreZona());
            }

            reporte.add(new ReporteRepartidorDTO(
                    repartidor.getNombre(),
                    totalEnvios,
                    totalKg,
                    new ArrayList<>(zonasSet),
                    costoTotal
            ));
        }

        // Ordenar alfabéticamente por nombre
        reporte.sort(Comparator.comparing(ReporteRepartidorDTO::getNombre));
        return reporte;
    }
}
