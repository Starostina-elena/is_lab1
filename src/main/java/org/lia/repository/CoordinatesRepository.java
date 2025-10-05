package org.lia.repository;

import org.lia.models.utils.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
}
