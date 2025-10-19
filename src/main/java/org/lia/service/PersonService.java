package org.lia.service;


import org.lia.models.person.Person;
import org.lia.repository.PersonRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final NotificationService notificationService;

    public PersonService(PersonRepository personRepository, NotificationService notificationService) {
        this.personRepository = personRepository;
        this.notificationService = notificationService;
    }

    public Person savePerson(Person person) {
        Person saved = personRepository.save(person);
        notificationService.sendReload();
        return saved;
    }

    public long count() {
        return personRepository.count();
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
        notificationService.sendReload();
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
        notificationService.sendReload();
    }
}
