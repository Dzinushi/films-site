package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.PaymentMapper;
import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.model.PaymentDTO;
import com.mev.films.model.UserDiscountDTO;
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
    @Autowired private UserDiscountMapper userDiscountMapper;

    public PaymentServiceImpl(){

    }

    public PaymentServiceImpl(PaymentMapper paymentMapper, UserDiscountMapper userDiscountMapper) {
        this.paymentMapper = paymentMapper;
        this.userDiscountMapper = userDiscountMapper;
    }

    @Override
    public List<PaymentDTO> getPayments() {
        LOG.debug("getPayments");

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPayments();
        if (paymentDTOS != null) {
            for (PaymentDTO paymentDTO : paymentDTOS) {
                BasketServiceImpl.priceByDiscount(paymentDTO.getBasketDTO());
            }
        }
        return paymentDTOS;
    }

    @Override
    public List<PaymentDTO> getPaymentsByUser(Long userId) {
        LOG.debug("getPaymentsByUser: user_id = {}",
                userId);

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPaymentsByUser(userId);
        if (paymentDTOS != null) {
            for (PaymentDTO paymentDTO : paymentDTOS) {
                BasketServiceImpl.priceByDiscount(paymentDTO.getBasketDTO());
            }
        }

        return paymentDTOS;
    }

    @Override
    public List<PaymentDTO> getPaymentsByFilm(Long filmId) {
        LOG.debug("getPaymentsByFilm: film_id = {}",
                filmId);

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPaymentsByFilm(filmId);
        if (paymentDTOS != null) {
            for (PaymentDTO paymentDTO : paymentDTOS) {
                BasketServiceImpl.priceByDiscount(paymentDTO.getBasketDTO());
            }
        }

        return paymentDTOS;
    }

    @Override
    public PaymentDTO getPaymentByBasket(Long basketId) {
        LOG.debug("getPaymentByBasket: basket_id = {}",
                basketId);

        PaymentDTO paymentDTO = paymentMapper.selectPaymentByBasket(basketId);
        if (paymentDTO != null) {
            BasketServiceImpl.priceByDiscount(paymentDTO.getBasketDTO());
        }

        return paymentDTO;
    }

    @Override
    public PaymentDTO getPayment(Long id) {
        LOG.debug("getPayment: id = {}",
                id);

        PaymentDTO paymentDTO = paymentMapper.selectPaymentByBasket(id);
        if (paymentDTO != null) {
            BasketServiceImpl.priceByDiscount(paymentDTO.getBasketDTO());
        }

        return paymentDTO;
    }

    @Override
    public void addPayment(PaymentDTO paymentDTO) {
        LOG.debug("addPayment: paymentDTO = {}",
                paymentDTO);

        // set code for insert payment as "used"
        UserDiscountDTO userDiscountDTO = userDiscountMapper.selectUserDiscountByDiscount(
                paymentDTO.getBasketDTO().getDiscountDTO().getId());

        if (userDiscountDTO != null) {
            userDiscountDTO.setUsed(true);
            userDiscountMapper.updateUserDiscount(userDiscountDTO);
        }
        paymentMapper.insertPayment(paymentDTO);
    }

    @Override
    public void updatePayment(PaymentDTO paymentDTO) {
        LOG.debug("updatePayment: paymentDTO = {}",
                paymentDTO);

        PaymentDTO paymentDTOOld = paymentMapper.selectPayment(paymentDTO.getId());

        if (paymentDTOOld != null &&
                !paymentDTO.getBasketDTO().getDiscountDTO().equals(
                paymentDTOOld.getBasketDTO().getDiscountDTO())) {

            // set old discount code as free
            UserDiscountDTO userDiscountDTO = userDiscountMapper.selectUserDiscount(
                    paymentDTOOld.getBasketDTO().getDiscountDTO().getId());
            userDiscountDTO.setUsed(false);
            userDiscountMapper.updateUserDiscount(userDiscountDTO);

            // set new discount code as used
            userDiscountDTO = userDiscountMapper.selectUserDiscountByDiscount(
                    paymentDTO.getBasketDTO().getDiscountDTO().getId());
            userDiscountDTO.setUsed(true);

            userDiscountMapper.updateUserDiscount(userDiscountDTO);
        }

        paymentMapper.updatePayment(paymentDTO);
    }

    @Override
    public void deletePayment(Long id) {
        LOG.debug("deletePayment: id = {}",
                id);

        paymentMapper.deletePayment(id);
    }

    @Override
    public void deletePaymentByBasket(Long id) {

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
