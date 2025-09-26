package org.lia.service;


import org.lia.models.person.Person;
import org.lia.models.utils.Color;
import org.lia.models.utils.Country;
import org.lia.models.utils.Location;
import org.lia.repository.LocationRepository;
import org.lia.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LocationRepository locationRepository;

    public void savePerson(String name, Color eyeColor, Color hairColor, Location location, Long weight, Country nationality) {
        Person person = new Person();
        person.setName(name);
        person.setEyeColor(eyeColor);
        person.setHairColor(hairColor);
        person.setLocation(location);
        person.setWeight(weight);
        person.setNationality(nationality);
        personRepository.save(person);
    }
}
