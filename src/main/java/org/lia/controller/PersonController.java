package org.lia.controller;

import org.lia.models.person.Person;
import org.lia.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;
    int pageSize = 10;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/get_page")
    @ResponseBody
    public Map<String, Object> getPersonsPage(@RequestParam(name="page", required=false, defaultValue="0") int page) {

        Page<Person> persons = personService.getPersonsPaged(PageRequest.of(page, pageSize));
        long pageCount = personService.count() / pageSize + (personService.count() % pageSize == 0 ? 0 : 1);

        Map<String, Object> response = new HashMap<>();
        response.put("persons", persons.getContent());
        response.put("pageCount", pageCount);

        return response;
    }
}
