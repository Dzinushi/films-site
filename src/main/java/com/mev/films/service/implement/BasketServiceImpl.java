package com.mev.films.service.implement;


import com.mev.films.mappers.interfaces.BasketMapper;
import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.model.BasketDTO;
import com.mev.films.model.UserDTO;
import com.mev.films.service.interfaces.BasketService;
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

    public BasketServiceImpl(){
    }

    public BasketServiceImpl(BasketMapper basketMapper){
        this.basketMapper = basketMapper;
    }

    @Override
    public List<BasketDTO> getBaskets() {
        LOG.debug("getBaskets");

        return basketMapper.selectBaskets();
    }

    @Override
    public List<BasketDTO> getBasketByUser(Long userId) {
        LOG.debug("getBasketByUser: user_id = {}",
                userId);

        return basketMapper.selectBasketByUser(userId);
    }

    @Override
    public void addBasket(BasketDTO basketDTO) {
        LOG.debug("addBasket: basketDTO = {}",
                basketDTO);

        basketMapper.insertBasket(basketDTO);
    }

    @Override
    public void updateBasket(BasketDTO basketDTO) {
        LOG.debug("updateBasket: basketDTO = {}",
                basketDTO);

        basketMapper.updateBasket(basketDTO);
    }

    @Override
    public void deleteBasket(Long id){
        LOG.debug("deleteBasket: id = {}",
                id);

        basketMapper.deleteBasket(id);
    }

    @Override
    public void deleteBasketByUser(Long userId) {
        LOG.debug("deleteBasket: userId = {}",
                userId);

        basketMapper.deleteBasketByUser(userId);
    }

    @Override
    public void deleteBasketByUserFilm(BasketDTO basketDTO) {
        LOG.debug("deleteBasketByUserFilm: basketDTO = {}",
                basketDTO);

        basketMapper.deleteBasketByUserFilm(basketDTO);
    }
}
