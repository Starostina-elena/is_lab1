package org.lia.controller;


import org.lia.models.person.Person;
import org.lia.models.utils.Location;
import org.lia.service.LocationService;
import org.lia.service.PersonService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;
    private final PersonService personService;
    int pageSize = 10;

    public LocationController(LocationService locationService, PersonService personService) {
        this.locationService = locationService;
        this.personService = personService;
    }

    @GetMapping("/get_page")
    @ResponseBody
    public Map<String, Object> getLocationPage(@RequestParam(name="page", required=false, defaultValue="0") int page) {
        Page<Location> locations = locationService.getLocationsPaged(PageRequest.of(page, pageSize));
        long pageCount = locationService.count() / pageSize + (locationService.count() % pageSize == 0 ? 0 : 1);

        Map<String, Object> response = new HashMap<>();
        response.put("locations", locations.getContent());
        response.put("pageCount", pageCount);

        return response;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("location", new Location());
        return "location/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Location location, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "location/create";
        }
        Location saved = locationService.saveLocation(location);
        return "redirect:/locations/update/" + saved.getId();
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Location location = locationService.findById(id);
        if (location == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("location", location);
        model.addAttribute("editId", id);
        Iterable<Person> persons = personService.findByLocationId(id);
        model.addAttribute("personsWithLocation", persons);
        return "location/create";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Location location, BindingResult bindingResult, Model model) {
        model.addAttribute("editId", id);
        Iterable<Person> persons = personService.findByLocationId(id);
        model.addAttribute("personsWithLocation", persons);
        if (bindingResult.hasErrors()) {
            model.addAttribute("location", location);
            return "location/create";
        }
        Location existing = locationService.findById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        existing.setX(location.getX());
        existing.setY(location.getY());
        existing.setZ(location.getZ());
        existing.setName(location.getName());
        locationService.saveLocation(existing);
        return "redirect:/locations/update/" + existing.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        try {
            locationService.deleteById(id);
            return "redirect:/";
        } catch (DataIntegrityViolationException e) {
            Location location = locationService.findById(id);
            model.addAttribute("location", location);
            model.addAttribute("editId", id);
            Iterable<Person> persons = personService.findByLocationId(id);
            model.addAttribute("personsWithLocation", persons);
            model.addAttribute("deleteError", "Удаление невозможно: локация занята персонажами.");
            return "location/create";
        }
    }
}
