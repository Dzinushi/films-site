package com.mev.films.mappers.interfaces;

import com.mev.films.model.DiscountDTO;

import java.util.List;

public interface DiscountMapper {
    List<DiscountDTO> selectDiscounts();
    DiscountDTO selectDiscountByCode(String code);
    Long insertDiscount(DiscountDTO discountDTO);
    Long updateDiscount(DiscountDTO discountDTO);
    Long deleteDiscountByCode(String code);
}