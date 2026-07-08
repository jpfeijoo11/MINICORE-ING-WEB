package com.minicore.repository;

import com.minicore.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * REPOSITORY — acceso a datos de Envio.
 *
 * La consulta JPQL obtiene todos los envíos cuya fecha_envio
 * está dentro del rango [fechaInicio, fechaFin] (inclusive).
 * Se usa JOIN FETCH para traer Repartidor y Zona en una sola query
 * y evitar el problema N+1.
 */
@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {

    @Query("""
        SELECT e FROM Envio e
        JOIN FETCH e.repartidor r
        JOIN FETCH e.zona z
        WHERE e.fechaEnvio BETWEEN :fechaInicio AND :fechaFin
        ORDER BY r.nombre
        """)
    List<Envio> findEnviosByRangoDeFechas(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin")    LocalDate fechaFin
    );
}
