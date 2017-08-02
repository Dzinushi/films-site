package com.mev.films.controllers;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class MainController {

    private Logger LOG = LogManager.getLogger();

    @RequestMapping(value = {"/", "/home**"}, method = RequestMethod.GET)
    public ModelAndView homePage(){

        LOG.debug("Connect to /home");

        ModelAndView model = new ModelAndView();
        model.addObject("msg", "Default page");
        model.setViewName("home");

        return model;
    }

    @RequestMapping(value = {"/admin**"}, method = RequestMethod.GET)
    public ModelAndView adminPage(){

        LOG.debug("Connect to /admin");

        ModelAndView model = new ModelAndView();
        model.addObject("msg", "Connected as ROLE_ADMIN");
        model.setViewName("admin");

        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout){

        LOG.debug("Connecting to /login");

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid login or password");
            LOG.debug("Invalid login or password");
        }

        if (logout != null){
            model.addObject("msg", "You've been logged out successfully");
            LOG.debug("You've been logged out successfully");
        }
        model.setViewName("login");

        return model;
    }
}