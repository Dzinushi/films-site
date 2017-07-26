package com.mev.films.controllers;


import com.mev.films.model.DiscountDTO;
import com.mev.films.service.interfaces.DiscountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiscountController {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired private DiscountService discountService;

    public DiscountController(){
    }

    public DiscountController(DiscountService discountService){
        this.discountService = discountService;
    }

    @RequestMapping(value = {"/api/discounts"}, method = RequestMethod.GET)
    public List<DiscountDTO> getDiscounts() {
        LOG.debug("getDiscounts");

        return discountService.getDiscounts();
    }

    @RequestMapping(value = "/api/discount", method = RequestMethod.GET)
    public DiscountDTO getDiscount(@RequestParam Long id){
        LOG.debug("getDiscount: id = {}",
                id);

        return discountService.getDiscount(id);
    }

    @RequestMapping(value = {"/api/discounts/code"}, method = RequestMethod.GET)
    public DiscountDTO getDiscountByCode(@RequestParam String code) {
        LOG.debug("getDiscountByCode: code = {}",
                code);

        return discountService.getDiscountByCode(code);
    }

    @RequestMapping(value = {"/api/discounts"}, method = RequestMethod.POST)
    public void addDiscount(@RequestBody DiscountDTO discountDTO) {
        LOG.debug("addDiscount: discountDTO = {}",
                discountDTO);

        discountService.addDiscount(discountDTO);
    }

    @RequestMapping(value = {"/api/discounts"}, method = RequestMethod.PUT)
    public void updateDiscount(@RequestBody DiscountDTO discountDTO) {
        LOG.debug("updateDiscount: discountDTO = {}",
                discountDTO);

        discountService.updateDiscount(discountDTO);
    }

    @RequestMapping(value = "/api/discount", method = RequestMethod.DELETE)
    public void deleteDiscount(@RequestParam Long id){
        LOG.debug("deleteDiscount: id = {}",
                id);

        discountService.deleteDiscount(id);
    }

    @RequestMapping(value = {"/api/discounts/code"}, method = RequestMethod.DELETE)
    public void deleteDiscountByCode(@RequestParam String code) {
        LOG.debug("deleteDiscountByCode: code = {}",
                code);

        discountService.deleteDiscountByCode(code);
    }
}