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
import org.springframework.stereotype.Service;

@Service
public class DragonService {
    @Autowired
    private DragonRepository dragonRepository;

    public void saveDragon(String name, Coordinates coordinates,
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
    }
}
