package com.mev.films.controllers;

import com.mev.films.model.UserDiscountDTO;
import com.mev.films.service.interfaces.UserDiscountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserDiscountController {

    Logger LOG = LogManager.getLogger();

    @Autowired private UserDiscountService userDiscountService;

    @RequestMapping(value = {"/api/user_discounts"}, method = RequestMethod.GET)
    public List<UserDiscountDTO> getUserDiscounts() {
        LOG.debug("getUserDiscounts");

        return userDiscountService.getUserDiscounts();
    }

    @RequestMapping(value = {"/api/user_discount"}, method = RequestMethod.GET)
    public UserDiscountDTO getUserDiscount(@RequestParam Long id) {
        LOG.debug("getUserDiscount: id = {}",
                id);

        return userDiscountService.getUserDiscount(id);
    }

    @RequestMapping(value = {"/api/user_discounts/user"}, method = RequestMethod.GET)
    public List<UserDiscountDTO> getUserDiscountsByUser(@RequestParam Long userId) {
        LOG.debug("getUserDiscountsByUser: user_id = {}",
                userId);

        return userDiscountService.getUserDiscountsByUser(userId);
    }

    @RequestMapping(value = {"/api/user_discounts/discount"}, method = RequestMethod.GET)
    public UserDiscountDTO getUserDiscountByDiscount(@RequestParam Long discountId) {
        LOG.debug("getUserDiscountByDiscount: discount_id = {}",
                discountId);

        return userDiscountService.getUserDiscountByDiscount(discountId);
    }

    @RequestMapping(value = {"/api/user_discounts"}, method = RequestMethod.POST)
    public void addUserDiscount(@RequestBody UserDiscountDTO userDiscountDTO) {
        LOG.debug("addUserDiscount: userDiscountDTO = {}",
                userDiscountDTO);

        userDiscountService.addUserDiscount(userDiscountDTO);
    }

    @RequestMapping(value = {"/api/user_discounts"}, method = RequestMethod.PUT)
    public void updateUserDiscount(@RequestBody UserDiscountDTO userDiscountDTO) {
        LOG.debug("updateUserDiscount: userDiscountDTO = {}",
                userDiscountDTO);

        userDiscountService.updateUserDiscount(userDiscountDTO);
    }

    @RequestMapping(value = {"/api/user_discounts"}, method = RequestMethod.DELETE)
    public void deleteUserDiscount(@RequestParam Long id) {
        LOG.debug("deleteUserDiscount: id = {}",
                id);

        userDiscountService.deleteUserDiscount(id);
    }

    @RequestMapping(value = {"/api/user_discounts/discount"}, method = RequestMethod.DELETE)
    public void deleteUserDiscountByDiscount(@RequestParam Long discountId) {
        LOG.debug("deleteUserDiscountByDiscount: discount_id = {}",
                discountId);

        userDiscountService.deleteUserDiscountByDiscount(discountId);
    }

    @RequestMapping(value = {"/api/user_discounts/user"}, method = RequestMethod.DELETE)
    public void deleteUserDiscountByUser(@RequestParam Long userId) {
        LOG.debug("deleteUserDiscountByUser: user_id = {}",
                userId);

        userDiscountService.deleteUserDiscountByUser(userId);
    }
}
