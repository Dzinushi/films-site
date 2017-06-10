package com.mev.films.mappers.interfaces;

import com.mev.films.model.BasketDTO;

import java.util.List;

public interface BasketMapper {
    List<BasketDTO> selectBaskets();
    List<BasketDTO> selectBasketByUserName(String userName);
    Long insertBasket(BasketDTO basketDTO);
    Long updateBasket(BasketDTO basketDTO);
    List<Long> deleteBasketByUserId(Long userId);
    Long deleteBasket(BasketDTO basketDTO);
}