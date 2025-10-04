package org.lia.service;

import org.lia.models.dragon.DragonHead;
import org.lia.repository.DragonHeadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DragonHeadService {
    @Autowired
    private DragonHeadRepository dragonHeadRepository;

    public DragonHead saveDragonHead(Integer eyesCount) {
        DragonHead dragonHead = new DragonHead();
        dragonHead.setEyesCount(eyesCount);
        dragonHeadRepository.save(dragonHead);
        return dragonHead;
    }

    public Page<DragonHead> getDragonHeadsPaged(Pageable pageable) {
        return dragonHeadRepository.findAll(pageable);
    }

    public long count() {
        return dragonHeadRepository.count();
    }
}
