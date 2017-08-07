package com.mev.films.mappers.interfaces;

import com.mev.films.model.OrderDTO;

import java.util.List;

public interface OrderMapper {
    List<OrderDTO> selectAll();
    List<OrderDTO> selects(Long limit, Long offset);
    Long selectsCount();
    OrderDTO select(Long id);
    List<OrderDTO> selectsByBasket(Long basketId);
    List<OrderDTO> selectsByBasketIsMark(Long basketId);
    void insert(OrderDTO orderDTO);
    void delete(Long id);
    void deleteByBasket(Long basketId);
}
