package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.OrderMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.model.FilmDTO;
import com.mev.films.model.OrderDTO;
import com.mev.films.model.UserDTO;
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

    @Autowired private ExceptionService exceptionService;

    public OrderServiceImpl(){
    }

    public OrderServiceImpl(OrderMapper orderMapper, ExceptionService exceptionService){
        this.orderMapper = orderMapper;
        this.exceptionService = exceptionService;
    }

    private void priceByDiscount(OrderDTO orderDTO){

        if (orderDTO.getDiscountDTO() != null) {

            Integer price = orderDTO.getFilmDTO().getPrice();
            Float value = orderDTO.getDiscountDTO().getValue();

            if (price != null && value != null) {
                Float price_discount = price - (price * value);
                orderDTO.getFilmDTO().setPrice(Math.round(price_discount));
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
    public List<OrderDTO> getOrderByUser(Long userId) {

        LOG.debug("getOrderByUser: user_id = {}",
                userId);

        exceptionService.checkOrderUserId(userId);

        List<OrderDTO> orderDTOS = orderMapper.selectOrderByUser(userId);
        for (OrderDTO orderDTO : orderDTOS){
            priceByDiscount(orderDTO);
        }

        return orderDTOS;
    }

    @Override
    public List<OrderDTO> getOrderByUserIsMark(Long userId) {

        LOG.debug("getOrderByUserIsMark: user_id = {}",
                userId);

        exceptionService.checkOrderUserId(userId);

        List<OrderDTO> orderDTOS = orderMapper.selectOrderByUserIsMark(userId);
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
    public void deleteOrderByUser(Long userId) {

        LOG.debug("deleteOrderByUser: user_id = {}",
                userId);

        exceptionService.checkOrderUserId(userId);

        orderMapper.deleteOrderByUser(userId);
    }
}
