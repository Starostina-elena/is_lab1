package org.lia.controller;

import org.lia.models.dragon.DragonHead;
import org.lia.service.DragonHeadService;
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
@RequestMapping("/dragon_heads")
public class DragonHeadController {
    private final DragonHeadService dragonHeadService;
    int pageSize = 10;

    public DragonHeadController(DragonHeadService dragonHeadService) {
        this.dragonHeadService = dragonHeadService;
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
}
