package com.mev.films.service.implement;


import com.mev.films.mappers.interfaces.BasketMapper;
import com.mev.films.mappers.interfaces.OrderMapper;
import com.mev.films.mappers.interfaces.UserDiscountMapper;
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
public class BasketServiceImpl implements BasketService{

    private static Logger LOG = LogManager.getLogger();

    @Autowired private BasketMapper basketMapper;

    @Autowired private ExceptionService exceptionService;

    public BasketServiceImpl(){
    }

    public BasketServiceImpl(BasketMapper basketMapper, ExceptionService exceptionService){
        this.basketMapper = basketMapper;
        this.exceptionService = exceptionService;
    }

    @Override
    public List<BasketDTO> getBaskets(Long number, Long from) {
        LOG.debug("getBaskets: number = {}, from = {}",
                number, from);

        exceptionService.checkBasketNumberFrom(number, from);

        return basketMapper.selectBaskets(number, from);
    }

    @Override
    public Long getBasketsCount() {
        LOG.debug("getBasketsCount");

        return basketMapper.selectBasketsCount();
    }

    @Override
    public BasketDTO getBasket(Long id) {
        LOG.debug("getBasket: id = {}",
                id);

        exceptionService.checkBasketId(id);

        return basketMapper.selectBasket(id);
    }

    @Override
    public BasketDTO getBasketByUser(Long userId) {
        LOG.debug("getBasketByUser: user_id = {}",
                userId);

        exceptionService.checkBasketUserId(userId);

        return basketMapper.selectBasketByUser(userId);
    }

//    @Override
//    public void addBasket(BasketDTO basketDTO) {
//        LOG.debug("addBasket: basketDTO = {}",
//                basketDTO);
//
//        exceptionService.checkBasketWithoutId(basketDTO);
//
//        basketMapper.insertBasket(basketDTO);
//    }
//
//    @Override
//    public void deleteBasket(Long id){
//        LOG.debug("deleteBasket: id = {}",
//                id);
//
//        exceptionService.checkBasketId(id);
//
//        basketMapper.deleteBasket(id);
//    }
//
//    @Override
//    public void deleteBasketByUser(Long userId) {
//        LOG.debug("deleteBasket: userId = {}",
//                userId);
//
//        exceptionService.checkBasketUserId(userId);
//
//        basketMapper.deleteBasketByUser(userId);
//    }
}
