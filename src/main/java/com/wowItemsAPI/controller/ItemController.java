package com.wowItemsAPI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;

@Controller
@Mapping("/api")
public class ItemController {
    @GetMapping("/itemsList")
    public String itemsList(){
        return "itemsList";
    }
}
