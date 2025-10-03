package org.lia.controller;

import org.lia.models.dragon.Dragon;
import org.lia.service.DragonService;
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
@RequestMapping("/dragons")
public class DragonController {
    private final DragonService dragonService;
    int pageSize = 10;

    public DragonController(DragonService dragonService) {
        this.dragonService = dragonService;
    }

    @GetMapping("/get_page")
    @ResponseBody
    public Map<String, Object> getDragonsPage(@RequestParam(name="page", required=false, defaultValue="0") int page) {

        Page<Dragon> dragons = dragonService.getDragonsPaged(PageRequest.of(page, pageSize));
        long pageCount = dragonService.count() / pageSize + (dragonService.count() % pageSize == 0 ? 0 : 1);

        Map<String, Object> response = new HashMap<>();
        response.put("dragons", dragons.getContent());
        response.put("pageCount", pageCount);

        return response;
    }
}
