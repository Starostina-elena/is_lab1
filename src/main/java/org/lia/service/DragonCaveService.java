package org.lia.service;

import org.lia.models.dragon.DragonCave;
import org.lia.repository.DragonCaveRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
}