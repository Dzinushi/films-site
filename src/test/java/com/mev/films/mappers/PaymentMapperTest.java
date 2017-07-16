package com.mev.films.mappers;


import com.mev.films.mappers.interfaces.*;
import com.mev.films.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class PaymentMapperTest {

    @Autowired private PaymentMapper paymentMapper;
    @Autowired private DiscountMapper discountMapper;
    @Autowired private FilmMapper filmMapper;
    @Autowired private UserMapper userMapper;

    private DiscountDTO discountDTO1 = new DiscountDTO("code1");
    private DiscountDTO discountDTO2 = new DiscountDTO("code2");

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 10, "url1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre2", (short) 200, 20, "url2");

    private UserDTO userDTO1 = new UserDTO("user1", "password1", (short) 1);
    private UserDTO userDTO2 = new UserDTO("user2", "password2", (short) 2);

    private UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");

    private PaymentDTO paymentDTO1;
    private PaymentDTO paymentDTO2;

    @Before
    public void setup(){
        List<PaymentDTO> paymentDTOS = paymentMapper.selectPayments();
        for (PaymentDTO paymentDTO : paymentDTOS){
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
            userMapper.deleteUser(userDTO.getId());
        }


        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);

        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        discountDTOS = discountMapper.selectDiscounts();
        filmDTOS = filmMapper.selectFilms();
        userDTOS = userMapper.selectUsersIdLogin();

        paymentDTO1 = new PaymentDTO(userDTOS.get(0), filmDTOS.get(0), discountDTOS.get(0), 1);
        paymentDTO2 = new PaymentDTO(userDTOS.get(1), filmDTOS.get(1), discountDTOS.get(1), 2);

    }

    @Test
    public void selectPaymentsDTOTest(){

        paymentMapper.insertPayment(paymentDTO1);
        paymentMapper.insertPayment(paymentDTO2);

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPayments();
        assertTrue("paymentDTO1 = " + paymentDTO1.toString(),
                paymentDTOS.get(0).equals(paymentDTO1));
        assertTrue("paymentDTO2 = " + paymentDTO2.toString(),
                paymentDTOS.get(1).equals(paymentDTO2));
    }

    @Test
    public void selectPaymentsDTOByUserTest(){

        paymentMapper.insertPayment(paymentDTO1);
        paymentMapper.insertPayment(paymentDTO2);

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPaymentsByUser(paymentDTO2.getUserDTO().getId());

        assertTrue("paymentDTO2 = " + paymentDTO2.toString(),
                paymentDTOS.get(0).equals(paymentDTO2));
    }

    @Test
    public void selectPaymentsDTOByFilmTest(){

        paymentMapper.insertPayment(paymentDTO1);
        paymentMapper.insertPayment(paymentDTO2);

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPaymentsByFilm(paymentDTO2.getFilmDTO().getId());

        assertTrue("paymentDTO2 = " + paymentDTO2.toString(),
                paymentDTOS.get(0).equals(paymentDTO2));
    }

    @Test
    public void selectPaymentDTOTest(){

        paymentMapper.insertPayment(paymentDTO1);
        paymentMapper.insertPayment(paymentDTO2);

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPayments();

        PaymentDTO paymentDTO = paymentMapper.selectPayment(paymentDTOS.get(0).getId());

        assertTrue("paymentDTO1 = " + paymentDTO1.toString(),
                paymentDTO.equals(paymentDTO1));
    }

    @Test
    public void insertPaymentTest(){

        paymentMapper.insertPayment(paymentDTO1);

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPayments();

        assertTrue("count = 1",
                paymentDTOS.size() == 1);
        assertTrue("paymentDTO1 = " + paymentDTO1.toString(),
                paymentDTOS.get(0).equals(paymentDTO1));
    }

    @Test
    public void updatePaymentTest(){

        paymentMapper.insertPayment(paymentDTO1);

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPayments();
        paymentDTOS.get(0).setUserDTO(paymentDTO2.getUserDTO());
        paymentDTOS.get(0).setFilmDTO(paymentDTO2.getFilmDTO());
        paymentDTOS.get(0).setDiscountDTO(paymentDTO2.getDiscountDTO());
        paymentDTOS.get(0).setCount(paymentDTO2.getCount());

        paymentMapper.updatePayment(paymentDTOS.get(0));

        PaymentDTO paymentDTO = paymentMapper.selectPayment(paymentDTOS.get(0).getId());

        assertTrue("count = 1",
                paymentDTOS.size() == 1);
        assertTrue("paymentDTO1 = " + paymentDTOS.get(0),
                paymentDTO.equals(paymentDTOS.get(0)));
    }

    @Test
    public void deletePaymentTest(){

        paymentMapper.insertPayment(paymentDTO1);

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPayments();
        paymentMapper.deletePayment(paymentDTOS.get(0).getId());

        paymentDTOS = paymentMapper.selectPayments();

        assertTrue("count = 0",
                paymentDTOS.size() == 0);
    }

    @Test
    public void deletePaymentByUserTest(){

        paymentMapper.insertPayment(paymentDTO1);

        paymentMapper.deletePaymentByUser(paymentDTO1.getUserDTO().getId());

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPayments();

        assertTrue("count = 0",
                paymentDTOS.size() == 0);
    }

    @Test
    public void deletePaymentByFilmTest(){

        paymentMapper.insertPayment(paymentDTO1);

        paymentMapper.deletePaymentByFilm(paymentDTO1.getFilmDTO().getId());

        List<PaymentDTO> paymentDTOS = paymentMapper.selectPayments();

        assertTrue("count = 0",
                paymentDTOS.size() == 0);
    }
}
