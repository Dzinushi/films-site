package com.mev.films.mappers.interfaces;

import com.mev.films.model.BasketDTO;

import java.util.List;

public interface BasketMapper {
    List<BasketDTO> selectBaskets();
    List<BasketDTO> selectBasketByUser(Long id);
    void insertBasket(BasketDTO basketDTO);
    void updateBasket(Long id);
    void deleteBasket(Long id);
    void deleteBasketByUser(Long userId);
    void deleteBasketByUserFilm(BasketDTO basketDTO);
}