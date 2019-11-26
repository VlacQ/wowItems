package com.wowItemsAPI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class ControllerDemo {
    @GetMapping("/hello")
    public String sayHello(Model model){
        model.addAttribute("theDate", new Date());

        return "helloworld";
    }
}
