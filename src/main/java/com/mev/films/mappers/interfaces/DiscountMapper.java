package com.mev.films.mappers.interfaces;

import com.mev.films.model.DiscountDTO;

import java.util.List;

public interface DiscountMapper {
    List<DiscountDTO> selectsAll();
    List<DiscountDTO> selects(Long limit, Long offset);
    Long selectsCount();
    DiscountDTO select(Long id);
    DiscountDTO selectByCode(String code);
    void insert(DiscountDTO discountDTO);
    void update(DiscountDTO discountDTO);
    void deleteByCode(String code);
    void delete(Long id);
}