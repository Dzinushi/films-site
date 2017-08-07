package com.mev.films.controllers;

import com.mev.films.model.OrderDTO;
import com.mev.films.service.interfaces.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired private OrderService orderService;

    // for admin
    @RequestMapping(value = {"/admin/api/orders"}, method = RequestMethod.GET)
    public List<OrderDTO> getOrders(@RequestParam Long number,
                                    @RequestParam Long from){

        LOG.debug("getOrders: number = {}, from = {}",
                number, from);

        return orderService.getOrders(number, from);
    }

    @RequestMapping(value = {"/admin/api/orders/count"}, method = RequestMethod.GET)
    public Long getOrdersCount(){

        LOG.debug("getOrdersCount");

        return orderService.getOrdersCount();
    }

    @RequestMapping(value = {"/admin/api/order"}, method = RequestMethod.GET)
    public OrderDTO getOrder(@RequestParam Long id){

        LOG.debug("getOrder: order_id = {}",
                id);

        return orderService.getOrder(id);
    }

    @RequestMapping(value = {"/admin/api/basket/orders", "/api/basket/orders"}, method = RequestMethod.GET)
    public List<OrderDTO> getOrderByBasket(@RequestParam Long basketId){

        LOG.debug("getOrderByBasket: basket_id = {}",
                basketId);

        return orderService.getOrderByBasket(basketId);
    }

    @RequestMapping(value = {"/admin/api/basket/orders/marked", "/api/basket/orders/marked"}, method = RequestMethod.GET)
    public List<OrderDTO> getOrderByBasketIsMark(@RequestParam Long basketId){

        LOG.debug("getOrderByBasketIsMark: basket_id = {}",
                basketId);

        return orderService.getOrderByBasketIsMark(basketId);
    }

    @RequestMapping(value = {"/admin/api/order", "/api/order"}, method = RequestMethod.POST)
    public void addOrder(@RequestBody OrderDTO orderDTO){

        LOG.debug("addOrder: {}",
                orderDTO);

        orderService.addOrder(orderDTO);
    }

    @RequestMapping(value = {"/admin/api/order"}, method = RequestMethod.DELETE)
    public void deleteOrder(@RequestParam Long id){

        LOG.debug("deleteOrder: order_id = {}",
                id);

        orderService.deleteOrder(id);
    }

    @RequestMapping(value = {"/admin/api/basket/order"})
    public void deleteOrderByBasket(@RequestParam Long basketId){

        LOG.debug("deleteOrderByBasket: basket_id = {}",
                basketId);

        orderService.deleteOrderByBasket(basketId);
    }
}
