package org.lia.service;

import org.lia.models.dragon.DragonHead;
import org.lia.repository.DragonHeadRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DragonHeadService {
    private final DragonHeadRepository dragonHeadRepository;
    private final NotificationService notificationService;

    public DragonHeadService(DragonHeadRepository dragonHeadRepository, NotificationService notificationService) {
        this.dragonHeadRepository = dragonHeadRepository;
        this.notificationService = notificationService;
    }

    public DragonHead saveDragonHead(DragonHead dragonHead) {
        DragonHead saved = dragonHeadRepository.save(dragonHead);
        notificationService.sendReload("dragon_heads");
        return saved;
    }

    public Page<DragonHead> getDragonHeadsPaged(Pageable pageable) {
        return dragonHeadRepository.findAll(pageable);
    }

    public long count() {
        return dragonHeadRepository.count();
    }

    public DragonHead findById(Long id) {
        return dragonHeadRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        dragonHeadRepository.deleteById(id);
        notificationService.sendReload("dragon_heads");
    }

    public Iterable<DragonHead> findAll() {
        return dragonHeadRepository.findAll();
    }
}
