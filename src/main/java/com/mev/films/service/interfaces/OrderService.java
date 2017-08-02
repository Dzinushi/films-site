package com.mev.films.service.interfaces;

import com.mev.films.model.OrderDTO;
import com.mev.films.model.UserDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getOrders();
    OrderDTO getOrder(Long id);
    List<OrderDTO> getOrderByBasket(Long basketId);
    List<OrderDTO> getOrderByBasketIsMark(Long basketId);
    void addOrder(OrderDTO orderDTO);
    void deleteOrder(Long id);
    void deleteOrderByBasket(Long basketId);
}
