package org.lia.controller;

import org.lia.models.dragon.Dragon;
import org.lia.models.dragon.DragonHead;
import org.lia.service.DragonHeadService;
import org.lia.service.DragonService;
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
@RequestMapping("/dragon_heads")
public class DragonHeadController {
    private final DragonHeadService dragonHeadService;
    private final DragonService dragonService;
    int pageSize = 10;

    public DragonHeadController(DragonHeadService dragonHeadService, DragonService dragonService) {
        this.dragonHeadService = dragonHeadService;
        this.dragonService = dragonService;
    }

    @GetMapping("/get_page")
    @ResponseBody
    public Map<String, Object> getDragonHeadPage(@RequestParam(name="page", required=false, defaultValue="0") int page) {
        Page<DragonHead> dragonHeads = dragonHeadService.getDragonHeadsPaged(PageRequest.of(page, pageSize));
        long pageCount = dragonHeadService.count() / pageSize + (dragonHeadService.count() % pageSize == 0 ? 0 : 1);

        Map<String, Object> response = new HashMap<>();
        response.put("dragonHeads", dragonHeads.getContent());
        response.put("pageCount", pageCount);

        return response;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("dragonHead", new DragonHead());
        return "dragonHead/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute DragonHead dragonHead, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "dragonHead/create";
        }
        DragonHead saved = dragonHeadService.saveDragonHead(dragonHead);
        return "redirect:/dragon_heads/update/" + saved.getId();
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        DragonHead dragonHead = dragonHeadService.findById(id);
        if (dragonHead == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("dragonHead", dragonHead);
        model.addAttribute("editId", id);
        Iterable<Dragon> dragons = dragonService.findByHeadId(id);
        model.addAttribute("dragonsWithHead", dragons);
        return "dragonHead/create";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute DragonHead dragonHead, BindingResult bindingResult, Model model) {
        model.addAttribute("editId", id);
        Iterable<Dragon> dragons = dragonService.findByHeadId(id);
        model.addAttribute("dragonsWithHead", dragons);
        if (bindingResult.hasErrors()) {
            model.addAttribute("dragonHead", dragonHead);
            return "dragonHead/create";
        }
        DragonHead existing = dragonHeadService.findById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        existing.setEyesCount(dragonHead.getEyesCount());
        dragonHeadService.saveDragonHead(existing);
        return "redirect:/dragon_heads/update/" + existing.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        dragonHeadService.deleteById(id);
        return "redirect:/";
    }
}
