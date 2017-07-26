package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.PaymentMapper;
import com.mev.films.model.*;
import com.mev.films.service.interfaces.BasketService;
import com.mev.films.service.interfaces.OrderService;
import com.mev.films.service.interfaces.PaymentService;
import com.mev.films.service.interfaces.UserDiscountService;
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

    @Autowired private BasketService basketService;
    @Autowired private OrderService orderService;
    @Autowired private UserDiscountService userDiscountService;

    public PaymentServiceImpl(){
    }

    public PaymentServiceImpl(PaymentMapper paymentMapper, BasketService basketService, OrderService orderService, UserDiscountService userDiscountService) {
        this.paymentMapper = paymentMapper;
        this.basketService = basketService;
        this.orderService = orderService;
        this.userDiscountService = userDiscountService;
    }

    @Override
    public List<PaymentDTO> getPayments() {
        LOG.debug("getPayments");

        return paymentMapper.selectPayments();
    }

    @Override
    public List<PaymentDTO> getPaymentsByUser(Long userId) {
        LOG.debug("getPaymentsByUser: user_id = {}",
                userId);

        List<PaymentDTO> paymentDTOS = null;
        if (userId != null && userId >= 0){

            paymentDTOS = paymentMapper.selectPaymentsByUser(userId);

        } else {
            LOG.debug("Error in 'getPaymentsByUser'! 'user_id' is not validate: user_id = {}",
                    userId);
        }

        return paymentDTOS;
    }

    @Override
    public List<PaymentDTO> getPaymentsByFilm(Long filmId) {
        LOG.debug("getPaymentsByFilm: film_id = {}",
                filmId);

        List<PaymentDTO> paymentDTOS = null;
        if (filmId != null && filmId >= 0){
            paymentDTOS = paymentMapper.selectPaymentsByFilm(filmId);
        } else {
            LOG.debug("Error in 'getPaymentsByFilm'! 'film_id' is not validate: film_id = {}",
                    filmId);
        }

        return paymentDTOS;
    }

    @Override
    public PaymentDTO getPayment(Long id) {
        LOG.debug("getPayment: id = {}",
                id);

        PaymentDTO paymentDTO = null;
        if (id != null && id >=0 ){
            paymentDTO = paymentMapper.selectPayment(id);
        } else {
            LOG.debug("Error in 'getPayment'! 'id' is not validate: id = {}",
                    id);
        }

        return paymentDTO;
    }

    @Override
    public void addPayment(Long userId) {

        if (userId != null && userId >= 0){
            BasketDTO basketDTO = basketService.getBasketByUser(userId);
            if (basketDTO != null){
                List<OrderDTO> orderDTOS = orderService.getOrderByUserIsMark(basketDTO.getUserDTO().getId());
                if (orderDTOS != null){

                    // Check all mark field in orderDTOs
                    for (OrderDTO orderDTO : orderDTOS){

                        // Check is discount free
                        DiscountDTO discountDTO = orderDTO.getDiscountDTO();
                        if (discountDTO != null) {
                            UserDiscountDTO userDiscountDTO = userDiscountService.getUserDiscountByDiscount(discountDTO.getId());
                            if (!userDiscountDTO.isUsed()) {

                                PaymentDTO paymentDTO = new PaymentDTO(orderDTO.getUserDTO(), orderDTO.getFilmDTO(), orderDTO.getDiscountDTO());
                                LOG.debug("addPayment: {}",
                                        paymentDTO);
                                paymentMapper.insertPayment(paymentDTO);

                            }
                            else {
                                LOG.debug("Error in 'addPayment'! This discount code has already been used");
                            }

                            // Set discount is 'used'
                            userDiscountDTO.setUsed(true);
                            userDiscountService.updateUserDiscount(userDiscountDTO);
                        }
                    }
                    // Delete orders that will be payed
                    orderService.deleteOrderByUser(userId);

                } else {
                    LOG.debug("Error in 'addPayment'! Orders for that user not found");
                }
            } else {
                LOG.debug("Error in 'addPayment'! Basket with this user not found");
            }
        }
    }

    @Override
    public void deletePayment(Long id) {
        LOG.debug("deletePayment: id = {}",
                id);

        if (id != null && id >= 0){
            paymentMapper.deletePayment(id);
        }
        else {
            LOG.debug("Error in 'deletePayment'! 'id' is not validate: id = {}",
                    id);
        }
    }
}
