package com.minicore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * MODEL — representa un envío realizado por un repartidor en una zona.
 * Contiene el peso del paquete y la fecha del envío.
 */
@Entity
@Table(name = "envio")
public class Envio {

    @Id
    @Column(name = "id_envio")
    private Integer idEnvio;

    /** FK → Repartidor */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_repartidor", nullable = false)
    private Repartidor repartidor;

    /** FK → Zona */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona", nullable = false)
    private Zona zona;

    @Column(name = "peso_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal pesoKg;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDate fechaEnvio;

    // ── Constructores ──────────────────────────────────────────
    public Envio() {}

    // ── Getters / Setters ──────────────────────────────────────
    public Integer getIdEnvio()               { return idEnvio; }
    public void    setIdEnvio(Integer id)     { this.idEnvio = id; }

    public Repartidor getRepartidor()                     { return repartidor; }
    public void       setRepartidor(Repartidor r)         { this.repartidor = r; }

    public Zona getZona()             { return zona; }
    public void setZona(Zona zona)    { this.zona = zona; }

    public BigDecimal getPesoKg()                  { return pesoKg; }
    public void       setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }

    public LocalDate getFechaEnvio()                    { return fechaEnvio; }
    public void      setFechaEnvio(LocalDate fechaEnvio){ this.fechaEnvio = fechaEnvio; }
}
