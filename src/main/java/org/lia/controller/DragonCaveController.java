package org.lia.controller;

import org.lia.models.dragon.DragonCave;
import org.lia.service.DragonCaveService;
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
@RequestMapping("/dragon_caves")
public class DragonCaveController {
    private final DragonCaveService dragonCaveService;
    int pageSize = 10;

    public DragonCaveController(DragonCaveService dragonCaveService) {
        this.dragonCaveService = dragonCaveService;
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
}
