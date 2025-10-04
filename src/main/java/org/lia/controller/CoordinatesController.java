package org.lia.controller;

import org.lia.models.utils.Coordinates;
import org.lia.service.CoordinatesService;
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
}
