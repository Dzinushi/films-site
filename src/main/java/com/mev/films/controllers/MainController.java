package com.mev.films.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/main")
public class MainController {

    @RequestMapping(value = "/home")
    public String homePage(){
        System.out.println("System is working");

        return "Work";
    }
}
