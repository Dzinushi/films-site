package com.mev.films.controllers;

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

    @RequestMapping(value = "/api/payments", method = RequestMethod.GET)
    public List<PaymentDTO> getPaymentsDTO() {
        LOG.debug("getPayments");

        return paymentService.getPayments();
    }

    @RequestMapping(value = "/api/payments/user", method = RequestMethod.GET)
    public List<PaymentDTO> getPaymentsDTOByUser(@RequestParam Long userId) {
        LOG.debug("getPaymentsByUser: user_id = {}",
                userId);

        return paymentService.getPaymentsByUser(userId);
    }

    @RequestMapping(value = "/api/payments/film", method = RequestMethod.GET)
    public List<PaymentDTO> getPaymentsDTOByFilm(@RequestParam Long filmId) {
        LOG.debug("getPaymentsByFilm: film_id = {}",
                filmId);

        return paymentService.getPaymentsByFilm(filmId);
    }

    @RequestMapping(value = "/api/payment/basket", method = RequestMethod.GET)
    public PaymentDTO getPaymentByBasket(@RequestParam Long basketId){
        LOG.debug("getPaymentByBasket: basket_id = {}",
                basketId);

        return paymentService.getPaymentByBasket(basketId);
    }

    @RequestMapping(value = "/api/payment", method = RequestMethod.GET)
    public PaymentDTO getPayment(@RequestParam Long id) {
        LOG.debug("getPayment: id = {}",
                id);

        return paymentService.getPayment(id);
    }

    @RequestMapping(value = "/api/payments", method = RequestMethod.POST)
    public void addPayment(@RequestBody PaymentDTO paymentDTO) {
        LOG.debug("addPayment: paymentDTO = {}",
                paymentDTO);

        paymentService.addPayment(paymentDTO);
    }

    @RequestMapping(value = "/api/payments", method = RequestMethod.PUT)
    public void updatePayment(@RequestBody PaymentDTO paymentDTO) {
        LOG.debug("updatePayment: paymentDTO = {}",
                paymentDTO);

        paymentService.updatePayment(paymentDTO);
    }

    @RequestMapping(value = "/api/payments", method = RequestMethod.DELETE)
    public void deletePayment(@RequestParam Long id) {
        LOG.debug("deletePayment: id = {}",
                id);

        paymentService.deletePayment(id);
    }

    @RequestMapping(value = "/api/payments/basket", method = RequestMethod.DELETE)
    public void deletePaymentByBasket(@RequestParam Long basketId){
        LOG.debug("deletePaymentByBasket: basket_id = {}",
                basketId);

        paymentService.deletePaymentByBasket(basketId);
    }

    @RequestMapping(value = "/api/payments/user", method = RequestMethod.DELETE)
    public void deletePaymentByUser(@RequestParam Long userId) {
        LOG.debug("deletePaymentByUser: user_id = {}",
                userId);

        paymentService.deletePaymentByUser(userId);
    }

    @RequestMapping(value = "/api/payments/film", method = RequestMethod.DELETE)
    public void deletePaymentByFilm(@RequestParam Long filmId) {
        LOG.debug("deletePaymentByFilm: film_id = {}",
                filmId);

        paymentService.deletePaymentByFilm(filmId);
    }
}
