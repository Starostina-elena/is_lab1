package org.lia.controller;

import org.lia.models.dragon.Dragon;
import org.lia.models.person.Person;
import org.lia.service.DragonService;
import org.lia.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainPageConroller {
    private final DragonService dragonService;
    private final PersonService personService;

    public MainPageConroller(DragonService dragonService, PersonService personService) {
        this.dragonService = dragonService;
        this.personService = personService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") int dragonPage,
                        @RequestParam(defaultValue = "0") int personPage,
                        Model model) {
        int pageSize = 10;

        Page<Dragon> dragons = dragonService.getDragonsPaged(PageRequest.of(dragonPage, pageSize));
        Page<Person> persons = personService.getPersonsPaged(PageRequest.of(personPage, pageSize));

        long peopleCount = personService.count();
        long dragonsCount = dragonService.count();

        model.addAttribute("persons", persons);
        model.addAttribute("dragons", dragons);
        model.addAttribute("personList", persons.getContent());
        model.addAttribute("dragonList", dragons.getContent());
        model.addAttribute("personPage", personPage);
        model.addAttribute("dragonPage", dragonPage);
        model.addAttribute("peoplePages", (peopleCount + pageSize - 1) / pageSize);
        model.addAttribute("dragonsPages", (dragonsCount + pageSize - 1) / pageSize);

        return "index";
    }
}
