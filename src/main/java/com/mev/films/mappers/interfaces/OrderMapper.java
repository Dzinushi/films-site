package com.mev.films.mappers.interfaces;

import com.mev.films.model.OrderDTO;

import java.util.List;

public interface OrderMapper {
    List<OrderDTO> selectOrders();
    OrderDTO selectOrder(Long id);
    List<OrderDTO> selectOrdersByBasket(Long basketId);
    List<OrderDTO> selectOrdersByBasketIsMark(Long basketId);
    void insertOrder(OrderDTO orderDTO);
    void deleteOrder(Long id);
    void deleteOrderByBasket(Long basketId);
}
