package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.OrderMapper;
import com.mev.films.mappers.interfaces.PaymentMapper;
import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.model.*;
import com.mev.films.service.interfaces.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{

    private Logger LOG = LogManager.getLogger();

    @Autowired private PaymentMapper paymentMapper;
    @Autowired private OrderMapper orderMapper;
    @Autowired private UserDiscountMapper userDiscountMapper;

    @Autowired private ExceptionService exceptionService;

    public PaymentServiceImpl(){
    }

    public PaymentServiceImpl(PaymentMapper paymentMapper, OrderMapper orderMapper, UserDiscountMapper userDiscountMapper, ExceptionService exceptionService) {
        this.paymentMapper = paymentMapper;
        this.orderMapper = orderMapper;
        this.userDiscountMapper = userDiscountMapper;
        this.exceptionService = exceptionService;
    }

    @Override
    public List<PaymentDTO> getPayments(Long number, Long from) {
        LOG.debug("getPayments: number = {}, from = {}",
                number, from);

        exceptionService.checkPaymentNumberFrom(number, from);

        return paymentMapper.selects(number, from);
    }

    @Override
    public Long getPaymentCount() {
        LOG.debug("getPaymentCount");

        return paymentMapper.selectCount();
    }

    @Override
    public List<PaymentDTO> getPaymentsByUser(Long userId) {
        LOG.debug("getPaymentsByUser: user_id = {}",
                userId);

        exceptionService.checkPaymentUserId(userId);

        return paymentMapper.selectsByUser(userId);
    }

    @Override
    public List<PaymentDTO> getPaymentsByFilm(Long filmId) {
        LOG.debug("getPaymentsByFilm: film_id = {}",
                filmId);

        exceptionService.checkPaymentFilmId(filmId);

        return paymentMapper.selectsByFilm(filmId);
    }

    @Override
    public PaymentDTO getPayment(Long id) {
        LOG.debug("getPayment: id = {}",
                id);

        exceptionService.checkPaymentId(id);

        return paymentMapper.select(id);
    }

    @Override
    public void addPayment(BasketDTO basketDTO) {
        LOG.debug("addPayment: {}",
                basketDTO);

        exceptionService.checkPaymentWithoutId(basketDTO);

        Long time = System.currentTimeMillis();
        List<OrderDTO> orderDTOS = orderMapper.selectsByBasketIsMark(basketDTO.getId());
        for (OrderDTO orderDTO : orderDTOS){
            Integer totalPrice;
            if (orderDTO.getDiscountDTO() != null){

                totalPrice = orderDTO.getPriceByDiscount();

                // set 'used' in UserDiscount
                UserDiscountDTO userDiscountDTO = userDiscountMapper.selectByDiscount(orderDTO.getDiscountDTO().getId());
                if (userDiscountDTO == null){

                    // create new UserDiscount
                    userDiscountDTO = new UserDiscountDTO(orderDTO.getBasketDTO().getUserDTO(), orderDTO.getDiscountDTO(), true);
                    userDiscountMapper.insert(userDiscountDTO);
                } else {
                    userDiscountDTO.setUsed(true);
                    userDiscountMapper.update(userDiscountDTO);
                }
            }
            else {
                totalPrice = orderDTO.getFilmDTO().getPrice();
            }
            PaymentDTO paymentDTO = new PaymentDTO(orderDTO.getBasketDTO().getUserDTO(),
                    orderDTO.getFilmDTO(),
                    orderDTO.getDiscountDTO(),
                    totalPrice,
                    new Timestamp(time));
            paymentMapper.insert(paymentDTO);

            // delete order that just payed
            orderMapper.delete(orderDTO.getId());
        }
    }
}
