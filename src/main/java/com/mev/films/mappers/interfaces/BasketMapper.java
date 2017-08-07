package com.mev.films.mappers.interfaces;

import com.mev.films.model.BasketDTO;

import java.util.List;

public interface BasketMapper {
    List<BasketDTO> selectAll();
    List<BasketDTO> selects(Long limit, Long offset);
    Long selectsCount();
    BasketDTO select(Long id);
    BasketDTO selectByUser(Long id);
    void insert(BasketDTO basketDTO);
    void delete(Long id);
    void deleteByUser(Long userId);
}