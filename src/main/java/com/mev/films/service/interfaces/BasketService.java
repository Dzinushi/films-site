package com.mev.films.service.interfaces;


import com.mev.films.model.BasketDTO;

import java.util.List;

public interface BasketService {
    List<BasketDTO> getAllBaskets();
    List<BasketDTO> getBasketByUserName(String username);
    void addBasket(BasketDTO basketDTO);
    void updateBasket(BasketDTO basketDTO);
    void deleteBasket(BasketDTO basketDTO);
    void deleteBasketByUserName(String username);
}