package org.lia.repository;

import org.lia.models.utils.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    @Query(value = "SELECT * FROM find_locations_by_name_substring(:search)", nativeQuery = true)
    Iterable<Location> findLocationsByNameSubstring(@Param("search") String search);

}
