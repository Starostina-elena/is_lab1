package org.lia.controller;


import org.lia.models.person.Person;
import org.lia.models.utils.Color;
import org.lia.service.LocationService;
import org.lia.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;
    private final LocationService locationService;
    int pageSize = 10;

    public PersonController(PersonService personService, LocationService locationService) {
        this.personService = personService;
        this.locationService = locationService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(org.lia.models.utils.Location.class, new LocationEditor(locationService));
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

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("colorList", Color.values());
        model.addAttribute("nationalityList", org.lia.models.utils.Country.values());
        model.addAttribute("locationList", locationService.findAll());
        return "person/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Person person, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "person/create";
        }
        Person saved = personService.savePerson(person);
        return "redirect:/persons/update/" + saved.getId();
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Person person = personService.findById(id);
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("person", person);
        model.addAttribute("editId", id);
        model.addAttribute("colorList", Color.values());
        model.addAttribute("nationalityList", org.lia.models.utils.Country.values());
        model.addAttribute("locationList", locationService.findAll());
        return "person/create";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Person person, BindingResult bindingResult, Model model) {
        model.addAttribute("editId", id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("person", person);
            return "person/create";
        }
        Person existing = personService.findById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        existing.setName(person.getName());
        existing.setEyeColor(person.getEyeColor());
        existing.setHairColor(person.getHairColor());
        existing.setLocation(person.getLocation());
        existing.setWeight(person.getWeight());
        existing.setNationality(person.getNationality());
        personService.savePerson(existing);
        return "redirect:/persons/update/" + existing.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        personService.deleteById(id);
        return "redirect:/";
    }
}
