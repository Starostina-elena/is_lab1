package org.lia.service;


import org.lia.models.person.Person;
import org.lia.models.utils.Color;
import org.lia.models.utils.Country;
import org.lia.models.utils.Location;
import org.lia.repository.LocationRepository;
import org.lia.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LocationRepository locationRepository;

    public Person savePerson(String name, Color eyeColor, Color hairColor, Location location, Long weight, Country nationality) {
        Person person = new Person();
        person.setName(name);
        person.setEyeColor(eyeColor);
        person.setHairColor(hairColor);
        person.setLocation(location);
        person.setWeight(weight);
        person.setNationality(nationality);
        personRepository.save(person);
        return person;
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public Page<Person> getPersonsPaged(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public long count() {
        return personRepository.count();
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }
}
