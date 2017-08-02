package com.mev.films.service.interfaces;


import com.mev.films.model.BasketDTO;

import java.util.List;

public interface BasketService {
    List<BasketDTO> getBaskets();
    BasketDTO getBasket(Long id);
    BasketDTO getBasketByUser(Long userId);
//    void addBasket(BasketDTO basketDTO);
//    void deleteBasket(Long id);
//    void deleteBasketByUser(Long id);
}