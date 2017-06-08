package com.mev.films.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "")
public class MainController {

    @RequestMapping(value = {"/", "/home**"}, method = RequestMethod.GET)
    public String homePage(){
        System.out.println("Connect to /home");

        return "Welcome!";
    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminPage(){
        System.out.println("Connect to /admin");

        ModelAndView model = new ModelAndView();
        model.addObject("msg", "Connected as admin");

        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout){

        System.out.println("Connecting to /login");

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid login or password");
        }

        if (logout != null){
            model.addObject("msg", "Connected as user");
        }

        return model;
    }
}