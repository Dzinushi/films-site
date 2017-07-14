package com.mev.films.service.interfaces;


import com.mev.films.model.UserDiscountDTO;

import java.util.List;

public interface UserDiscountService {
    List<UserDiscountDTO> getUserDiscounts();
    UserDiscountDTO getUserDiscount(Long id);
    List<UserDiscountDTO> getUserDiscountsByUser(Long userId);
    UserDiscountDTO getUserDiscountByDiscount(Long discountId);
    void addUserDiscount(UserDiscountDTO userDiscountDTO);
    void updateUserDiscount(UserDiscountDTO userDiscountDTO);
    void deleteUserDiscount(Long id);
    void deleteUserDiscountByDiscount(Long discountId);
    void deleteUserDiscountByUser(Long userId);
}
