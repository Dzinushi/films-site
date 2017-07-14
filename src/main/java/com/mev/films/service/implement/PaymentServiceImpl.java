package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.PaymentMapper;
import com.mev.films.model.PaymentDTO;
import com.mev.films.service.interfaces.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{

    private Logger LOG = LogManager.getLogger();

    @Autowired private PaymentMapper paymentMapper;

    @Override
    public List<PaymentDTO> getPaymentsDTO() {
        LOG.debug("getPaymentsDTO");

        return paymentMapper.selectPaymentsDTO();
    }

    @Override
    public List<PaymentDTO> getPaymentsDTOByUser(Long userId) {
        LOG.debug("getPaymentsDTOByUser: user_id = {}",
                userId);

        return paymentMapper.selectPaymentsDTOByUser(userId);
    }

    @Override
    public List<PaymentDTO> getPaymentsDTOByFilm(Long filmId) {
        LOG.debug("getPaymentsDTOByFilm: film_id = {}",
                filmId);

        return paymentMapper.selectPaymentsDTOByFilm(filmId);
    }

    @Override
    public PaymentDTO getPayment(Long id) {
        LOG.debug("getPayment: id = {}",
                id);

        return paymentMapper.selectPayment(id);
    }

    @Override
    public void addPayment(PaymentDTO paymentDTO) {
        LOG.debug("addPayment: paymentDTO = {}",
                paymentDTO);

        paymentMapper.insertPayment(paymentDTO);
    }

    @Override
    public void updatePayment(PaymentDTO paymentDTO) {
        LOG.debug("updatePayment: paymentDTO = {}",
                paymentDTO);

        paymentMapper.updatePayment(paymentDTO);
    }

    @Override
    public void deletePayment(Long id) {
        LOG.debug("deletePayment: id = {}",
                id);

        paymentMapper.deletePayment(id);
    }

    @Override
    public void deletePaymentByUser(Long userId) {
        LOG.debug("deletePaymentByUser: user_id = {}",
                userId);

        paymentMapper.deletePaymentByUser(userId);
    }

    @Override
    public void deletePaymentByFilm(Long filmId) {
        LOG.debug("deletePaymentByFilm: film_id = {}",
                filmId);

        paymentMapper.deletePaymentByFilm(filmId);
    }
}
