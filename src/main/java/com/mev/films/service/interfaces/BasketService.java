package com.mev.films.service.interfaces;


import com.mev.films.model.BasketDTO;

import java.util.List;

public interface BasketService {
    List<BasketDTO> getAllBaskets();
    List<BasketDTO> getBasketByUser(Long id);
    void addBasket(BasketDTO basketDTO);
    void updateBasket(BasketDTO basketDTO);
    void deleteBasket(Long id);
    void deleteBasketByUser(Long id);
    void deleteBasketByUserFilm(BasketDTO basketDTO);
}