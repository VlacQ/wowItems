package com.wowItemsAPI.controller;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){this.itemService = itemService;}

    @GetMapping("/list")
    public String itemsList(Model model){
        List<Item> itemList = itemService.findAll();

        model.addAttribute("itemList" ,itemList);
        return "items/list";
    }

    @GetMapping("/item")
    public String save(@RequestParam("itemId") int id, Model model){
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "items/item";
    }

    @GetMapping("/add")
    public String addItemForm(Model model){
        model.addAttribute("item", new Item());
        return "items/item-formSave";
    }

    @GetMapping("/update")
    public String updateItem(@RequestParam("itemId") int id, Model model){
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "items/item-formSave";
    }

    @GetMapping("/delete")
    public String deleteItem(@RequestParam("itemId") int id){
        itemService.deleteById(id);
        return "redirect:/items/list";
    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute("item") Item item){
        itemService.save(item);

        return "redirect:/items/list";
    }
}
