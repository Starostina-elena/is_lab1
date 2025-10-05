package org.lia.repository;

import org.lia.models.dragon.Dragon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DragonRepository extends JpaRepository<Dragon, Long> {
    Iterable<Dragon> findByCoordinatesId(Long coordinatesId);
    Iterable<Dragon> findByCaveId(Long caveId);
    Iterable<Dragon> findByKillerId(Long killerId);
    Iterable<Dragon> findByHeadId(Long headId);
}
