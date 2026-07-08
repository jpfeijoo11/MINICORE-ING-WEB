package com.minicore.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO — datos que el Controller devuelve al frontend para cada repartidor.
 *
 * Campos:
 *  - nombre        : nombre del repartidor
 *  - totalEnvios   : cantidad de envíos en el período
 *  - totalKg       : suma de pesos en kg
 *  - zonas         : lista de zonas distintas utilizadas
 *  - costoTotal    : suma de (peso_kg × tarifa_por_kg) por cada envío
 */
public class ReporteRepartidorDTO {

    private String          nombre;
    private long            totalEnvios;
    private BigDecimal      totalKg;
    private List<String>    zonas;
    private BigDecimal      costoTotal;

    public ReporteRepartidorDTO() {}

    public ReporteRepartidorDTO(String nombre,
                                 long totalEnvios,
                                 BigDecimal totalKg,
                                 List<String> zonas,
                                 BigDecimal costoTotal) {
        this.nombre      = nombre;
        this.totalEnvios = totalEnvios;
        this.totalKg     = totalKg;
        this.zonas       = zonas;
        this.costoTotal  = costoTotal;
    }

    // ── Getters / Setters ──────────────────────────────────────
    public String getNombre()               { return nombre; }
    public void   setNombre(String nombre)  { this.nombre = nombre; }

    public long   getTotalEnvios()               { return totalEnvios; }
    public void   setTotalEnvios(long count)     { this.totalEnvios = count; }

    public BigDecimal getTotalKg()                   { return totalKg; }
    public void       setTotalKg(BigDecimal totalKg) { this.totalKg = totalKg; }

    public List<String> getZonas()                { return zonas; }
    public void         setZonas(List<String> z)  { this.zonas = z; }

    public BigDecimal getCostoTotal()                     { return costoTotal; }
    public void       setCostoTotal(BigDecimal costoTotal){ this.costoTotal = costoTotal; }
}
