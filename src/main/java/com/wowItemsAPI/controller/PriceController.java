package com.wowItemsAPI.controller;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.entity.Price;
import com.wowItemsAPI.service.ItemService;
import com.wowItemsAPI.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String addPriceForm(Model model, @RequestParam("itemId") @Valid int itemId){
        model.addAttribute("price", new Price());
        model.addAttribute("item", itemService.findById(itemId));
        return "prices/price-formSave";
    }

    @GetMapping("/update")
    public String updatePrice(@RequestParam("priceId") @Valid Long id, Model model){
        Price price = priceService.findById(id);
        model.addAttribute("price", price);
        return "prices/price-formSave";
    }

    @GetMapping("/delete")
    public String deletePrice(@RequestParam("priceId") @Valid Long priceId, @RequestParam("itemId") @Valid int itemId){
        Item item = itemService.findById(itemId);
        Price price = priceService.findById(priceId);
        item.removePrice(price);
        priceService.deleteById(priceId);
        return "redirect:/items/item?itemId=" + itemId;
    }

    @PostMapping("/save")
    public String savePrice(@ModelAttribute("price") @Valid Price price, @RequestParam("itemId") @Valid int itemId, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "prices/price-formSave";
        }
        priceService.save(price);
        Item item = itemService.findById(itemId);
        item.addPrice(price);
        itemService.save(item);
        return "redirect:/items/item?itemId=" + itemId;

    }
}
