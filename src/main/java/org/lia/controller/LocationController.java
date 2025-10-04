package org.lia.controller;


import org.lia.models.utils.Location;
import org.lia.service.LocationService;
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
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;
    int pageSize = 10;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
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
}
