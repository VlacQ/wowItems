package com.wowItemsAPI.controller;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.entity.Price;
import com.wowItemsAPI.service.ItemService;
import com.wowItemsAPI.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prices")
public class PriceController {
    private PriceService priceService;
    private ItemService itemService;

    @Autowired
    public PriceController(PriceService priceService, ItemService itemService){
        this.priceService = priceService;
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public String itemsList(Model model){
        List<Price> priceList = priceService.findAll();

        model.addAttribute("priceList" ,priceList);
        return "prices/list";
    }

    @GetMapping("/add")
    public String addPriceForm(Model model){
        model.addAttribute("price", new Price());
        return "prices/price-formSave";
    }

    @GetMapping("/update")
    public String updatePrice(@RequestParam("priceId") int id, Model model){
        Price price = priceService.findById(id);
        model.addAttribute("price", price);
        return "prices/price-formSave";
    }

    @GetMapping("/delete")
    public String deletePrice(@RequestParam("priceId") int priceId, @RequestParam("itemId") int itemId){
        priceService.deleteById(priceId);
        return "redirect:/items/item?itemId=" + itemId;
    }

    @PostMapping("/save")
    public String savePrice(@ModelAttribute("price") Price price, @RequestParam("itemId") int itemId){
        if (priceService.save(price)){
            return "redirect:/items/item?itemId=" + itemId;
        } else {
            return "prices/price-formSave";
        }
    }
}
