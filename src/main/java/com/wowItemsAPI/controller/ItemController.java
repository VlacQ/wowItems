package com.wowItemsAPI.controller;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){this.itemService = itemService;}

    @GetMapping("/itemsList")
    public String itemsList(Model model){
        List<Item> itemList = itemService.findAll();

        System.out.println("/itemsList");

        model.addAttribute("itemList" ,itemList);
        return "itemsList";
    }

    @GetMapping("/add-item")
    public String create(Model model){
        model.addAttribute("item", new Item());
        return "create-item";
    }
}
