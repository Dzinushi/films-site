package com.mev.films.mappers;


import com.mev.films.mappers.interfaces.*;
import com.mev.films.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class PaymentMapperTest {

    @Autowired private PaymentMapper paymentMapper;
    @Autowired private BasketMapper basketMapper;
    @Autowired private OrderMapper orderMapper;
    @Autowired private DiscountMapper discountMapper;
    @Autowired private FilmMapper filmMapper;
    @Autowired private UserMapper userMapper;

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.2F);
    private DiscountDTO discountDTO3 = new DiscountDTO("code3", 0.18F);

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 10, 100, "url1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre2", (short) 20, 200, "url2");
    private FilmDTO filmDTO3 = new FilmDTO("film3", "genre3", (short) 30, 150, "url3");

    private UserDTO userDTO1 = new UserDTO("user1", "password1", (short) 1);
    private UserDTO userDTO2 = new UserDTO("user2", "password2", (short) 2);
    private UserDTO userDTO3 = new UserDTO("user3", "password3", (short) 3);

    private List<OrderDTO> orderDTOS;

    private List<BasketDTO> basketDTOS;

    private List<PaymentDTO> paymentDTOS;

    @Before
    public void setup() throws InterruptedException {

        Long count = paymentMapper.selectPaymentsCount();

        List<PaymentDTO> payments = paymentMapper.selectPayments(count, 0L);
        for (PaymentDTO paymentDTO : payments){
            paymentMapper.deletePayment(paymentDTO.getId());
        }

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        for (DiscountDTO discountDTO : discountDTOS){
            discountMapper.deleteDiscountByCode(discountDTO.getCode());
        }

        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        for (FilmDTO filmDTO : filmDTOS){
            filmMapper.deleteFilmByImage(filmDTO.getImage());
        }

        List<UserDTO> userDTOS = userMapper.selectUsers();
        for (UserDTO userDTO : userDTOS){
            userMapper.deleteUserByLogin(userDTO.getLogin());
        }

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);
        discountMapper.insertDiscount(discountDTO3);

        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);
        filmMapper.insertFilm(filmDTO3);

        userMapper.insertUser(userDTO1);
        userMapper.insertUser(userDTO2);
        userMapper.insertUser(userDTO3);

        discountDTOS = discountMapper.selectDiscounts();
        filmDTOS = filmMapper.selectFilms();
        userDTOS = userMapper.selectUsersIdLogin();

        // added baskets
        basketDTOS = new ArrayList<>();
        basketDTOS.add(new BasketDTO(userDTOS.get(0)));
        basketDTOS.add(new BasketDTO(userDTOS.get(1)));
        basketDTOS.add(new BasketDTO(userDTOS.get(2)));

        for (BasketDTO basketDTO : basketDTOS){
            basketMapper.insertBasket(basketDTO);
        }

        count = basketMapper.selectBasketsCount();
        basketDTOS = basketMapper.selectBaskets(count, 0L);

        // added orders
        orderDTOS = new ArrayList<>();
        orderDTOS.add(new OrderDTO(basketDTOS.get(0), filmDTOS.get(0), discountDTOS.get(0), true));
        orderDTOS.add(new OrderDTO(basketDTOS.get(1), filmDTOS.get(0), discountDTOS.get(1), true));
        orderDTOS.add(new OrderDTO(basketDTOS.get(1), filmDTOS.get(1), null, true));
        orderDTOS.add(new OrderDTO(basketDTOS.get(1), filmDTOS.get(2), null, true));
        orderDTOS.add(new OrderDTO(basketDTOS.get(2), filmDTOS.get(0), null, true));
        orderDTOS.add(new OrderDTO(basketDTOS.get(2), filmDTOS.get(2), discountDTOS.get(2), true));
        orderDTOS.add(new OrderDTO(basketDTOS.get(2), filmDTOS.get(1), null, false));

        for (OrderDTO orderDTO : orderDTOS){
            if (orderDTO.getDiscountDTO() != null){
                Integer price = orderDTO.getFilmDTO().getPrice();
                Float discount = orderDTO.getDiscountDTO().getValue();
                Integer priceByDiscount = Math.round(price - (price * discount));
                orderDTO.setPriceByDiscount(priceByDiscount);
            }
            orderMapper.insertOrder(orderDTO);
        }

        // create payments (3 payments)
        int user2Add = 2;
        paymentDTOS = new ArrayList<>();
        for (BasketDTO basketDTO : basketDTOS){
            Long time = System.currentTimeMillis();
            List<OrderDTO> orders = orderMapper.selectOrdersByBasket(basketDTO.getId());
            for (OrderDTO orderDTO : orders){
                if (orderDTO.isMark()){
                    if (orderDTO.getBasketDTO().getUserDTO().getId() == 2 && user2Add != 0){
                        -- user2Add;
                    } else if (orderDTO.getBasketDTO().getUserDTO().getId() == 2 ||
                            orderDTO.getBasketDTO().getUserDTO().getId() == 3){
                        Thread.sleep(1L);
                        time = System.currentTimeMillis();
                    }

                    Integer totalPrice = orderDTO.getDiscountDTO() == null ? orderDTO.getFilmDTO().getPrice() : orderDTO.getPriceByDiscount();
                    PaymentDTO paymentDTO = new PaymentDTO(
                            orderDTO.getBasketDTO().getUserDTO(),
                            orderDTO.getFilmDTO(),
                            orderDTO.getDiscountDTO(),
                            totalPrice,
                            new Timestamp(time));
                    paymentDTOS.add(paymentDTO);
                }
            }
        }
    }

    @Test
    public void selectPaymentsTest(){

        for (PaymentDTO paymentDTO : paymentDTOS){
            paymentMapper.insertPayment(paymentDTO);
        }

        List<PaymentDTO> payments = paymentMapper.selectPayments(3L, 0L);
        assertTrue("count = 3",
                payments.size() == 3);
        assertTrue("paymentDTO1 = " + paymentDTOS.get(0).toString(),
                payments.get(0).equals(paymentDTOS.get(0)));
        assertTrue("paymentDTO2 = " + paymentDTOS.get(1).toString(),
                payments.get(1).equals(paymentDTOS.get(1)));
        assertTrue("paymentDTO3 = " + paymentDTOS.get(2).toString(),
                payments.get(2).equals(paymentDTOS.get(2)));

        payments = paymentMapper.selectPayments(3L, 3L);
        assertTrue("count = 3",
                payments.size() == 3);
        assertTrue("paymentDTO4 = " + paymentDTOS.get(3).toString(),
                payments.get(0).equals(paymentDTOS.get(3)));
        assertTrue("paymentDTO5 = " + paymentDTOS.get(4).toString(),
                payments.get(1).equals(paymentDTOS.get(4)));
        assertTrue("paymentDTO6 = " + paymentDTOS.get(5).toString(),
                payments.get(2).equals(paymentDTOS.get(5)));
    }

    @Test
    public void selectPaymentsCount(){

        for (PaymentDTO paymentDTO : paymentDTOS){
            paymentMapper.insertPayment(paymentDTO);
        }

        Long count = paymentMapper.selectPaymentsCount();

        assertTrue("count = 6",
                count == 6);
    }

    @Test
    public void selectPaymentsByUserTest(){

        for (PaymentDTO paymentDTO : paymentDTOS){
            paymentMapper.insertPayment(paymentDTO);
        }

        List<PaymentDTO> payments = paymentMapper.selectPaymentsByUser(paymentDTOS.get(1).getUserDTO().getId());
        assertTrue("count = 3",
                payments.size() == 3);
        assertTrue("paymentDTO2 = " + paymentDTOS.get(1).toString(),
                payments.get(0).equals(paymentDTOS.get(1)));
        assertTrue("paymentDTO3 = " + paymentDTOS.get(2).toString(),
                payments.get(1).equals(paymentDTOS.get(2)));
        assertTrue("paymentDTO4 = " + paymentDTOS.get(3).toString(),
                payments.get(2).equals(paymentDTOS.get(3)));
    }

    @Test
    public void selectPaymentsByFilmTest(){

        for (PaymentDTO paymentDTO : paymentDTOS){
            paymentMapper.insertPayment(paymentDTO);
        }

        List<PaymentDTO> payments = paymentMapper.selectPaymentsByFilm(paymentDTOS.get(0).getFilmDTO().getId());

        assertTrue("count = 3",
                payments.size() == 3);
        assertTrue("paymentDTO1 = " + paymentDTOS.get(0).toString(),
                payments.get(0).equals(paymentDTOS.get(0)));
        assertTrue("paymentDTO2 = " + paymentDTOS.get(1).toString(),
                payments.get(1).equals(paymentDTOS.get(1)));
        assertTrue("paymentDTO5 = " + paymentDTOS.get(4).toString(),
                payments.get(2).equals(paymentDTOS.get(4)));
    }

    @Test
    public void selectPaymentTest(){

        for (PaymentDTO paymentDTO : paymentDTOS){
            paymentMapper.insertPayment(paymentDTO);
        }

        Long count = paymentMapper.selectPaymentsCount();

        List<PaymentDTO> payments = paymentMapper.selectPayments(count, 0L);
        PaymentDTO paymentDTO = paymentMapper.selectPayment(payments.get(2).getId());
        assertTrue("paymentDTO1 = " + payments.get(2).toString(),
                paymentDTO.equals(payments.get(2)));
    }

    // need to check test
    @Test
    public void selectUsersPayingAboveMedianForLastMonthTest(){

        for (PaymentDTO paymentDTO : paymentDTOS){
            paymentMapper.insertPayment(paymentDTO);
//            System.out.printf("login=%s, price=%d, time=%s\n", paymentDTO.getUserDTO().getLogin(), paymentDTO.getTotalPrice(), paymentDTO.getTime());
        }

//        System.out.println();

        Timestamp paymentTime = paymentDTOS.get(0).getTime();
        Long time = paymentTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        List<UserDTO> userDTOS = paymentMapper.selectUsersPayingAboveMedianForLastMonth(calendar.get(Calendar.MONTH) + 1);

//        assertTrue("count = 1",
//                userDTOS.size() == 1);
//        assertTrue("userDTO.login = user2",
//                userDTOS.get(0).getLogin().equals("user2"));
//        assertTrue("userDTO.median = 215",
//                userDTOS.get(0).getMedian() == 215);

//        for (UserDTO userDTO : userDTOS){
//            System.out.println(userDTO);
//        }


    }

    // need to check test
    @Test
    public void selectPaymentsSortByUsersOrders(){

        for (PaymentDTO paymentDTO : paymentDTOS){
            paymentMapper.insertPayment(paymentDTO);
        }

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPaymentsSortByUsersOrders();
        for (PaymentDTO paymentDTO : paymentDTOS){
            System.out.printf("%s, total_price=%d, time=%s\n", paymentDTO.getUserDTO(), paymentDTO.getTotalPrice(), paymentDTO.getTime());
        }
    }

    // need to check test
    @Test
    public void selectTop5BestOrders(){

        for (PaymentDTO paymentDTO : paymentDTOS){
            paymentMapper.insertPayment(paymentDTO);
        }

        List<UserDTO> userDTOS = paymentMapper.selectTop5BestOrders();
        for (UserDTO userDTO : userDTOS){
            System.out.println(userDTO);
        }
    }

    @Test
    public void insertPaymentTest(){

        paymentMapper.insertPayment(paymentDTOS.get(0));

        Long count = paymentMapper.selectPaymentsCount();

        List<PaymentDTO> payments = paymentMapper.selectPayments(count, 0L);
        assertTrue("count = 1",
                payments.size() == 1);
        assertTrue("paymentDTO1 = " + paymentDTOS.get(0).toString(),
                payments.get(0).equals(paymentDTOS.get(0)));

        paymentMapper.insertPayment(paymentDTOS.get(1));

        count = paymentMapper.selectPaymentsCount();

        payments = paymentMapper.selectPayments(count, 0L);
        assertTrue("count = 2",
                payments.size() == 2);
        assertTrue("paymentDTO1 = " + paymentDTOS.get(0).toString(),
                payments.get(0).equals(paymentDTOS.get(0)));
        assertTrue("paymentDTO2 = " + paymentDTOS.get(1).toString(),
                payments.get(1).equals(paymentDTOS.get(1)));
    }

    @Test
    public void deletePaymentTest(){

        // add 1 delete 1
        paymentMapper.insertPayment(paymentDTOS.get(0));

        Long count = paymentMapper.selectPaymentsCount();

        List<PaymentDTO> payments = paymentMapper.selectPayments(count, 0L);
        paymentMapper.deletePayment(payments.get(0).getId());

        count = paymentMapper.selectPaymentsCount();
        assertTrue("count = 0",
                count == 0);

        // add 2 delete 2
        paymentMapper.insertPayment(paymentDTOS.get(0));
        paymentMapper.insertPayment(paymentDTOS.get(1));

        count = paymentMapper.selectPaymentsCount();

        payments = paymentMapper.selectPayments(count, 0L);
        paymentMapper.deletePayment(payments.get(0).getId());
        paymentMapper.deletePayment(payments.get(1).getId());

        count = paymentMapper.selectPaymentsCount();
        assertTrue("count = 0",
                count == 0);

        // add 2 delete 1
        paymentMapper.insertPayment(paymentDTOS.get(0));
        paymentMapper.insertPayment(paymentDTOS.get(1));

        count = paymentMapper.selectPaymentsCount();

        payments = paymentMapper.selectPayments(count, 0L);
        paymentMapper.deletePayment(payments.get(0).getId());

        count = paymentMapper.selectPaymentsCount();

        payments = paymentMapper.selectPayments(count, 0L);
        assertTrue("count = 1",
                payments.size() == 1);
        assertTrue("paymentDTO1 = " + paymentDTOS.get(1).toString(),
                payments.get(0).equals(paymentDTOS.get(1)));
    }
}
