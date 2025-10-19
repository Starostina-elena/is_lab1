package org.lia.service;

import org.lia.models.dragon.DragonCave;
import org.lia.repository.DragonCaveRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DragonCaveService {
    private final DragonCaveRepository dragonCaveRepository;
    private final NotificationService notificationService;

    public DragonCaveService(DragonCaveRepository dragonCaveRepository, NotificationService notificationService) {
        this.dragonCaveRepository = dragonCaveRepository;
        this.notificationService = notificationService;
    }

    public DragonCave saveDragonCave(DragonCave dragonCave) {
        DragonCave saved = dragonCaveRepository.save(dragonCave);
        notificationService.sendReload();
        return saved;
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
        notificationService.sendReload();
    }

    public Iterable<DragonCave> findAll() {
        return dragonCaveRepository.findAll();
    }
}