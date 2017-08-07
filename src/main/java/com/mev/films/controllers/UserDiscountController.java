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

    @RequestMapping(value = {"/admin/api/user_discounts"}, method = RequestMethod.GET)
    public List<UserDiscountDTO> getUserDiscounts(@RequestParam Long number,
                                                  @RequestParam Long from) {
        LOG.debug("getUserDiscounts: number = {}, from = {}",
                number, from);

        return userDiscountService.getUserDiscounts(number, from);
    }

    @RequestMapping(value = "/admin/api/user_discounts/count", method = RequestMethod.GET)
    public Long getUserDiscountsCount(){
        LOG.debug("getUserDiscountsCount");

        return userDiscountService.getUserDiscountsCount();
    }

    @RequestMapping(value = {"/admin/api/user_discount"}, method = RequestMethod.GET)
    public UserDiscountDTO getUserDiscount(@RequestParam Long id) {
        LOG.debug("getUserDiscount: id = {}",
                id);

        return userDiscountService.getUserDiscount(id);
    }

    @RequestMapping(value = {"/admin/api/user_discounts/user"}, method = RequestMethod.GET)
    public List<UserDiscountDTO> getUserDiscountsByUser(@RequestParam Long userId) {
        LOG.debug("getUserDiscountsByUser: user_id = {}",
                userId);

        return userDiscountService.getUserDiscountsByUser(userId);
    }

    @RequestMapping(value = {"/admin/api/user_discounts/discount"}, method = RequestMethod.GET)
    public UserDiscountDTO getUserDiscountByDiscount(@RequestParam Long discountId) {
        LOG.debug("getUserDiscountByDiscount: discount_id = {}",
                discountId);

        return userDiscountService.getUserDiscountByDiscount(discountId);
    }

    @RequestMapping(value = {"/admin/api/user_discounts"}, method = RequestMethod.POST)
    public void addUserDiscount(@RequestBody UserDiscountDTO userDiscountDTO) {
        LOG.debug("addUserDiscount: userDiscountDTO = {}",
                userDiscountDTO);

        userDiscountService.addUserDiscount(userDiscountDTO);
    }

    @RequestMapping(value = {"/admin/api/user_discounts"}, method = RequestMethod.PUT)
    public void updateUserDiscount(@RequestBody UserDiscountDTO userDiscountDTO) {
        LOG.debug("update: userDiscountDTO = {}",
                userDiscountDTO);

        userDiscountService.updateUserDiscount(userDiscountDTO);
    }

    @RequestMapping(value = {"/admin/api/user_discounts"}, method = RequestMethod.DELETE)
    public void deleteUserDiscount(@RequestParam Long id) {
        LOG.debug("delete: id = {}",
                id);

        userDiscountService.deleteUserDiscount(id);
    }

    @RequestMapping(value = {"/admin/api/user_discounts/discount"}, method = RequestMethod.DELETE)
    public void deleteUserDiscountByDiscount(@RequestParam Long discountId) {
        LOG.debug("deleteByDiscount: discount_id = {}",
                discountId);

        userDiscountService.deleteUserDiscountByDiscount(discountId);
    }

    @RequestMapping(value = {"/admin/api/user_discounts/user"}, method = RequestMethod.DELETE)
    public void deleteUserDiscountByUser(@RequestParam Long userId) {
        LOG.debug("deleteByUser: user_id = {}",
                userId);

        userDiscountService.deleteUserDiscountByUser(userId);
    }
}
