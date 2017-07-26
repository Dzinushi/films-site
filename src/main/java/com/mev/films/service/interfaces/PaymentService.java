package com.mev.films.service.interfaces;


import com.mev.films.model.PaymentDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getPayments();
    List<PaymentDTO> getPaymentsByUser(Long userId);
    List<PaymentDTO> getPaymentsByFilm(Long filmId);
    PaymentDTO getPayment(Long id);
    void addPayment(Long userId);
    void deletePayment(Long id);
}