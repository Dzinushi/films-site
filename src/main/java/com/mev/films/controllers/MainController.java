package com.mev.films.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class MainController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage(){
        System.out.println("System is working");

        return "Work";
    }
}