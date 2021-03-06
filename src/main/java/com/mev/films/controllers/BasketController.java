package com.mev.films.controllers;

import com.mev.films.model.BasketDTO;
import com.mev.films.service.interfaces.BasketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
public class BasketController {
    private static Logger LOG = LogManager.getLogger();

    @Autowired private BasketService basketService;

    // for admin
    @RequestMapping(value = {"/admin/api/baskets"}, method = RequestMethod.GET)
    public List<BasketDTO> getBaskets(@RequestParam Long number,
                                      @RequestParam Long from) {
        LOG.debug("getBaskets");

        return basketService.getBaskets(number, from);
    }

    @RequestMapping(value = "/admin/api/baskets/count", method = RequestMethod.GET)
    public Long getBasketsCount(){
        LOG.debug("getBasketsCount");

        return basketService.getBasketsCount();
    }

    @RequestMapping(value = {"/admin/api/basket", "/api/basket"}, method = RequestMethod.GET)
    public BasketDTO getBasket(@RequestParam Long id){
        LOG.debug("getBasket: id = {}",
                id);

        return basketService.getBasket(id);
    }

    @RequestMapping(value = {"/api/baskets/user", "/admin/api/baskets/user"}, method = RequestMethod.GET)
    public BasketDTO getBasketByUser(@RequestParam Long userId) {
        LOG.debug("getBasketByUser: user_id = {}",
                userId);

        return basketService.getBasketByUser(userId);
    }

//    @RequestMapping(value = {"/api/baskets"}, method = RequestMethod.POST)
//    public void addBasket(@RequestBody BasketDTO basketDTO) {
//        LOG.debug("addBasket: basketDTO",
//                basketDTO);
//
//        basketService.addBasket(basketDTO);
//    }
//
//    @RequestMapping(value = {"/api/baskets"}, method = RequestMethod.DELETE)
//    public void delete(@RequestParam Long id) {
//        LOG.debug("delete: id = {}",
//                id);
//
//        basketService.delete(id);
//    }
//
//    @RequestMapping(value = {"/api/baskets/user"}, method = RequestMethod.DELETE)
//    public void deleteByUser(@RequestParam Long userId) {
//        LOG.debug("delete: user_id = {}",
//                userId);
//
//        basketService.deleteByUser(userId);
//    }
}