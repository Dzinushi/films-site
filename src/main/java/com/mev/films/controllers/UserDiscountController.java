package com.mev.films.controllers;

import com.mev.films.model.UserDiscountDTO;
import com.mev.films.service.interfaces.UserDiscountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public UserDiscountDTO getUserDiscount(Long id) {
        LOG.debug("getUserDiscount: id = {}",
                id);

        return userDiscountService.getUserDiscount(id);
    }

    @RequestMapping(value = {"/api/user_discount/user"}, method = RequestMethod.GET)
    public List<UserDiscountDTO> getUserDiscountsByUser(Long userId) {
        LOG.debug("getUserDiscountsByUser: user_id = {}",
                userId);

        return userDiscountService.getUserDiscountsByUser(userId);
    }

    @RequestMapping(value = {"/api/user_discount/discount"}, method = RequestMethod.GET)
    public UserDiscountDTO getUserDiscountByDiscount(Long discountId) {
        LOG.debug("getUserDiscountByDiscount: discount_id = {}",
                discountId);

        return userDiscountService.getUserDiscountByDiscount(discountId);
    }

    @RequestMapping(value = {"/api/user_discount"}, method = RequestMethod.POST)
    public void addUserDiscount(UserDiscountDTO userDiscountDTO) {
        LOG.debug("addUserDiscount: userDiscountDTO = {}",
                userDiscountDTO);

        userDiscountService.addUserDiscount(userDiscountDTO);
    }

    @RequestMapping(value = {"/api/user_discount"}, method = RequestMethod.PUT)
    public void updateUserDiscount(UserDiscountDTO userDiscountDTO) {
        LOG.debug("updateUserDiscount: userDiscountDTO = {}",
                userDiscountDTO);

        userDiscountService.updateUserDiscount(userDiscountDTO);
    }

    @RequestMapping(value = {"/api/user_discount"}, method = RequestMethod.DELETE)
    public void deleteUserDiscount(Long id) {
        LOG.debug("deleteUserDiscount: id = {}",
                id);

        userDiscountService.deleteUserDiscount(id);
    }

    @RequestMapping(value = {"/api/user_discount/discount"}, method = RequestMethod.DELETE)
    public void deleteUserDiscountByDiscount(Long discountId) {
        LOG.debug("deleteUserDiscountByDiscount: discount_id = {}",
                discountId);

        userDiscountService.deleteUserDiscountByDiscount(discountId);
    }

    @RequestMapping(value = {"/api/user_discount/user"}, method = RequestMethod.DELETE)
    public void deleteUserDiscountByUser(Long userId) {
        LOG.debug("deleteUserDiscountByUser: user_id = {}",
                userId);

        userDiscountService.deleteUserDiscountByUser(userId);
    }
}
