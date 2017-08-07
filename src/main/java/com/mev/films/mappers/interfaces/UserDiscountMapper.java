package com.mev.films.mappers.interfaces;


import com.mev.films.model.UserDiscountDTO;

import java.util.List;

public interface UserDiscountMapper {
    List<UserDiscountDTO> selects();
    UserDiscountDTO select(Long id);
    List<UserDiscountDTO> selectsByUser(Long userId);
    UserDiscountDTO selectByDiscount(Long discountId);
    void insert(UserDiscountDTO userDiscountDTO);
    void update(UserDiscountDTO userDiscountDTO);
    void delete(Long id);
    void deleteByDiscount(Long discountId);
    void deleteByUser(Long userId);
}