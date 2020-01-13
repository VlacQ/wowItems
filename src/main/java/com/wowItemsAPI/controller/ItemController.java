package com.wowItemsAPI.controller;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.entity.Price;
import com.wowItemsAPI.service.ItemService;
import com.wowItemsAPI.service.PriceService;
import com.wowItemsAPI.util.Excel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Path;
import javax.validation.Valid;
import java.io.File;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;

    private PriceService priceService;

    private Excel excel;

    @Autowired
    public ItemController(ItemService itemService, PriceService priceService, Excel excel){
        this.itemService = itemService;
        this.priceService = priceService;
        this.excel = excel;
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

        item = itemService.findByName(item.getName());

        return "redirect:/items/item?itemId=" + item.getId();
    }

    @GetMapping("/read")
    public String readFile(){
        return "/items/read";
    }

    @PostMapping("/read")
    public String readFromFile(@RequestParam("file") MultipartFile file){
        List<Item> itemList = excel.readExcelFile(file);
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

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity exportToFile(){
        FileSystemResource fsr = new FileSystemResource(excel.exportToFile());

        System.out.println(fsr.getFilename());

        return ResponseEntity.ok()
                .header("content-disposition", "inline; filename=d" + fsr.getFilename())
                .body(fsr);
    }
}
