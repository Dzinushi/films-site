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
public class OrderMapperTest {

    @Autowired private OrderMapper orderMapper;
    @Autowired private DiscountMapper discountMapper;
    @Autowired private FilmMapper filmMapper;
    @Autowired private UserMapper userMapper;
    @Autowired private UserRoleMapper userRoleMapper;

    private UserDTO userDTO1 = new UserDTO("user1", "password1", (short) 1);
    private UserDTO userDTO2 = new UserDTO("user2", "password2", (short) 1);
    private UserDTO userDTO3 = new UserDTO("user3", "password3", (short) 1);

    private UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");
    private UserRoleDTO userRoleDTO3 = new UserRoleDTO("user3", "ROLE_USER");

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 10, 100, "image1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre2", (short) 20, 200, "image2");
    private FilmDTO filmDTO3 = new FilmDTO("film3", "genre3", (short) 30, 300, "image3");

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.12F);
    private DiscountDTO discountDTO3 = new DiscountDTO("code3", 0.18F);

    private OrderDTO orderDTO1;
    private OrderDTO orderDTO2;
    private OrderDTO orderDTO3;
    private OrderDTO orderDTO4;

    @Before
    public void setup(){

        List<OrderDTO> orderDTOS = orderMapper.selectOrders();
        for (OrderDTO orderDTO : orderDTOS){
            orderMapper.deleteOrder(orderDTO.getId());
        }

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        for (DiscountDTO discountDTO : discountDTOS){
            discountMapper.deleteDiscount(discountDTO.getId());
        }

        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        for (FilmDTO filmDTO : filmDTOS){
            filmMapper.deleteFilm(filmDTO.getId());
        }

        List<UserDTO> userDTOS = userMapper.selectUsers();
        for (UserDTO userDTO : userDTOS){
            userMapper.deleteUser(userDTO.getId());
        }

        userMapper.insertUser(userDTO1);
        userMapper.insertUser(userDTO2);
        userMapper.insertUser(userDTO3);

        userRoleMapper.insertUserRole(userRoleDTO1);
        userRoleMapper.insertUserRole(userRoleDTO2);
        userRoleMapper.insertUserRole(userRoleDTO3);

        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);
        filmMapper.insertFilm(filmDTO3);

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);
        discountMapper.insertDiscount(discountDTO3);

        userDTOS = userMapper.selectUsersIdLogin();
        filmDTOS = filmMapper.selectFilms();
        discountDTOS = discountMapper.selectDiscounts();

        orderDTO1 = new OrderDTO(userDTOS.get(0), filmDTOS.get(0), discountDTOS.get(0), false);
        orderDTO2 = new OrderDTO(userDTOS.get(1), filmDTOS.get(1), discountDTOS.get(1), false);
        orderDTO3 = new OrderDTO(userDTOS.get(2), filmDTOS.get(2), discountDTOS.get(2), false);
        orderDTO4 = new OrderDTO(userDTOS.get(0), filmDTOS.get(1), discountDTOS.get(2), false);
    }

    @Test
    void selectOrdersTest(){

        orderMapper.insertOrder(orderDTO1);
        orderMapper.insertOrder(orderDTO2);
        orderMapper.insertOrder(orderDTO3);

        List<OrderDTO> orderDTOS = orderMapper.selectOrders();
        assertTrue("count = 3",
                orderDTOS.size() == 3);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(1).equals(orderDTO2));
        assertTrue("orderDTO3 = " + orderDTO3.toString(),
                orderDTOS.get(2).equals(orderDTO3));
    }

    @Test
    void selectOrderTest(){

        orderMapper.insertOrder(orderDTO1);
        orderMapper.insertOrder(orderDTO3);
        orderMapper.insertOrder(orderDTO2);

        List<OrderDTO> orderDTOS = orderMapper.selectOrders();
        OrderDTO orderDTO = orderMapper.selectOrder(orderDTOS.get(1).getId());
        assertTrue("orderDTO3 = " + orderDTO3.toString(),
                orderDTO.equals(orderDTO3));
    }

    @Test
    void selectOrderByUserTest(){

        orderMapper.insertOrder(orderDTO3);
        orderMapper.insertOrder(orderDTO1);
        orderMapper.insertOrder(orderDTO2);
        orderMapper.insertOrder(orderDTO4);

        List<OrderDTO> orderDTOS = orderMapper.selectOrderByUser(orderDTO1.getUserDTO().getId());
        assertTrue("count = 2",
                orderDTOS.size() == 2);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));
        assertTrue("orderDTO4 = " + orderDTO4.toString(),
                orderDTOS.get(1).equals(orderDTO4));
    }

    @Test
    void insertOrderTest(){

        orderMapper.insertOrder(orderDTO1);

        List<OrderDTO> orderDTOS = orderMapper.selectOrders();
        assertTrue("count = 1",
                orderDTOS.size() == 1);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));

        orderMapper.insertOrder(orderDTO2);

        orderDTOS = orderMapper.selectOrders();
        assertTrue("count = 2",
                orderDTOS.size() == 2);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(1).equals(orderDTO2));
    }

    @Test
    void deleteOrderTest(){

        // add 1 and delete 1
        orderMapper.insertOrder(orderDTO1);

        orderMapper.deleteOrder(orderDTO1.getId());

        List<OrderDTO> orderDTOS = orderMapper.selectOrders();
        assertTrue("count = 0",
                orderDTOS.size() == 0);

        // add 2 and delete 2
        orderMapper.insertOrder(orderDTO1);
        orderMapper.insertOrder(orderDTO2);

        orderMapper.deleteOrder(orderDTO1.getId());
        orderMapper.deleteOrder(orderDTO2.getId());

        orderDTOS = orderMapper.selectOrders();
        assertTrue("count = 0",
                orderDTOS.size() == 0);

        // add 2 and delete 1
        orderMapper.insertOrder(orderDTO1);
        orderMapper.insertOrder(orderDTO2);

        orderMapper.deleteOrder(orderDTO1.getId());

        orderDTOS = orderMapper.selectOrders();
        assertTrue("count = 1",
                orderDTOS.size() == 1);
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(0).equals(orderDTO2));
    }

    @Test
    void deleteOrderByUserTest(){

        // add 1 and delete 1
        orderMapper.insertOrder(orderDTO1);

        orderMapper.deleteOrderByUser(orderDTO1.getUserDTO().getId());

        List<OrderDTO> orderDTOS = orderMapper.selectOrders();
        assertTrue("count = 0",
                orderDTOS.size() == 0);

        // add 2 and delete 2
        orderMapper.insertOrder(orderDTO1);
        orderMapper.insertOrder(orderDTO2);

        orderMapper.deleteOrder(orderDTO1.getUserDTO().getId());
        orderMapper.deleteOrder(orderDTO2.getUserDTO().getId());

        orderDTOS = orderMapper.selectOrders();
        assertTrue("count = 0",
                orderDTOS.size() == 0);

        // add 2 and delete 1
        orderMapper.insertOrder(orderDTO1);
        orderMapper.insertOrder(orderDTO2);

        orderMapper.deleteOrderByUser(orderDTO1.getUserDTO().getId());

        orderDTOS = orderMapper.selectOrders();
        assertTrue("count = 1",
                orderDTOS.size() == 1);
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(0).equals(orderDTO2));
    }
}
