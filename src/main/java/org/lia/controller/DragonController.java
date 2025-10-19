package org.lia.controller;

import org.lia.models.dragon.Dragon;
import org.lia.models.utils.Color;
import org.lia.models.utils.DragonType;
import org.lia.models.utils.DragonCharacter;
import org.lia.controller.editor.*;
import org.lia.service.*;
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
import java.util.Map;


@SuppressWarnings("ALL")
@Controller
@RequestMapping("/dragons")
public class DragonController {
    private final DragonService dragonService;
    private final CoordinatesService coordinatesService;
    private final DragonCaveService dragonCaveService;
    private final PersonService personService;
    private final DragonHeadService dragonHeadService;
    int pageSize = 10;

    public DragonController(DragonService dragonService, CoordinatesService coordinatesService,
                            DragonCaveService dragonCaveService, PersonService personService,
                            DragonHeadService dragonHeadService) {
        this.dragonService = dragonService;
        this.coordinatesService = coordinatesService;
        this.dragonCaveService = dragonCaveService;
        this.personService = personService;
        this.dragonHeadService = dragonHeadService;
    }

    @GetMapping("/get_page")
    @ResponseBody
    public Map<String, Object> getDragonsPage(@RequestParam(name="page", required=false, defaultValue="0") int page,
                                              @RequestParam(name="sort", required=false) String sort,
                                              @RequestParam(name="dir", required=false) String dir,
                                              @RequestParam(name="filter", required=false) String filter) {
        Sort ordering = Sort.unsorted();
        if ("name".equals(sort)) {
            if ("desc".equalsIgnoreCase(dir)) ordering = Sort.by(Sort.Direction.DESC, "name");
            else ordering = Sort.by(Sort.Direction.ASC, "name");
        }

        Page<Dragon> dragons = dragonService.getDragonsPagedAndFiltered(PageRequest.of(page, pageSize, ordering), filter);
        long pageCount = dragonService.countFiltered(filter) / pageSize + (dragonService.countFiltered(filter) % pageSize == 0 ? 0 : 1);

        Map<String, Object> response = new HashMap<>();
        response.put("dragons", dragons.getContent());
        response.put("pageCount", pageCount);

        return response;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("dragon", new Dragon());
        model.addAttribute("colorList", Color.values());
        model.addAttribute("typeList", DragonType.values());
        model.addAttribute("characterList", DragonCharacter.values());
        model.addAttribute("coordinatesList", coordinatesService.findAll());
        model.addAttribute("dragonCaveList", dragonCaveService.findAll());
        model.addAttribute("personList", personService.findAll());
        model.addAttribute("dragonHeadList", dragonHeadService.findAll());
        return "dragon/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Dragon dragon, BindingResult bindingResult, Model model) {
        if (dragon.getCreationDate() == null) {
            dragon.setCreationDate(java.time.ZonedDateTime.now());
        }
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("colorList", Color.values());
            model.addAttribute("typeList", DragonType.values());
            model.addAttribute("characterList", DragonCharacter.values());
            model.addAttribute("coordinatesList", coordinatesService.findAll());
            model.addAttribute("dragonCaveList", dragonCaveService.findAll());
            model.addAttribute("personList", personService.findAll());
            model.addAttribute("dragonHeadList", dragonHeadService.findAll());
            return "dragon/create";
        }
        Dragon saved = dragonService.saveDragon(dragon);
        return "redirect:/dragons/update/" + saved.getId();
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Dragon dragon = dragonService.findById(id);
        if (dragon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("dragon", dragon);
        model.addAttribute("editId", id);
        model.addAttribute("colorList", Color.values());
        model.addAttribute("typeList", DragonType.values());
        model.addAttribute("characterList", DragonCharacter.values());
        model.addAttribute("coordinatesList", coordinatesService.findAll());
        model.addAttribute("dragonCaveList", dragonCaveService.findAll());
        model.addAttribute("personList", personService.findAll());
        model.addAttribute("dragonHeadList", dragonHeadService.findAll());
        return "dragon/create";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Dragon dragon, BindingResult bindingResult, Model model) {
        model.addAttribute("editId", id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("colorList", Color.values());
            model.addAttribute("typeList", DragonType.values());
            model.addAttribute("characterList", DragonCharacter.values());
            model.addAttribute("coordinatesList", coordinatesService.findAll());
            model.addAttribute("dragonCaveList", dragonCaveService.findAll());
            model.addAttribute("personList", personService.findAll());
            model.addAttribute("dragonHeadList", dragonHeadService.findAll());
            model.addAttribute("dragon", dragon);
            return "dragon/create";
        }
        Dragon existing = dragonService.findById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        existing.setName(dragon.getName());
        existing.setCoordinates(dragon.getCoordinates() != null ? coordinatesService.findById(dragon.getCoordinates().getId()) : null);
        existing.setCave(dragon.getCave() != null ? dragonCaveService.findById(dragon.getCave().getId()) : null);
        existing.setKiller(dragon.getKiller() != null ? personService.findById(dragon.getKiller().getId()) : null);
        existing.setAge(dragon.getAge());
        existing.setColor(dragon.getColor());
        existing.setType(dragon.getType());
        existing.setCharacter(dragon.getCharacter());
        existing.setHead(dragon.getHead() != null ? dragonHeadService.findById(dragon.getHead().getId()) : null);
        dragonService.saveDragon(existing);
        return "redirect:/dragons/update/" + existing.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        dragonService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/average_age")
    @ResponseBody
    public Map<String, Object> getAverageAge() {
        Double avg = dragonService.getAverageAge();
        Map<String, Object> result = new HashMap<>();
        result.put("averageAge", avg);
        return result;
    }

    @GetMapping("/count_head_less_than/{maxId}")
    @ResponseBody
    public Map<String, Object> countHeadLessThan(@PathVariable("maxId") int maxId) {
        int count = dragonService.countDragonsWithHeadLessThan(maxId);
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        return result;
    }

    @GetMapping("/search/{search}")
    @ResponseBody
    public Map<String, Object> searchByName(@PathVariable("search") String search) {
        Iterable<Dragon> dragons = dragonService.findDragonsByNameSubstring(search);
        Map<String, Object> result = new HashMap<>();
        result.put("dragons", dragons);
        return result;
    }

    @PostMapping("/kill")
    @ResponseBody
    public Map<String, Object> killDragon(@RequestParam("dragonId") long dragonId, @RequestParam("killerId") long killerId) {
        dragonService.killDragon(dragonId, killerId);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    @GetMapping("/get_without_killer")
    @ResponseBody
    public Iterable<Dragon> getDragonsWithoutKiller() {
        return dragonService.findDragonsWithoutKiller();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(org.lia.models.dragon.DragonHead.class, new DragonHeadEditor(dragonHeadService));
        binder.registerCustomEditor(org.lia.models.dragon.DragonCave.class, new DragonCaveEditor(dragonCaveService));
        binder.registerCustomEditor(org.lia.models.person.Person.class, new PersonEditor(personService));
        binder.registerCustomEditor(org.lia.models.utils.Coordinates.class, new CoordinatesEditor(coordinatesService));
    }
}
