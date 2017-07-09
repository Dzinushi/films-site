package com.mev.films.controllers;

import com.mev.films.model.BasketDTO;
import com.mev.films.service.interfaces.BasketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BasketController {
    private static Logger LOG = LogManager.getLogger();

    @Autowired private BasketService basketService;

    @RequestMapping(value = {"/api/baskets"}, method = RequestMethod.GET)
    public List<BasketDTO> getAllBaskets() {
        LOG.debug("getAllBaskets");
        return basketService.getAllBaskets();
    }

    @RequestMapping(value = {"/api/baskets/user"}, method = RequestMethod.GET)
    public List<BasketDTO> getBasketByUser(@RequestParam Long id) {
        LOG.debug("getBasketByUser: id = {}",
                id);
        return basketService.getBasketByUser(id);
    }

    @RequestMapping(value = {"/api/baskets"}, method = RequestMethod.POST)
    public void addBasket(@RequestBody BasketDTO basketDTO) {
        LOG.debug("addBasket: id = {}, user_id = {}, film_id = {}, discount_id = {}",
                basketDTO.getId(), basketDTO.getUserDTO(), basketDTO.getFilmDTO(), basketDTO.getDiscountDTO());
        basketService.addBasket(basketDTO);
    }

    @RequestMapping(value = {"/api/baskets"}, method = RequestMethod.PUT)
    public void updateBasket(@RequestBody BasketDTO basketDTO) {
        LOG.debug("updateBasket: id = {}, user_id = {}, film_id = {}, discount_id = {}",
                basketDTO.getId(), basketDTO.getUserDTO(), basketDTO.getFilmDTO(), basketDTO.getDiscountDTO());
        basketService.updateBasket(basketDTO);
    }

    @RequestMapping(value = {"/api/baskets"}, method = RequestMethod.DELETE)
    public void deleteBasket(@RequestParam Long id) {
        LOG.debug("deleteBasket: id = {}",
                id);
        basketService.deleteBasket(id);
    }

    @RequestMapping(value = {"/api/baskets/user"}, method = RequestMethod.DELETE)
    public void deleteBasketByUser(@RequestParam Long userId) {
        LOG.debug("deleteBasket: userId = {}",
                userId);
        basketService.deleteBasketByUser(userId);
    }

    @RequestMapping(value = {"/api/baskets/user/film"}, method = RequestMethod.DELETE)
    public void deleteBasketByUserFilm(@RequestBody BasketDTO basketDTO) {
        LOG.debug("deleteBasketByUserFilm: id = {}, user_id = {}, film_id = {}",
                basketDTO.getId(), basketDTO.getUserDTO(), basketDTO.getFilmDTO());
        basketService.deleteBasketByUserFilm(basketDTO);
    }
}