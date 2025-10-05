package org.lia.service;


import org.lia.models.person.Person;
import org.lia.models.utils.Color;
import org.lia.models.utils.Country;
import org.lia.models.utils.Location;
import org.lia.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

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

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public Iterable<Person> findByLocationId(Long locationId) {
        return personRepository.findByLocationId(locationId);
    }

    public Page<Person> getPersonsPagedAndFiltered(Pageable pageable, String filter) {
        if (filter == null || filter.isEmpty()) {
            return personRepository.findAll(pageable);
        }
        Person probe = new Person();
        probe.setName(filter);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Person> example = Example.of(probe, matcher);
        return personRepository.findAll(example, pageable);
    }

    public long countFiltered(String filter) {
        if (filter == null || filter.isEmpty()) {
            return personRepository.count();
        }
        Person probe = new Person();
        probe.setName(filter);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Person> example = Example.of(probe, matcher);
        return personRepository.count(example);
    }

    public Iterable<Person> findPersonsByNameSubstring(String search) {
        return personRepository.findPersonsByNameSubstring(search);
    }

    public void updatePersonsLocation(List<Long> personIds, long locationId) {
        personRepository.updatePersonsLocation(personIds, locationId);
    }
}
