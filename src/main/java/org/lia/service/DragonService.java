package org.lia.service;

import org.lia.models.dragon.Dragon;
import org.lia.models.dragon.DragonCave;
import org.lia.models.dragon.DragonHead;
import org.lia.models.person.Person;
import org.lia.models.utils.Coordinates;
import org.lia.models.utils.DragonCharacter;
import org.lia.models.utils.DragonType;
import org.lia.repository.DragonRepository;
import org.lia.models.utils.Color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DragonService {
    @Autowired
    private DragonRepository dragonRepository;

    public Dragon saveDragon(String name, Coordinates coordinates,
                           DragonCave cave, Person killer, long age, Color color, DragonType type,
                           DragonCharacter character, DragonHead head) {
        Dragon dragon = new Dragon();
        dragon.setName(name);
        dragon.setCoordinates(coordinates);
        dragon.setCave(cave);
        dragon.setKiller(killer);
        dragon.setAge(age);
        dragon.setColor(color);
        dragon.setType(type);
        dragon.setCharacter(character);
        dragon.setHead(head);
        dragonRepository.save(dragon);
        return dragon;
    }

    public Dragon saveDragon(Dragon dragon) {
        return dragonRepository.save(dragon);
    }

    public Page<Dragon> getDragonsPaged(Pageable pageable) {
        return dragonRepository.findAll(pageable);
    }

    public long count() {
        return dragonRepository.count();
    }

    public Dragon findById(Long id) {
        return dragonRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        dragonRepository.deleteById(id);
    }

    public Iterable<Dragon> findByCoordinatesId(Long coordinatesId) {
        return dragonRepository.findByCoordinatesId(coordinatesId);
    }

    public Iterable<Dragon> findByCaveId(Long caveId) {
        return dragonRepository.findByCaveId(caveId);
    }

    public Iterable<Dragon> findByKillerId(Long killerId) {
        return dragonRepository.findByKillerId(killerId);
    }

    public Iterable<Dragon> findByHeadId(Long headId) {
        return dragonRepository.findByHeadId(headId);
    }

    public Page<Dragon> getDragonsPagedAndFiltered(Pageable pageable, String filter) {
        Specification<Dragon> spec = Specification.where(null);
        if (filter != null && !filter.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + filter.toLowerCase() + "%"));
        }
        return dragonRepository.findAll(spec, pageable);
    }

    public long countFiltered(String filter) {
        Specification<Dragon> spec = Specification.where(null);
        if (filter != null && !filter.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + filter.toLowerCase() + "%"));
        }
        return dragonRepository.count(spec);
    }

    public Double getAverageAge() {
        return dragonRepository.getAverageAge();
    }

    public int countDragonsWithHeadLessThan(int maxId) {
        return dragonRepository.countDragonsWithHeadLessThan(maxId);
    }

    public Iterable<Dragon> findDragonsByNameSubstring(String search) {
        return dragonRepository.findDragonsByNameSubstring(search);
    }

}
