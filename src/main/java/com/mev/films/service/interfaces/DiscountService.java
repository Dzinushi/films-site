package com.mev.films.service.interfaces;


import com.mev.films.model.DiscountDTO;

import java.util.List;

public interface DiscountService {
    List<DiscountDTO> getAllDiscounts();
    DiscountDTO getDiscountByCode(String code);
    Long addDiscount(DiscountDTO discountDTO);
    Long updateDiscount(DiscountDTO discountDTO);
    Long deleteDiscountByCode(String code);
}
