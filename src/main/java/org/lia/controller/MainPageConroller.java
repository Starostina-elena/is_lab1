package org.lia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageConroller {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
