package com.mev.films.mappers.interfaces;

import com.mev.films.model.BasketDTO;

import java.util.List;

public interface BasketMapper {
    List<BasketDTO> selectBaskets();
    BasketDTO selectBasket(Long id);
    BasketDTO selectBasketByUser(Long id);
    void insertBasket(BasketDTO basketDTO);
    void deleteBasket(Long id);
    void deleteBasketByUser(Long userId);
}