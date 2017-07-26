package com.mev.films.mappers.interfaces;

import com.mev.films.model.OrderDTO;

import java.util.List;

public interface OrderMapper {
    List<OrderDTO> selectOrders();
    OrderDTO selectOrder(Long id);
    List<OrderDTO> selectOrderByUser(Long id);
    List<OrderDTO> selectOrderByUserIsMark(Long userId);
    void insertOrder(OrderDTO orderDTO);
    void deleteOrder(Long id);
    void deleteOrderByUser(Long userId);
}
