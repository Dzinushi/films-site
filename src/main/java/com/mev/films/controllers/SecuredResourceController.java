package com.mev.films.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecuredResourceController {

    @RequestMapping("/secured")
    public void secureResource(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Accessing to secured resources");
    }
}