package com.mev.films.controllers;

import com.mev.films.model.PaymentDTO;
import com.mev.films.service.interfaces.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {

    private Logger LOG = LogManager.getLogger();

    @Autowired private PaymentService paymentService;

    @RequestMapping(value = "/api/payments", method = RequestMethod.GET)
    public List<PaymentDTO> getPaymentsDTO() {
        LOG.debug("getPaymentsDTO");

        return paymentService.getPaymentsDTO();
    }

    @RequestMapping(value = "/api/payment/user", method = RequestMethod.GET)
    public List<PaymentDTO> getPaymentsDTOByUser(Long userId) {
        LOG.debug("getPaymentsDTOByUser: user_id = {}",
                userId);

        return paymentService.getPaymentsDTOByUser(userId);
    }

    @RequestMapping(value = "/api/payment/film", method = RequestMethod.GET)
    public List<PaymentDTO> getPaymentsDTOByFilm(Long filmId) {
        LOG.debug("getPaymentsDTOByFilm: film_id = {}",
                filmId);

        return paymentService.getPaymentsDTOByFilm(filmId);
    }

    @RequestMapping(value = "/api/payment", method = RequestMethod.GET)
    public PaymentDTO getPayment(Long id) {
        LOG.debug("getPayment: id = {}",
                id);

        return paymentService.getPayment(id);
    }

    @RequestMapping(value = "/api/payment", method = RequestMethod.POST)
    public void addPayment(PaymentDTO paymentDTO) {
        LOG.debug("addPayment: paymentDTO = {}",
                paymentDTO);

        paymentService.addPayment(paymentDTO);
    }

    @RequestMapping(value = "/api/payment", method = RequestMethod.PUT)
    public void updatePayment(PaymentDTO paymentDTO) {
        LOG.debug("updatePayment: paymentDTO = {}",
                paymentDTO);

        paymentService.updatePayment(paymentDTO);
    }

    @RequestMapping(value = "/api/payment", method = RequestMethod.DELETE)
    public void deletePayment(Long id) {
        LOG.debug("deletePayment: id = {}",
                id);

        paymentService.deletePayment(id);
    }

    @RequestMapping(value = "/api/payment/user", method = RequestMethod.DELETE)
    public void deletePaymentByUser(Long userId) {
        LOG.debug("deletePaymentByUser: user_id = {}",
                userId);

        paymentService.deletePaymentByUser(userId);
    }

    @RequestMapping(value = "/api/payment/film", method = RequestMethod.DELETE)
    public void deletePaymentByFilm(Long filmId) {
        LOG.debug("deletePaymentByFilm: film_id = {}",
                filmId);

        paymentService.deletePaymentByFilm(filmId);
    }
}
