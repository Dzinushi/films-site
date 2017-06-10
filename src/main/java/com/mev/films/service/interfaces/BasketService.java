package com.mev.films.service.interfaces;


import com.mev.films.model.BasketDTO;

import java.util.List;

public interface BasketService {
    List<BasketDTO> getBaskets();
    List<BasketDTO> getBasketByUserName(String username);
    Long addBasket(BasketDTO basketDTO);
    Long updateBasket(BasketDTO basketDTO);
    Long deleteBasket(BasketDTO basketDTO);
    List<Long> deleteBasketByUserName(String username);
}