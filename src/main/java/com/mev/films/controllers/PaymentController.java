package com.mev.films.controllers;

import com.mev.films.model.BasketDTO;
import com.mev.films.model.PaymentDTO;
import com.mev.films.service.interfaces.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private Logger LOG = LogManager.getLogger();

    @Autowired private PaymentService paymentService;

    // for admin
    @RequestMapping(value = "/admin/api/payments", method = RequestMethod.GET)
    public List<PaymentDTO> getPaymentsDTO(@RequestParam Long number,
                                           @RequestParam Long from) {
        LOG.debug("getPayments: number = {}, from = {}");

        return paymentService.getPayments(number, from);
    }

    @RequestMapping(value = {"/admin/api/payments/count", "/api/payments/count"}, method = RequestMethod.GET)
    public Long getPaymentCount(){
        LOG.debug("getPaymentCount");

        return paymentService.getPaymentCount();
    }

    @RequestMapping(value = {"/api/payments/user", "/admin/api/payments/user"}, method = RequestMethod.GET)
    public List<PaymentDTO> getPaymentsDTOByUser(@RequestParam Long userId) {
        LOG.debug("getPaymentsByUser: user_id = {}",
                userId);

        return paymentService.getPaymentsByUser(userId);
    }

    // for admin
    @RequestMapping(value = "/admin/api/payments/film", method = RequestMethod.GET)
    public List<PaymentDTO> getPaymentsDTOByFilm(@RequestParam Long filmId) {
        LOG.debug("getPaymentsByFilm: film_id = {}",
                filmId);

        return paymentService.getPaymentsByFilm(filmId);
    }

    // for admin
    @RequestMapping(value = "/admin/api/payment", method = RequestMethod.GET)
    public PaymentDTO getPayment(@RequestParam Long id) {
        LOG.debug("getPayment: id = {}",
                id);

        return paymentService.getPayment(id);
    }

    @RequestMapping(value = {"/api/payments", "/admin/api/payments"}, method = RequestMethod.POST)
    public void addPayment(@RequestBody BasketDTO basketDTO) {
        LOG.debug("addPayment: {}",
                basketDTO);

        paymentService.addPayment(basketDTO);
    }
}
