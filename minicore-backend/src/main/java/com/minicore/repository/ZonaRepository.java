package com.minicore.repository;

import com.minicore.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORY — acceso a datos de Zona.
 */
@Repository
public interface ZonaRepository extends JpaRepository<Zona, Integer> {
}
