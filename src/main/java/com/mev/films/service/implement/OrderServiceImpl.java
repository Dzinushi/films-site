package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.BasketMapper;
import com.mev.films.mappers.interfaces.OrderMapper;
import com.mev.films.model.*;
import com.mev.films.service.interfaces.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private Logger LOG = LogManager.getLogger();

    @Autowired private OrderMapper orderMapper;
    @Autowired private BasketMapper basketMapper;

    @Autowired private ExceptionService exceptionService;

    public OrderServiceImpl(){
    }

    public OrderServiceImpl(OrderMapper orderMapper, BasketMapper basketMapper, ExceptionService exceptionService){
        this.orderMapper = orderMapper;
        this.basketMapper = basketMapper;
        this.exceptionService = exceptionService;
    }

    private void priceByDiscount(OrderDTO orderDTO){

        if (orderDTO.getDiscountDTO() != null) {

            Integer price = orderDTO.getFilmDTO().getPrice();
            Float value = orderDTO.getDiscountDTO().getValue();

            if (price != null && value != null) {
                Float price_discount = price - (price * value);
                orderDTO.setPriceByDiscount(Math.round(price_discount));
            }
        }
    }

    @Override
    public List<OrderDTO> getOrders() {

        LOG.debug("getOrders:");

        List<OrderDTO> orderDTOS = orderMapper.selectOrders();
        if (orderDTOS != null){
            for (OrderDTO orderDTO : orderDTOS){
                priceByDiscount(orderDTO);
            }
        }

        return orderDTOS;
    }

    @Override
    public OrderDTO getOrder(Long id) {

        LOG.debug("getOrder: id = {}",
                id);

        exceptionService.checkOrderId(id);

        OrderDTO orderDTO = orderMapper.selectOrder(id);
        priceByDiscount(orderDTO);

        return orderDTO;
    }

    @Override
    public List<OrderDTO> getOrderByBasket(Long basketId) {

        LOG.debug("getOrderByBasket: user_id = {}",
                basketId);

        exceptionService.checkOrderBasketId(basketId);

        List<OrderDTO> orderDTOS = orderMapper.selectOrdersByBasket(basketId);
        for (OrderDTO orderDTO : orderDTOS){
            priceByDiscount(orderDTO);
        }

        return orderDTOS;
    }

    @Override
    public List<OrderDTO> getOrderByBasketIsMark(Long basketId) {

        LOG.debug("getOrderByBasketIsMark: user_id = {}",
                basketId);

        exceptionService.checkOrderBasketId(basketId);

        List<OrderDTO> orderDTOS = orderMapper.selectOrdersByBasketIsMark(basketId);
        for (OrderDTO orderDTO : orderDTOS){
            priceByDiscount(orderDTO);
        }

        return orderDTOS;
    }

    @Override
    public void addOrder(OrderDTO orderDTO) {

        LOG.debug("addOrder: {}",
                orderDTO);

        exceptionService.checkOrderWithoutId(orderDTO);

        // checking if basket exist for this user
        BasketDTO basketDTO = basketMapper.selectBasketByUser(orderDTO.getBasketDTO().getUserDTO().getId());

        // create basket for this user
        if (basketDTO == null){
            basketMapper.insertBasket(new BasketDTO(orderDTO.getBasketDTO().getUserDTO()));
        }

        // set basketDTO for orderDTO
        basketDTO = basketMapper.selectBasketByUser(orderDTO.getBasketDTO().getUserDTO().getId());
        orderDTO.setBasketDTO(basketDTO);
        orderMapper.insertOrder(orderDTO);
    }

    @Override
    public void deleteOrder(Long id) {

        LOG.debug("deleteOrder: id = {}",
                id);

        exceptionService.checkOrderId(id);

        orderMapper.deleteOrder(id);
    }

    @Override
    public void deleteOrderByBasket(Long basketId) {

        LOG.debug("deleteOrderByBasket: user_id = {}",
                basketId);

        exceptionService.checkOrderBasketId(basketId);

        orderMapper.deleteOrderByBasket(basketId);
    }
}
