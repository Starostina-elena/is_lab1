package org.lia.controller;


import org.lia.controller.editor.LocationEditor;
import org.lia.models.dragon.Dragon;
import org.lia.models.person.Person;
import org.lia.models.utils.Color;
import org.lia.service.DragonService;
import org.lia.service.LocationService;
import org.lia.service.PersonService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("DuplicatedCode")
@Controller
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;
    private final LocationService locationService;
    private final DragonService dragonService;
    final int pageSize = 10;

    public PersonController(PersonService personService, LocationService locationService,
                            DragonService dragonService) {
        this.personService = personService;
        this.locationService = locationService;
        this.dragonService = dragonService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(org.lia.models.utils.Location.class, new LocationEditor(locationService));
    }

    @GetMapping("/get/page")
    @ResponseBody
    public Map<String, Object> getPersonsPage(@RequestParam(name="page", required=false, defaultValue="0") int page,
                                              @RequestParam(name="sort", required=false) String sort,
                                              @RequestParam(name="dir", required=false) String dir,
                                              @RequestParam(name="filter", required=false) String filter) {

        Sort ordering = Sort.unsorted();
        if ("name".equals(sort)) {
            if ("desc".equalsIgnoreCase(dir)) ordering = Sort.by(Sort.Direction.DESC, "name");
            else ordering = Sort.by(Sort.Direction.ASC, "name");
        }

        Page<Person> persons = personService.getPersonsPagedAndFiltered(PageRequest.of(page, pageSize, ordering), filter);
        long pageCount = personService.countFiltered(filter) / pageSize + (personService.countFiltered(filter) % pageSize == 0 ? 0 : 1);

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
            model.addAttribute("colorList", Color.values());
            model.addAttribute("nationalityList", org.lia.models.utils.Country.values());
            model.addAttribute("locationList", locationService.findAll());
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
        Iterable<Dragon> dragons = dragonService.findByKillerId(id);
        model.addAttribute("dragonsWithKiller", dragons);
        return "person/create";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Person person, BindingResult bindingResult, Model model) {
        model.addAttribute("editId", id);
        Iterable<Dragon> dragons = dragonService.findByKillerId(id);
        model.addAttribute("dragonsWithKiller", dragons);
        if (bindingResult.hasErrors()) {
            model.addAttribute("person", person);
            model.addAttribute("colorList", Color.values());
            model.addAttribute("nationalityList", org.lia.models.utils.Country.values());
            model.addAttribute("locationList", locationService.findAll());
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
    public String delete(@PathVariable Long id, Model model) {
        try {
            personService.deleteById(id);
            return "redirect:/";
        } catch (DataIntegrityViolationException e) {
            Person person = personService.findById(id);
            model.addAttribute("person", person);
            model.addAttribute("editId", id);
            model.addAttribute("colorList", Color.values());
            model.addAttribute("nationalityList", org.lia.models.utils.Country.values());
            model.addAttribute("locationList", locationService.findAll());
            Iterable<Dragon> dragons = dragonService.findByKillerId(id);
            model.addAttribute("dragonsWithKiller", dragons);
            model.addAttribute("deleteError", "Удаление невозможно: есть связанные (убитые) драконы.");
            return "person/create";
        }
    }

    @GetMapping("/search/{search}")
    @ResponseBody
    public Map<String, Object> searchByName(@PathVariable("search") String search) {
        Iterable<Person> persons = personService.findPersonsByNameSubstring(search);
        Map<String, Object> result = new HashMap<>();
        result.put("persons", persons);
        return result;
    }

    @GetMapping("/get/all")
    @ResponseBody
    public Iterable<Person> getAllPersons() {
        return personService.findAll();
    }

    @PostMapping("/send/team")
    @ResponseBody
    public Map<String, Object> sendTeamToLocation(@RequestParam("personIds") List<Long> personIds, @RequestParam("locationId") long locationId) {
        personService.updatePersonsLocation(personIds, locationId);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }
}
