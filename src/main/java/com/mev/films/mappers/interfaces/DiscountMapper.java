package com.mev.films.mappers.interfaces;

import com.mev.films.model.DiscountDTO;

public interface DiscountMapper {
    Long insertDiscount(DiscountDTO discountDTO);
    Long updateDiscount(DiscountDTO discountDTO);
    Long deleteDiscountByCode(String code);
}