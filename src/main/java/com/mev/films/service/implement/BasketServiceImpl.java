package com.mev.films.service.implement;


import com.mev.films.mappers.interfaces.BasketMapper;
import com.mev.films.mappers.interfaces.OrderMapper;
import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.model.*;
import com.mev.films.service.interfaces.BasketService;
import com.mev.films.service.interfaces.OrderService;
import com.mev.films.service.interfaces.UserDiscountService;
import com.mev.films.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BasketServiceImpl implements BasketService{

    private static Logger LOG = LogManager.getLogger();

    @Autowired private BasketMapper basketMapper;

    @Autowired private OrderService orderService;

    public BasketServiceImpl(){
    }

    public BasketServiceImpl(BasketMapper basketMapper, OrderService orderService){
        this.basketMapper = basketMapper;
        this.orderService = orderService;
    }

    @Override
    public List<BasketDTO> getBaskets() {
        LOG.debug("getBaskets");

        return basketMapper.selectBaskets();
    }

    @Override
    public BasketDTO getBasket(Long id) {
        LOG.debug("getBasket: id = {}",
                id);

        BasketDTO basketDTO = null;
        if (id != null && id >= 0) {
            basketDTO = basketMapper.selectBasket(id);
        }
        else {
            LOG.debug("Error in 'getBasket'! 'id' is not validate: id = {}",
                    id);
        }

        return basketDTO;
    }

    @Override
    public BasketDTO getBasketByUser(Long userId) {
        LOG.debug("getBasketByUser: user_id = {}",
                userId);

        BasketDTO basketDTO = null;
        if (userId != null && userId >= 0){
            basketDTO = basketMapper.selectBasketByUser(userId);
        }
        else {
            LOG.debug("Error in 'getBasketByUser'! 'user_id' is not validate: user_id = {}",
                    userId);
        }

        return basketDTO;
    }

    @Override
    public void addBasket(BasketDTO basketDTO) {
        LOG.debug("addBasket: basketDTO = {}",
                basketDTO);

        if (basketDTO != null){
            if (basketDTO.getUserDTO() != null){
                List<OrderDTO> orderDTOS = orderService.getOrderByUser(basketDTO.getUserDTO().getId());
                if (orderDTOS != null){

                    basketMapper.insertBasket(basketDTO);

                }
                LOG.debug("Error in 'addBasket'! Orders not found");
            }
            LOG.debug("Error in 'addBasket'! 'userDTO' is null");
        }
        else {
            LOG.debug("Error in 'addBasket'! 'basketDTO' is null");
        }
    }

    @Override
    public void deleteBasket(Long id){
        LOG.debug("deleteBasket: id = {}",
                id);

        if (id != null && id >= 0){
            basketMapper.deleteBasket(id);
        }
        else {
            LOG.debug("Error in 'deleteBasket'! 'id' is not validate: id = {}",
                    id);
        }
    }

    @Override
    public void deleteBasketByUser(Long userId) {
        LOG.debug("deleteBasket: userId = {}",
                userId);


        if (userId != null && userId >= 0){
            basketMapper.deleteBasketByUser(userId);
        }
        else {
            LOG.debug("Error in 'deleteBasket'! 'user_id' is not validate: user_id = {}",
                    userId);
        }
    }
}
