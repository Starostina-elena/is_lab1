package org.lia.repository;

import org.lia.models.dragon.Dragon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DragonRepository extends JpaRepository<Dragon, Long>, JpaSpecificationExecutor<Dragon> {
    Iterable<Dragon> findByCoordinatesId(Long coordinatesId);
    Iterable<Dragon> findByCaveId(Long caveId);
    Iterable<Dragon> findByKillerId(Long killerId);
    Iterable<Dragon> findByHeadId(Long headId);

    @Query(value = "SELECT avg_dragon_age()", nativeQuery = true)
    Double getAverageAge();

    @Query(value = "SELECT count_dragons_with_head_less_than(:maxId)", nativeQuery = true)
    int countDragonsWithHeadLessThan(@Param("maxId") int maxId);
}
