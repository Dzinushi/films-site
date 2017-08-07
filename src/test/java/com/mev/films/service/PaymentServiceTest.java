package com.mev.films.service;


import com.mev.films.mappers.interfaces.*;
import com.mev.films.model.*;
import com.mev.films.service.implement.ExceptionServiceImpl;
import com.mev.films.service.implement.PaymentServiceImpl;
import com.mev.films.service.interfaces.*;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class PaymentServiceTest {

    @Autowired private PaymentMapper paymentMapperMock;
    @Autowired private PaymentService paymentService;

    @Autowired private OrderMapper orderMapperMock;
    @Autowired private BasketMapper basketMapperMock;
    @Autowired private DiscountMapper discountMapperMock;
    @Autowired private UserDiscountMapper userDiscountMapperMock;

    @Autowired ExceptionService exceptionService;

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.2F);

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 100, "url1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre2", (short) 200, 200, "url2");

    private UserDTO userDTO1 = new UserDTO("user1", null, null);
    private UserDTO userDTO2 = new UserDTO("user2", null, null);
    private UserDTO userDTO3 = new UserDTO("user3", null, null);

    private UserDiscountDTO userDiscountDTO1;
    private UserDiscountDTO userDiscountDTO2;

    private List<BasketDTO> baskets;

    private List<OrderDTO> orders;

    private List<PaymentDTO> payments;

    @Before
    public void setup(){

        paymentMapperMock = createNiceMock(PaymentMapper.class);

        userDiscountMapperMock = createNiceMock(UserDiscountMapper.class);
        orderMapperMock = createNiceMock(OrderMapper.class);
        basketMapperMock = createNiceMock(BasketMapper.class);
        discountMapperMock = createNiceMock(DiscountMapper.class);

        exceptionService = new ExceptionServiceImpl(basketMapperMock, orderMapperMock, discountMapperMock, userDiscountMapperMock);

        paymentService = new PaymentServiceImpl(paymentMapperMock, orderMapperMock, userDiscountMapperMock, exceptionService);

        userDTO1.setId(1L);
        userDTO2.setId(2L);

        discountDTO1.setId(1L);
        discountDTO2.setId(2L);

        filmDTO1.setId(1L);
        filmDTO2.setId(2L);

        userDiscountDTO1 = new UserDiscountDTO(userDTO1, discountDTO1, false);
        userDiscountDTO2 = new UserDiscountDTO(userDTO2, discountDTO2, true);

        userDiscountDTO1.setId(1L);
        userDiscountDTO2.setId(2L);

        baskets = new ArrayList<>();
        baskets.add(new BasketDTO(userDTO1));
        baskets.add(new BasketDTO(userDTO2));
        baskets.add(new BasketDTO(userDTO3));

        baskets.get(0).setId(1L);
        baskets.get(1).setId(2L);
        baskets.get(2).setId(3L);

        orders = new ArrayList<>();
        orders.add(new OrderDTO(baskets.get(0), filmDTO1, discountDTO1, true));
        orders.add(new OrderDTO(baskets.get(1), filmDTO2, discountDTO2, true));
        orders.add(new OrderDTO(baskets.get(1), filmDTO1, null, false));
        orders.add(new OrderDTO(baskets.get(2), filmDTO2, null, true));

        orders.get(0).setPriceByDiscount(Math.round(filmDTO1.getPrice() - (filmDTO1.getPrice() * discountDTO1.getValue())));
        orders.get(1).setPriceByDiscount(Math.round(filmDTO2.getPrice() - (filmDTO2.getPrice() * discountDTO2.getValue())));

        orders.get(0).setId(1L);
        orders.get(1).setId(2L);
        orders.get(2).setId(3L);
        orders.get(3).setId(4L);

        payments = new ArrayList<>();
        long id = 1;
        for (BasketDTO basketDTO : baskets){
            for (OrderDTO orderDTO : orders){
                if (orderDTO.getBasketDTO().equals(basketDTO)){
                    Integer totalPrice = orderDTO.getDiscountDTO() == null ? orderDTO.getFilmDTO().getPrice() : orderDTO.getPriceByDiscount();
                    PaymentDTO paymentDTO = new PaymentDTO(orderDTO.getBasketDTO().getUserDTO(), orderDTO.getFilmDTO(), orderDTO.getDiscountDTO(), totalPrice);
                    paymentDTO.setId(id);
                    payments.add(paymentDTO);
                    ++id;
                }
            }
        }
    }

    @Test
    public void getPaymentsTest(){

        expect(paymentMapperMock.selectPayments(2L, 1L)).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                List<PaymentDTO> paymentDTOS = new ArrayList<>();
                paymentDTOS.add(payments.get(1));
                paymentDTOS.add(payments.get(2));
                return paymentDTOS;
            }
        });

        replay(paymentMapperMock);

        List<PaymentDTO> paymentDTOS = paymentService.getPayments(2L, 1L);
        assertTrue("count = 2",
                paymentDTOS.size() == 2);
        assertTrue("paymentDTO1 = " + payments.get(1).toString(),
                paymentDTOS.get(0).equals(payments.get(1)));
        assertTrue("paymentDTO2 = " + payments.get(2).toString(),
                paymentDTOS.get(1).equals(payments.get(2)));

        // check number null
        try{
            paymentService.getPayments(null, 1L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_WRONG_NUMBER_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_WRONG_NUMBER_PROVIDED).getMessage()));
        }

        // check number < 0
        try{
            paymentService.getPayments(-5L, 1L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_WRONG_NUMBER_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = -5L",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_WRONG_NUMBER_PROVIDED).getMessage()));
        }

        // check number > 100
        try{
            paymentService.getPayments(101L, 1L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_NUMBER_VALUE_MORE_THAN_100).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = 101",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_NUMBER_VALUE_MORE_THAN_100).getMessage()));
        }

        // check from null
        try{
            paymentService.getPayments(2L, null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_FROM_WRONG_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("from = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_FROM_WRONG_PROVIDED).getMessage()));
        }

        // check from < 0
        try{
            paymentService.getPayments(2L, -5L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_FROM_WRONG_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("from = -5",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_FROM_WRONG_PROVIDED).getMessage()));
        }
    }

    @Test
    public void getPaymentsByUserTest(){

        expect(paymentMapperMock.selectPaymentsByUser(userDTO1.getId())).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                List<PaymentDTO> paymentDTOS = new ArrayList<>();
                paymentDTOS.add(payments.get(0));
                paymentDTOS.add(payments.get(1));
                return paymentDTOS;
            }
        });

        replay(paymentMapperMock);

        List<PaymentDTO> paymentDTOS = paymentService.getPaymentsByUser(userDTO1.getId());
        assertTrue("count = 2",
                paymentDTOS.size() == 2);
        assertTrue("paymentDTO1 = " + payments.get(0),
                paymentDTOS.get(0).equals(payments.get(0)));
        assertTrue("paymentDTO2 = " + payments.get(1),
                paymentDTOS.get(1).equals(payments.get(1)));

        // check user_id null
        try{
            paymentService.getPaymentsByUser(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check user_id < 0
        try{
            paymentService.getPaymentsByUser(-7L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(paymentMapperMock);
    }

    @Test
    public void getPaymentsByFilmTest(){

        expect(paymentMapperMock.selectPaymentsByFilm(filmDTO2.getId())).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                List<PaymentDTO> paymentDTOS = new ArrayList<>();
                paymentDTOS.add(payments.get(1));
                paymentDTOS.add(payments.get(2));
                return paymentDTOS;
            }
        });

        replay(paymentMapperMock);

        List<PaymentDTO> paymentDTOS = paymentService.getPaymentsByFilm(filmDTO2.getId());
        assertTrue("count = 2",
                paymentDTOS.size() == 2);
        assertTrue("paymentDTO2 = " + payments.get(1).toString(),
                paymentDTOS.get(0).equals(payments.get(1)));
        assertTrue("paymentDTO3 = " + payments.get(2).toString(),
                paymentDTOS.get(1).equals(payments.get(2)));

        // check film_id null
        try{
            paymentService.getPaymentsByFilm(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_FILM_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("film_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_FILM_WRONG_ID_PROVIDED).getMessage()));
        }

        // check film_id null
        try{
            paymentService.getPaymentsByFilm(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_FILM_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("film_id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_FILM_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(paymentMapperMock);
    }

    @Test
    public void getPaymentTest(){

        expect(paymentMapperMock.selectPayment(payments.get(1).getId())).andReturn(payments.get(1));

        replay(paymentMapperMock);

        PaymentDTO paymentDTO = paymentService.getPayment(payments.get(1).getId());
        assertTrue("paymentDTO2 = " + payments.get(1).toString(),
                paymentDTO.equals(payments.get(1)));

        // check id null
        try{
            paymentService.getPayment(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try{
            paymentService.getPayment(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(paymentMapperMock);
    }

    @Test
    public void addPaymentTest(){

        expect(basketMapperMock.selectBasket(baskets.get(0).getId())).andStubReturn(baskets.get(0));
        expect(basketMapperMock.selectBasket(baskets.get(1).getId())).andStubReturn(baskets.get(1));
        expect(basketMapperMock.selectBasket(baskets.get(2).getId())).andStubReturn(baskets.get(2));
        expect(orderMapperMock.selectOrdersByBasketIsMark(userDTO1.getId())).andStubAnswer(new IAnswer<List<OrderDTO>>() {
            @Override
            public List<OrderDTO> answer() throws Throwable {
                List<OrderDTO> orderDTOS = new ArrayList<>();
                orderDTOS.add(orders.get(0));
                return orderDTOS;
            }
        });
        expect(orderMapperMock.selectOrdersByBasketIsMark(userDTO2.getId())).andStubAnswer(new IAnswer<List<OrderDTO>>() {
            @Override
            public List<OrderDTO> answer() throws Throwable {
                List<OrderDTO> orderDTOS = new ArrayList<>();
                orderDTOS.add(orders.get(1));
                return orderDTOS;
            }
        });
        expect(discountMapperMock.selectDiscount(discountDTO1.getId())).andStubReturn(discountDTO1);
        expect(discountMapperMock.selectDiscount(discountDTO2.getId())).andStubReturn(discountDTO2);
        expect(userDiscountMapperMock.selectUserDiscountByDiscount(discountDTO1.getId())).andStubReturn(userDiscountDTO1);
        expect(userDiscountMapperMock.selectUserDiscountByDiscount(discountDTO2.getId())).andStubReturn(userDiscountDTO2);

        replay(basketMapperMock);
        replay(orderMapperMock);
        replay(userDiscountMapperMock);
        replay(discountMapperMock);

        paymentService.addPayment(baskets.get(0));

        // check basket null
        try{
            paymentService.addPayment(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_BASKET_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("paymentDTO.basketDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_BASKET_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check basket_id null
        try{
            paymentService.addPayment(new BasketDTO(userDTO1));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("paymentDTO.basket_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage()));
        }

        // check basket_id < 0
        try{
            BasketDTO basketDTO = new BasketDTO(userDTO1);
            basketDTO.setId(-6L);
            paymentService.addPayment(basketDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("paymentDTO.user_id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage()));
        }

        // check basket_id not found
        try{
            BasketDTO basketDTO = new BasketDTO(userDTO1);
            basketDTO.setId(4L);
            paymentService.addPayment(basketDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_BASKET_NOT_FOUND).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("paymentDTO.basket_id not found",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_BASKET_NOT_FOUND).getMessage()));
        }

        // check basket_id compare false
        try{
            UserDTO userDTO = new UserDTO(userDTO2.getLogin(), null, null);
            userDTO.setId(1L);
            BasketDTO basketDTO = new BasketDTO(userDTO);
            basketDTO.setId(1L);
            paymentService.addPayment(basketDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_BASKET_COMPARE_FALSE).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("paymentDTO.basketDTO compare false",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_BASKET_COMPARE_FALSE).getMessage()));
        }

        // check orderDTOS not found
        try{
            paymentService.addPayment(baskets.get(2));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_ORDER_NOT_FOUND).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("paymentDTO: orderDTO by basketDTO not found",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_ORDER_NOT_FOUND).getMessage()));
        }

        // check userDiscountDTO: discount just used
        try{
            paymentService.addPayment(baskets.get(1));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_DISCOUNT_ALREADY_USED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("paymentDTO: userDiscountDTO already used",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.PAYMENT_ERROR_DISCOUNT_ALREADY_USED).getMessage()));
        }
    }
}
