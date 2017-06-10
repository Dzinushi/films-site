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

    @Autowired private UserMapper userMapper;
    @Autowired private BasketMapper basketMapper;

    @Override
    public List<BasketDTO> getBaskets() {
        LOG.debug("getBaskets");
        return basketMapper.selectBaskets();
    }

    @Override
    public List<BasketDTO> getBasketByUserName(String username) {
        LOG.debug("getBasketByUser");
        return basketMapper.selectBasketByUserName(username);
    }

    @Override
    public Long addBasket(BasketDTO basketDTO) {
        LOG.debug("addBasket: id = {}, user_id = {}, film_id = {}, discount_id = {}",
                basketDTO.getId(), basketDTO.getUserId(), basketDTO.getFilmId(), basketDTO.getDiscountId());
        return basketMapper.insertBasket(basketDTO);
    }

    @Override
    public Long updateBasket(BasketDTO basketDTO) {
        LOG.debug("updateBasket: id = {}, user_id = {}, film_id = {}, discount_id = {}",
                basketDTO.getId(), basketDTO.getUserId(), basketDTO.getFilmId(), basketDTO.getDiscountId());
        return basketMapper.updateBasket(basketDTO);
    }

    @Override
    public Long deleteBasket(BasketDTO basketDTO) {
        LOG.debug("deleteBasket: id = {}, user_id = {}, film_id = {}, discount_id = {}",
                basketDTO.getId(), basketDTO.getUserId(), basketDTO.getFilmId(), basketDTO.getDiscountId());
        return basketMapper.deleteBasket(basketDTO);
    }

    @Override
    public List<Long> deleteBasketByUserName(String username) {
        LOG.debug("deleteBasketByUserName: username = {}",
                username);
        UserDTO userDTO = userMapper.selectUserByLogin(username);
        return basketMapper.deleteBasketByUserId(userDTO.getId());
    }
}
