package com.mev.films.mappers.interfaces;

import com.mev.films.model.BasketDTO;

import java.util.List;

public interface BasketMapper {
    List<BasketDTO> selectBaskets();
    List<BasketDTO> selectBasketByUserName(String userName);
    void insertBasket(BasketDTO basketDTO);
    void updateBasket(BasketDTO basketDTO);
    void deleteBasketByUserId(Long userId);
    void deleteBasket(BasketDTO basketDTO);
}