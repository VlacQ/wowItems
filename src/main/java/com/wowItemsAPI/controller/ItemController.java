package com.wowItemsAPI.controller;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.entity.Price;
import com.wowItemsAPI.service.ItemService;
import com.wowItemsAPI.service.PriceService;
import com.wowItemsAPI.util.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;

    private PriceService priceService;

    private ExcelReader excelReader;

    @Autowired
    public ItemController(ItemService itemService, PriceService priceService, ExcelReader excelReader){
        this.itemService = itemService;
        this.priceService = priceService;
        this.excelReader = excelReader;
    }


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

    @PostMapping("/read")
    public String readFromFile(@RequestParam("file") MultipartFile file){
        List<Item> itemList = excelReader.readExcelFile(file);
        Item temp;

        for (Item item:itemList) {
            temp = itemService.findByName(item.getName());
            if (temp == null){
                for (Price price:item.getPriceList()) {
                    priceService.save(price);
                }
                itemService.save(item);
                item.countValues();
            } else {
                for (Price price:item.getPriceList()) {
                    priceService.save(price);
                    temp.addPrice(price);
                }
                itemService.save(temp);
            }
        }

        return "items/readSuccessfully";
    }
}
