package org.lia.repository;

import org.lia.models.dragon.Dragon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface DragonRepository extends JpaRepository<Dragon, Long>, JpaSpecificationExecutor<Dragon> {
    Iterable<Dragon> findByCoordinatesId(Long coordinatesId);
    Iterable<Dragon> findByCaveId(Long caveId);
    Iterable<Dragon> findByKillerId(Long killerId);
    Iterable<Dragon> findByHeadId(Long headId);

    @Query(value = "SELECT avg_dragon_age()", nativeQuery = true)
    Double getAverageAge();
}
