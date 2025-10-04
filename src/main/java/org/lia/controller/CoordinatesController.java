package org.lia.controller;

import org.lia.models.utils.Coordinates;
import org.lia.service.CoordinatesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/coordinates")
public class CoordinatesController {
    private final CoordinatesService coordinatesService;
    int pageSize = 10;

    public CoordinatesController(CoordinatesService coordinatesService) {
        this.coordinatesService = coordinatesService;
    }

    @GetMapping("/get_page")
    @ResponseBody
    public Map<String, Object> getCoordinatesPage(@RequestParam(name="page", required=false, defaultValue="0") int page) {
        Page<Coordinates> coordinates = coordinatesService.getCoordinatesPaged(PageRequest.of(page, pageSize));
        long pageCount = coordinatesService.count() / pageSize + (coordinatesService.count() % pageSize == 0 ? 0 : 1);

        Map<String, Object> response = new HashMap<>();
        response.put("coordinates", coordinates.getContent());
        response.put("pageCount", pageCount);

        return response;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("coordinates", new Coordinates());
        return "coordinates/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Coordinates coordinates, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "coordinates/create";
        }
        Coordinates saved = coordinatesService.saveCoordinates(coordinates);
        return "redirect:/coordinates/update/" + saved.getId();
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Coordinates coordinates = coordinatesService.findById(id);
        if (coordinates == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("coordinates", coordinates);
        model.addAttribute("editId", id);
        return "coordinates/create";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Coordinates coordinates, BindingResult bindingResult, Model model) {
        model.addAttribute("editId", id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("coordinates", coordinates);
            return "coordinates/create";
        }
        Coordinates existing = coordinatesService.findById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        existing.setX(coordinates.getX());
        existing.setY(coordinates.getY());
        coordinatesService.saveCoordinates(existing);
        return "redirect:/coordinates/update/" + existing.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        coordinatesService.deleteById(id);
        return "redirect:/";
    }

}
