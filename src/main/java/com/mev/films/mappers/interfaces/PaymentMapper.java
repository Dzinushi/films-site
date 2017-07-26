package com.mev.films.mappers.interfaces;


import com.mev.films.model.PaymentDTO;

import java.util.List;

public interface PaymentMapper {
    List<PaymentDTO> selectPayments();
    List<PaymentDTO> selectPaymentsByUser(Long userId);
    List<PaymentDTO> selectPaymentsByFilm(Long filmId);
    PaymentDTO selectPayment(Long id);
    void insertPayment(PaymentDTO paymentDTO);
    void deletePayment(Long id);
}
