package com.mev.films.service.interfaces;


import com.mev.films.model.DiscountDTO;

import java.util.List;

public interface DiscountService {
    List<DiscountDTO> getDiscounts(Long number, Long from);
    Long getDiscountCount();
    DiscountDTO getDiscount(Long id);
    DiscountDTO getDiscountByCode(String code);
    void addDiscount(DiscountDTO discountDTO);
    void updateDiscount(DiscountDTO discountDTO);
    void deleteDiscount(Long id);
    void deleteDiscountByCode(String code);
}
