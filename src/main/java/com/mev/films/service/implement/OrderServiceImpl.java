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
    public List<OrderDTO> getOrders(Long number, Long from) {
        LOG.debug("getOrders: number = {}, from = {}",
                number, from);

        exceptionService.checkOrderNumberFrom(number, from);

        return orderMapper.selects(number, from);
    }

    @Override
    public Long getOrdersCount() {
        LOG.debug("getOrdersCount");

        return orderMapper.selectsCount();
    }

    @Override
    public OrderDTO getOrder(Long id) {

        LOG.debug("getOrder: id = {}",
                id);

        exceptionService.checkOrderId(id);

        OrderDTO orderDTO = orderMapper.select(id);
        priceByDiscount(orderDTO);

        return orderDTO;
    }

    @Override
    public List<OrderDTO> getOrderByBasket(Long basketId) {

        LOG.debug("getOrderByBasket: user_id = {}",
                basketId);

        exceptionService.checkOrderBasketId(basketId);

        List<OrderDTO> orderDTOS = orderMapper.selectsByBasket(basketId);
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

        List<OrderDTO> orderDTOS = orderMapper.selectsByBasketIsMark(basketId);
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
        BasketDTO basketDTO = basketMapper.selectByUser(orderDTO.getBasketDTO().getUserDTO().getId());

        // create basket for this user
        if (basketDTO == null){
            basketMapper.insert(new BasketDTO(orderDTO.getBasketDTO().getUserDTO()));
        }

        // set basketDTO for orderDTO
        basketDTO = basketMapper.selectByUser(orderDTO.getBasketDTO().getUserDTO().getId());
        orderDTO.setBasketDTO(basketDTO);
        orderMapper.insert(orderDTO);
    }

    @Override
    public void deleteOrder(Long id) {

        LOG.debug("delete: id = {}",
                id);

        exceptionService.checkOrderId(id);

        orderMapper.delete(id);
    }

    @Override
    public void deleteOrderByBasket(Long basketId) {

        LOG.debug("deleteByBasket: user_id = {}",
                basketId);

        exceptionService.checkOrderBasketId(basketId);

        orderMapper.deleteByBasket(basketId);
    }
}
