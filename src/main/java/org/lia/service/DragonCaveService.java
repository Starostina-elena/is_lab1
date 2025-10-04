package org.lia.service;

import org.lia.models.dragon.DragonCave;
import org.lia.models.person.Person;
import org.lia.repository.DragonCaveRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DragonCaveService {
    @Autowired
    private DragonCaveRepository dragonCaveRepository;

    public DragonCave saveDragonCave(Integer depth) {
        DragonCave dragonCave = new DragonCave();
        dragonCave.setDepth(depth);
        dragonCaveRepository.save(dragonCave);
        return dragonCave;
    }

    public DragonCave saveDragonCave(DragonCave dragonCave) {
        return dragonCaveRepository.save(dragonCave);
    }

    public Page<DragonCave> getDragonCavesPaged(Pageable pageable) {
        return dragonCaveRepository.findAll(pageable);
    }

    public long count() {
        return dragonCaveRepository.count();
    }

    public DragonCave findById(Long id) {
        return dragonCaveRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        dragonCaveRepository.deleteById(id);
    }
}