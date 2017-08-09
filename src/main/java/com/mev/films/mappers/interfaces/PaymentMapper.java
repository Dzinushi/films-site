package com.mev.films.mappers.interfaces;


import com.mev.films.model.PaymentDTO;
import com.mev.films.model.PaymentStatDTO;
import com.mev.films.model.UserDTO;

import java.util.List;

public interface PaymentMapper {
    List<PaymentDTO> selectAll();
    List<PaymentDTO> selects(Long limit, Long offset);
    Long selectCount();
    List<PaymentDTO> selectsByUser(Long userId);
    List<PaymentDTO> selectsByFilm(Long filmId);
    PaymentDTO select(Long id);
    List<PaymentStatDTO> selectUsersPayingAboveMedianForLastMonth(Short year, Short month);
    List<PaymentDTO> selectsSortByUsersOrders();
    List<PaymentStatDTO> selectsTop5Orders();
    Long selectsAveragePriceDayPurchases(Short year, Short month, Short day);
    void insert(PaymentDTO paymentDTO);
    void delete(Long id);
}
