package com.wowItemsAPI.controller;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.entity.Price;
import com.wowItemsAPI.entity.PriceItem;
import com.wowItemsAPI.service.ItemService;
import com.wowItemsAPI.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
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
    public String addPriceForm(Model model, @RequestParam("itemId") @Valid Integer itemId){
        PriceItem priceItem = new PriceItem();
        priceItem.setPack(1);
        priceItem.setItem(itemId);
        model.addAttribute("priceItem", priceItem);
        model.addAttribute("item", itemService.findById(itemId));
        return "prices/price-formSave";
    }

    @GetMapping("/update")
    public String updatePriceGet(Model model, @RequestParam("itemId") @Valid Integer itemId, @RequestParam("priceId") @Valid Long priceId){
        Price price = priceService.findById(priceId);
        PriceItem pi = new PriceItem();
        pi.setItem(itemId);
        pi.setAmount(price.getAmount().multiply(BigDecimal.valueOf(price.getQuantity())));
        pi.setDate(price.getDate());
        pi.setQuantity(price.getQuantity());
        pi.setId(price.getId());
        pi.setPack(1);
        Item item = itemService.findById(itemId);
        model.addAttribute("priceItem", pi);
        model.addAttribute("item", item);
        return "prices/price-formSave";
    }

    @GetMapping("/delete")
    public String deletePrice(@RequestParam("priceId") @Valid Long priceId, @RequestParam("itemId") @Valid Integer itemId){
        Item item = itemService.findById(itemId);
        Price price = priceService.findById(priceId);
        item.removePrice(price);
        priceService.deleteById(priceId);
        return "redirect:/items/item?itemId=" + itemId;
    }

    @PostMapping("/save")
    public String savePrice(@ModelAttribute("priceItem") @Valid PriceItem priceItem, BindingResult bindingResult, Model model){
        Item item = itemService.findById(priceItem.getItem());
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            return "prices/price-formSave";
        }
        if (priceItem.getId() == null)
            priceItem.setId(0L);
        Price price = priceService.findById(priceItem.getId());
        price.setDate(priceItem.getDate());
        price.setAmount(priceItem.getAmount().multiply(BigDecimal.valueOf(priceItem.getPack())));
        price.setQuantity(priceItem.getQuantity() * priceItem.getPack());
        if (priceItem.getId() != 0L)
            price.setId(priceItem.getId());
        priceService.save(price);
        if (priceItem.getId() == 0L){
            item.addPrice(price);
        }
        itemService.save(item);
        return "redirect:/items/item?itemId=" + item.getId();

    }
}
