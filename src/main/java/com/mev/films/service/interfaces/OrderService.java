package com.mev.films.service.interfaces;

import com.mev.films.model.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getOrders();
    OrderDTO getOrder(Long id);
    List<OrderDTO> getOrderByUser(Long userId);
    List<OrderDTO> getOrderByUserIsMark(Long userId);
    void addOrder(OrderDTO orderDTO);
    void deleteOrder(Long id);
    void deleteOrderByUser(Long userId);
}
