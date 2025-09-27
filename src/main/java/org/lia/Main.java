package org.lia;

import org.lia.models.dragon.Dragon;
import org.lia.models.dragon.DragonCave;
import org.lia.models.dragon.DragonHead;
import org.lia.models.person.Person;
import org.lia.models.utils.*;
import org.lia.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.lia.config.JpaConfig;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        LocationService locationService = context.getBean(LocationService.class);
        PersonService personService = context.getBean(PersonService.class);
        DragonHeadService dragonHeadService = context.getBean(DragonHeadService.class);
        DragonCaveService dragonCaveService = context.getBean(DragonCaveService.class);
        CoordinatesService coordinatesService = context.getBean(CoordinatesService.class);
        DragonService dragonService = context.getBean(DragonService.class);

//        Location location = locationService.saveLocation(1.4, 1.4f, 1.4f, "home");
//        Person person = personService.savePerson("Bob", Color.YELLOW, Color.BLUE, location, 80L, Country.GERMANY);
//        DragonCave cave = dragonCaveService.saveDragonCave(111);
//        DragonHead dragonHead = dragonHeadService.saveDragonHead(5);
//        Coordinates coordinates = coordinatesService.saveCoordinates(100, 200L);
//        Dragon dragon = dragonService.saveDragon("Smaug", coordinates, cave, person, 300, Color.RED, DragonType.AIR, DragonCharacter.CHAOTIC, dragonHead);
    }
}
