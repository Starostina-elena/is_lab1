package org.lia.service;

import org.lia.models.dragon.DragonHead;
import org.lia.repository.DragonHeadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DragonHeadService {
    @Autowired
    private DragonHeadRepository dragonHeadRepository;

    public void saveDragonHead(Integer eyesCount) {
        DragonHead dragonHead = new DragonHead();
        dragonHead.setEyesCount(eyesCount);
        dragonHeadRepository.save(dragonHead);
    }
}
