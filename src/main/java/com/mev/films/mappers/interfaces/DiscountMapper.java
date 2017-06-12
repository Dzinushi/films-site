package com.mev.films.mappers.interfaces;

import com.mev.films.model.DiscountDTO;

import java.util.List;

public interface DiscountMapper {
    List<DiscountDTO> selectDiscounts();
    DiscountDTO selectDiscountByCode(String code);
    void insertDiscount(DiscountDTO discountDTO);
    void updateDiscount(DiscountDTO discountDTO);
    void deleteDiscountByCode(String code);
}