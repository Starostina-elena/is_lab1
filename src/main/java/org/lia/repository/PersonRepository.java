package org.lia.repository;

import org.lia.models.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Iterable<Person> findByLocationId(Long locationId);

    @Query(value = "SELECT * FROM find_persons_by_name_substring(:search)", nativeQuery = true)
    Iterable<Person> findPersonsByNameSubstring(@Param("search") String search);

}
