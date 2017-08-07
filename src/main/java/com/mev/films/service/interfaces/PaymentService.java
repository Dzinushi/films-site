package com.mev.films.service.interfaces;


import com.mev.films.model.BasketDTO;
import com.mev.films.model.PaymentDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getPayments(Long number, Long from);
    Long getPaymentCount();
    List<PaymentDTO> getPaymentsByUser(Long userId);
    List<PaymentDTO> getPaymentsByFilm(Long filmId);
    PaymentDTO getPayment(Long id);
    void addPayment(BasketDTO basketDTO);
}