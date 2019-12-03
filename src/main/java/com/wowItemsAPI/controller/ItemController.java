package com.wowItemsAPI.controller;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String save(@RequestParam("itemId") @Valid int id, Model model){
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
    public String updateItem(@RequestParam("itemId") @Valid int id, Model model){
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "items/item-formSave";
    }

    @GetMapping("/delete")
    public String deleteItem(@RequestParam("itemId") @Valid int id){
        itemService.deleteById(id);
        return "redirect:/items/list";
    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute("item") @Valid Item item, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "items/item-formSave";
        }
        itemService.save(item);

        return "redirect:/items/list";
    }

    @GetMapping("/read")
    public String readFile(){
        return "/items/read";
    }
}
