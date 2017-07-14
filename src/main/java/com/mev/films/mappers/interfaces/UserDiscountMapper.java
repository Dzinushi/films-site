package com.mev.films.mappers.interfaces;


import com.mev.films.model.UserDiscountDTO;

import java.util.List;

public interface UserDiscountMapper {
    List<UserDiscountDTO> selectUserDiscounts();
    UserDiscountDTO selectUserDiscount(Long id);
    List<UserDiscountDTO> selectUserDiscountsByUser(Long userId);
    UserDiscountDTO selectUserDiscountByDiscount(Long discountId);
    void insertUserDiscount(UserDiscountDTO userDiscountDTO);
    void updateUserDiscount(UserDiscountDTO userDiscountDTO);
    void deleteUserDiscount(Long id);
    void deleteUserDiscountByDiscount(Long discountId);
    void deleteUserDiscountByUser(Long userId);
}