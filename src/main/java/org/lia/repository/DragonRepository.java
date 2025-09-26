package org.lia.repository;

import org.lia.models.dragon.Dragon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DragonRepository extends JpaRepository<Dragon, Long> {
}
