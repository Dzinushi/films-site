package com.mev.films.service;


import com.mev.films.mappers.interfaces.*;
import com.mev.films.model.*;
import com.mev.films.service.implement.BasketServiceImpl;
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
import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class PaymentServiceTest {

    @Autowired private PaymentMapper paymentMapperMock;
    @Autowired private UserDiscountMapper userDiscountMapperMock;

    @Autowired private PaymentService paymentService;

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.2F);

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 100, "url1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre2", (short) 200, 200, "url2");

    private UserDTO userDTO1 = new UserDTO("user1", "password1", (short) 1);
    private UserDTO userDTO2 = new UserDTO("user2", "password2", (short) 2);

    private UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");

    private BasketDTO basketDTO1;
    private BasketDTO basketDTO2;

    private PaymentDTO paymentDTO1;
    private PaymentDTO paymentDTO2;


    @Before
    public void setup(){

        userDiscountMapperMock = createNiceMock(UserDiscountMapper.class);
        paymentMapperMock = createNiceMock(PaymentMapper.class);

        paymentService = new PaymentServiceImpl(paymentMapperMock, userDiscountMapperMock);

        userDTO1.setId(1L);
        userDTO2.setId(2L);

        userRoleDTO1.setId(1L);
        userRoleDTO2.setId(2L);

        discountDTO1.setId(1L);
        discountDTO2.setId(2L);

        filmDTO1.setId(1L);
        filmDTO2.setId(2L);

        basketDTO1 = new BasketDTO(userDTO1, filmDTO1, discountDTO1);
        basketDTO2 = new BasketDTO(userDTO2, filmDTO2, discountDTO2);

        BasketServiceImpl.priceByDiscount(basketDTO1);
        BasketServiceImpl.priceByDiscount(basketDTO2);

        paymentDTO1 = new PaymentDTO(basketDTO1, 1);
        paymentDTO2 = new PaymentDTO(basketDTO2, 2);

        paymentDTO1.setId(1L);
        paymentDTO2.setId(2L);

        replay(userDiscountMapperMock);
    }

    @Test
    public void getPaymentsTest(){

        expect(paymentService.getPayments()).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                List<PaymentDTO> paymentDTOS = new ArrayList<>();
                paymentDTOS.add(paymentDTO1);
                paymentDTOS.add(paymentDTO2);

                return paymentDTOS;
            }
        });

        replay(paymentMapperMock);

        paymentService.addPayment(paymentDTO1);
        paymentService.addPayment(paymentDTO2);

        List<PaymentDTO> paymentDTOS = paymentService.getPayments();
        assertTrue("paymentDTO1 = " + paymentDTO1.toString(),
                paymentDTOS.get(0).equals(paymentDTO1));
        assertTrue("paymentDTO2 = " + paymentDTO2.toString(),
                paymentDTOS.get(1).equals(paymentDTO2));

        verify(paymentMapperMock);
        verify(userDiscountMapperMock);
    }

    @Test
    public void getPaymentsByUserTest(){

        expect(paymentService.getPaymentsByUser(paymentDTO2.getBasketDTO().getUserDTO().getId())).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                List<PaymentDTO> paymentDTOS = new ArrayList<>();
                paymentDTOS.add(paymentDTO2);

                return paymentDTOS;
            }
        });

        replay(paymentMapperMock);

        paymentService.addPayment(paymentDTO1);
        paymentService.addPayment(paymentDTO2);

        List<PaymentDTO> paymentDTOS = paymentService.getPaymentsByUser(paymentDTO2.getBasketDTO().getUserDTO().getId());

        assertTrue("paymentDTO2 = " + paymentDTO2.toString(),
                paymentDTOS.get(0).equals(paymentDTO2));

        verify(paymentMapperMock);
        verify(userDiscountMapperMock);
    }

    @Test
    public void getPaymentsByFilmTest(){

        expect(paymentService.getPaymentsByFilm(paymentDTO2.getBasketDTO().getFilmDTO().getId())).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                List<PaymentDTO> paymentDTOS = new ArrayList<>();
                paymentDTOS.add(paymentDTO2);

                return paymentDTOS;
            }
        });

        replay(paymentMapperMock);

        paymentService.addPayment(paymentDTO1);
        paymentService.addPayment(paymentDTO2);

        List<PaymentDTO> paymentDTOS = paymentService.getPaymentsByFilm(paymentDTO2.getBasketDTO().getFilmDTO().getId());

        assertTrue("paymentDTO2 = " + paymentDTO2.toString(),
                paymentDTOS.get(0).equals(paymentDTO2));

        verify(paymentMapperMock);
        verify(userDiscountMapperMock);
    }

    @Test
    public void getPaymentByBasketTest(){

        expect(paymentService.getPaymentByBasket(paymentDTO1.getBasketDTO().getId())).andStubAnswer(new IAnswer<PaymentDTO>() {
            @Override
            public PaymentDTO answer() throws Throwable {
                return paymentDTO1;
            }
        });

        replay(paymentMapperMock);

        paymentService.addPayment(paymentDTO1);
        paymentService.addPayment(paymentDTO2);

        PaymentDTO paymentDTO = paymentService.getPaymentByBasket(paymentDTO1.getBasketDTO().getId());

        assertTrue("paymentDTO1 = " + paymentDTO1.toString(),
                paymentDTO.equals(paymentDTO1));

        verify(paymentMapperMock);
        verify(userDiscountMapperMock);
    }

    @Test
    public void getPaymentTest(){

        expect(paymentService.getPayment(paymentDTO1.getId())).andStubAnswer(new IAnswer<PaymentDTO>() {
            @Override
            public PaymentDTO answer() throws Throwable {
                return paymentDTO1;
            }
        });

        replay(paymentMapperMock);

        paymentService.addPayment(paymentDTO1);
        paymentService.addPayment(paymentDTO2);

        PaymentDTO paymentDTO = paymentService.getPayment(paymentDTO1.getId());

        assertTrue("paymentDTO1 = " + paymentDTO1.toString(),
                paymentDTO.equals(paymentDTO1));

        verify(paymentMapperMock);
        verify(userDiscountMapperMock);
    }

    @Test
    public void addPaymentTest(){

        expect(paymentService.getPayments()).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                List<PaymentDTO> paymentDTOS = new ArrayList<>();
                paymentDTOS.add(paymentDTO1);

                return paymentDTOS;
            }
        });


        replay(paymentMapperMock);

        paymentService.addPayment(paymentDTO1);

        List<PaymentDTO> paymentDTOS = paymentService.getPayments();

        assertTrue("count = 1",
                paymentDTOS.size() == 1);
        assertTrue("paymentDTO1 = " + paymentDTO1.toString(),
                paymentDTOS.get(0).equals(paymentDTO1));

        verify(paymentMapperMock);
        verify(userDiscountMapperMock);
    }

    @Test
    public void updatePaymentTest(){

        expect(paymentService.getPayments()).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                List<PaymentDTO> paymentDTOS = new ArrayList<>();
                paymentDTOS.add(paymentDTO1);

                return paymentDTOS;
            }
        });

        expect(paymentService.getPayment(paymentDTO1.getId())).andStubAnswer(new IAnswer<PaymentDTO>() {
            @Override
            public PaymentDTO answer() throws Throwable {
                return paymentDTO2;
            }
        });

        replay(paymentMapperMock);

        paymentService.addPayment(paymentDTO1);

        List<PaymentDTO> paymentDTOS = paymentMapperMock.selectPayments();
        paymentDTOS.get(0).setBasketDTO(paymentDTO2.getBasketDTO());
        paymentDTOS.get(0).setCount(paymentDTO2.getCount());

        paymentService.updatePayment(paymentDTOS.get(0));

        PaymentDTO paymentDTO = paymentService.getPayment(paymentDTOS.get(0).getId());

        assertTrue("count = 1",
                paymentDTOS.size() == 1);
        assertTrue("paymentDTO1 = " + paymentDTOS.get(0),
                paymentDTO.equals(paymentDTOS.get(0)));

        verify(paymentMapperMock);
        verify(userDiscountMapperMock);
    }

    @Test
    public void deletePaymentTest(){

        expect(paymentService.getPayments()).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        expect(paymentService.getPayment(paymentDTO1.getId())).andStubAnswer(new IAnswer<PaymentDTO>() {
            @Override
            public PaymentDTO answer() throws Throwable {
                return paymentDTO2;
            }
        });

        replay(paymentMapperMock);

        paymentService.addPayment(paymentDTO1);

        PaymentDTO paymentDTO = paymentService.getPayment(paymentDTO1.getId());
        paymentService.deletePayment(paymentDTO.getId());

        List<PaymentDTO> paymentDTOS = paymentService.getPayments();

        assertTrue("count = 0",
                paymentDTOS.size() == 0);

        verify(paymentMapperMock);
        verify(userDiscountMapperMock);
    }

    @Test
    public void deletePaymentByUserTest(){

        expect(paymentService.getPayments()).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        expect(paymentService.getPayment(paymentDTO1.getId())).andStubAnswer(new IAnswer<PaymentDTO>() {
            @Override
            public PaymentDTO answer() throws Throwable {
                return paymentDTO2;
            }
        });

        replay(paymentMapperMock);

        paymentService.addPayment(paymentDTO1);

        PaymentDTO paymentDTO = paymentService.getPayment(paymentDTO1.getId());
        paymentService.deletePaymentByUser(paymentDTO.getBasketDTO().getUserDTO().getId());

        List<PaymentDTO> paymentDTOS = paymentService.getPayments();

        assertTrue("count = 0",
                paymentDTOS.size() == 0);

        verify(paymentMapperMock);
        verify(userDiscountMapperMock);
    }

    @Test
    public void deletePaymentByFilmTest(){

        expect(paymentService.getPayments()).andStubAnswer(new IAnswer<List<PaymentDTO>>() {
            @Override
            public List<PaymentDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        expect(paymentService.getPayment(paymentDTO1.getId())).andStubAnswer(new IAnswer<PaymentDTO>() {
            @Override
            public PaymentDTO answer() throws Throwable {
                return paymentDTO2;
            }
        });

        replay(paymentMapperMock);

        paymentService.addPayment(paymentDTO1);

        PaymentDTO paymentDTO = paymentService.getPayment(paymentDTO1.getId());
        paymentService.deletePaymentByFilm(paymentDTO.getBasketDTO().getFilmDTO().getId());

        List<PaymentDTO> paymentDTOS = paymentService.getPayments();

        assertTrue("count = 0",
                paymentDTOS.size() == 0);

        verify(paymentMapperMock);
        verify(userDiscountMapperMock);
    }
}
