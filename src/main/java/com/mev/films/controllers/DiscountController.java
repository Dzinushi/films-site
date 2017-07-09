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

    @RequestMapping(value = {"/api/discounts"}, method = RequestMethod.GET)
    public List<DiscountDTO> getAllDiscounts() {
        LOG.debug("getAllDiscounts");
        return discountService.getAllDiscounts();
    }

    @RequestMapping(value = {"/api/discounts/code"}, method = RequestMethod.GET)
    public DiscountDTO getDiscountByCode(@RequestParam String code) {
        LOG.debug("getDiscountByCode: code = {}",
                code);
        return discountService.getDiscountByCode(code);
    }

    @RequestMapping(value = {"/api/discounts"}, method = RequestMethod.POST)
    public void addDiscount(@RequestBody DiscountDTO discountDTO) {
        LOG.debug("addDiscount: id = {}, code = {}",
                discountDTO.getId(), discountDTO.getCode());
        discountService.addDiscount(discountDTO);
    }

    @RequestMapping(value = {"/api/discounts"}, method = RequestMethod.PUT)
    public void updateDiscount(@RequestBody DiscountDTO discountDTO) {
        LOG.debug("updateDiscount: id = {}, code = {}",
                discountDTO.getId(), discountDTO.getCode());
        discountService.updateDiscount(discountDTO);
    }

    @RequestMapping(value = {"/api/discounts"}, method = RequestMethod.DELETE)
    public void deleteDiscountByCode(@RequestParam String code) {
        LOG.debug("deleteDiscountByCode: code = {}",
                code);
        discountService.deleteDiscountByCode(code);
    }
}