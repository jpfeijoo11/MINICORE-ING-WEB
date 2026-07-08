package com.minicore.repository;

import com.minicore.model.Repartidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORY — acceso a datos de Repartidor.
 * Spring Data JPA genera la implementación automáticamente.
 */
@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Integer> {
}
