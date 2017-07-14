package com.mev.films.service.interfaces;


import com.mev.films.model.PaymentDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getPaymentsDTO();
    List<PaymentDTO> getPaymentsDTOByUser(Long userId);
    List<PaymentDTO> getPaymentsDTOByFilm(Long filmId);
    PaymentDTO getPayment(Long id);
    void addPayment(PaymentDTO paymentDTO);
    void updatePayment(PaymentDTO paymentDTO);
    void deletePayment(Long id);
    void deletePaymentByUser(Long userId);
    void deletePaymentByFilm(Long filmId);
}