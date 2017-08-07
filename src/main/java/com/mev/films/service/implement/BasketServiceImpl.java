package com.mev.films.service.implement;


import com.mev.films.mappers.interfaces.BasketMapper;
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

        return basketMapper.selects(number, from);
    }

    @Override
    public Long getBasketsCount() {
        LOG.debug("getBasketsCount");

        return basketMapper.selectsCount();
    }

    @Override
    public BasketDTO getBasket(Long id) {
        LOG.debug("getBasket: id = {}",
                id);

        exceptionService.checkBasketId(id);

        return basketMapper.select(id);
    }

    @Override
    public BasketDTO getBasketByUser(Long userId) {
        LOG.debug("getBasketByUser: user_id = {}",
                userId);

        exceptionService.checkBasketUserId(userId);

        return basketMapper.selectByUser(userId);
    }

//    @Override
//    public void addBasket(BasketDTO basketDTO) {
//        LOG.debug("addBasket: basketDTO = {}",
//                basketDTO);
//
//        exceptionService.checkBasketWithoutId(basketDTO);
//
//        basketMapper.insert(basketDTO);
//    }
//
//    @Override
//    public void delete(Long id){
//        LOG.debug("delete: id = {}",
//                id);
//
//        exceptionService.checkBasketId(id);
//
//        basketMapper.delete(id);
//    }
//
//    @Override
//    public void deleteByUser(Long userId) {
//        LOG.debug("delete: userId = {}",
//                userId);
//
//        exceptionService.checkBasketUserId(userId);
//
//        basketMapper.deleteByUser(userId);
//    }
}
