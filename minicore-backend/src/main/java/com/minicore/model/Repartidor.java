package com.minicore.model;

import jakarta.persistence.*;

/**
 * MODEL — representa a un repartidor de la empresa.
 */
@Entity
@Table(name = "repartidor")
public class Repartidor {

    @Id
    @Column(name = "id_repartidor")
    private Integer idRepartidor;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email")
    private String email;

    // ── Constructores ──────────────────────────────────────────
    public Repartidor() {}

    public Repartidor(Integer idRepartidor, String nombre, String email) {
        this.idRepartidor = idRepartidor;
        this.nombre       = nombre;
        this.email        = email;
    }

    // ── Getters / Setters ──────────────────────────────────────
    public Integer getIdRepartidor()                    { return idRepartidor; }
    public void    setIdRepartidor(Integer id)          { this.idRepartidor = id; }

    public String getNombre()               { return nombre; }
    public void   setNombre(String nombre)  { this.nombre = nombre; }

    public String getEmail()              { return email; }
    public void   setEmail(String email)  { this.email = email; }
}
