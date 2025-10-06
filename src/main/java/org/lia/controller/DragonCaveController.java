package org.lia.controller;

import org.lia.models.dragon.Dragon;
import org.lia.models.dragon.DragonCave;
import org.lia.service.DragonCaveService;
import org.lia.service.DragonService;
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
@RequestMapping("/dragon_caves")
public class DragonCaveController {
    private final DragonCaveService dragonCaveService;
    private final DragonService dragonService;
    final int pageSize = 10;

    public DragonCaveController(DragonCaveService dragonCaveService, DragonService dragonService) {
        this.dragonCaveService = dragonCaveService;
        this.dragonService = dragonService;
    }

    @GetMapping("/get_page")
    @ResponseBody
    public Map<String, Object> getDragonCavePage(@RequestParam(name="page", required=false, defaultValue="0") int page) {
        Page<DragonCave> dragonCaves = dragonCaveService.getDragonCavesPaged(PageRequest.of(page, pageSize));
        long pageCount = dragonCaveService.count() / pageSize + (dragonCaveService.count() % pageSize == 0 ? 0 : 1);

        Map<String, Object> response = new HashMap<>();
        response.put("dragonCaves", dragonCaves.getContent());
        response.put("pageCount", pageCount);

        return response;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("dragonCave", new DragonCave());
        return "dragonCave/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute DragonCave dragonCave, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "dragonCave/create";
        }
        DragonCave saved = dragonCaveService.saveDragonCave(dragonCave);
        return "redirect:/dragon_caves/update/" + saved.getId();
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        DragonCave dragonCave = dragonCaveService.findById(id);
        if (dragonCave == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("dragonCave", dragonCave);
        model.addAttribute("editId", id);
        Iterable<Dragon> dragons = dragonService.findByCaveId(id);
        model.addAttribute("dragonsWithCave", dragons);
        return "dragonCave/create";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute DragonCave dragonCave, BindingResult bindingResult, Model model) {
        model.addAttribute("editId", id);
        Iterable<Dragon> dragons = dragonService.findByCaveId(id);
        model.addAttribute("dragonsWithCave", dragons);
        if (bindingResult.hasErrors()) {
            model.addAttribute("dragonCave", dragonCave);
            return "dragonCave/create";
        }
        DragonCave existing = dragonCaveService.findById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        existing.setDepth(dragonCave.getDepth());
        dragonCaveService.saveDragonCave(existing);
        return "redirect:/dragon_caves/update/" + existing.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        try {
            dragonCaveService.deleteById(id);
            return "redirect:/";
        } catch (DataIntegrityViolationException e) {
            DragonCave dragonCave = dragonCaveService.findById(id);
            model.addAttribute("dragonCave", dragonCave);
            model.addAttribute("editId", id);
            Iterable<Dragon> dragons = dragonService.findByCaveId(id);
            model.addAttribute("dragonsWithCave", dragons);
            model.addAttribute("deleteError", "Удаление невозможно: пещера занята драконом.");
            return "dragonCave/create";
        }
    }
}
