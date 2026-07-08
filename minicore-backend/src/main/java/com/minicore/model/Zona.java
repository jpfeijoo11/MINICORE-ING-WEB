package com.minicore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * MODEL — representa una zona geográfica de entrega.
 * Contiene la tarifa fija por kilogramo que se aplica en esa zona.
 */
@Entity
@Table(name = "zona")
public class Zona {

    @Id
    @Column(name = "id_zona")
    private Integer idZona;

    @Column(name = "nombre_zona", nullable = false)
    private String nombreZona;

    @Column(name = "tarifa_por_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifaPorKg;

    // ── Constructores ──────────────────────────────────────────
    public Zona() {}

    public Zona(Integer idZona, String nombreZona, BigDecimal tarifaPorKg) {
        this.idZona     = idZona;
        this.nombreZona = nombreZona;
        this.tarifaPorKg = tarifaPorKg;
    }

    // ── Getters / Setters ──────────────────────────────────────
    public Integer getIdZona()               { return idZona; }
    public void    setIdZona(Integer idZona) { this.idZona = idZona; }

    public String getNombreZona()                { return nombreZona; }
    public void   setNombreZona(String nombre)   { this.nombreZona = nombre; }

    public BigDecimal getTarifaPorKg()                   { return tarifaPorKg; }
    public void       setTarifaPorKg(BigDecimal tarifa)  { this.tarifaPorKg = tarifa; }
}
