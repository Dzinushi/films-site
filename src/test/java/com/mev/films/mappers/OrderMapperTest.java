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
    @Autowired private BasketMapper basketMapper;

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

    private BasketDTO basketDTO1;
    private BasketDTO basketDTO2;
    private BasketDTO basketDTO3;

    private OrderDTO orderDTO1;
    private OrderDTO orderDTO2;
    private OrderDTO orderDTO3;
    private OrderDTO orderDTO4;

    @Before
    public void setup(){

        List<OrderDTO> orderDTOS = orderMapper.selectAll();
        for (OrderDTO orderDTO : orderDTOS){
            orderMapper.delete(orderDTO.getId());
        }

        List<DiscountDTO> discountDTOS = discountMapper.selects();
        for (DiscountDTO discountDTO : discountDTOS){
            discountMapper.delete(discountDTO.getId());
        }

        List<FilmDTO> filmDTOS = filmMapper.selects();
        for (FilmDTO filmDTO : filmDTOS){
            filmMapper.delete(filmDTO.getId());
        }

        List<UserDTO> userDTOS = userMapper.selects();
        for (UserDTO userDTO : userDTOS){
            userMapper.deleteByLogin(userDTO.getLogin());
        }

        userMapper.insert(userDTO1);
        userMapper.insert(userDTO2);
        userMapper.insert(userDTO3);

        userRoleMapper.insert(userRoleDTO1);
        userRoleMapper.insert(userRoleDTO2);
        userRoleMapper.insert(userRoleDTO3);

        filmMapper.insert(filmDTO1);
        filmMapper.insert(filmDTO2);
        filmMapper.insert(filmDTO3);

        discountMapper.insert(discountDTO1);
        discountMapper.insert(discountDTO2);
        discountMapper.insert(discountDTO3);

        userDTOS = userMapper.selectsIdLogin();
        filmDTOS = filmMapper.selects();
        discountDTOS = discountMapper.selects();

        basketDTO1 = new BasketDTO(userDTOS.get(0));
        basketDTO2 = new BasketDTO(userDTOS.get(1));
        basketDTO3 = new BasketDTO(userDTOS.get(2));

        basketMapper.insert(basketDTO1);
        basketMapper.insert(basketDTO2);
        basketMapper.insert(basketDTO3);

        List<BasketDTO> basketDTOS = basketMapper.selectAll();

        orderDTO1 = new OrderDTO(basketDTOS.get(0), filmDTOS.get(0), discountDTOS.get(0), false);
        orderDTO2 = new OrderDTO(basketDTOS.get(1), filmDTOS.get(1), discountDTOS.get(1), false);
        orderDTO3 = new OrderDTO(basketDTOS.get(2), filmDTOS.get(2), discountDTOS.get(2), false);
        orderDTO4 = new OrderDTO(basketDTOS.get(0), filmDTOS.get(1), discountDTOS.get(2), false);
    }

    @Test
    public void selectOrdersTest(){

        orderMapper.insert(orderDTO1);
        orderMapper.insert(orderDTO2);
        orderMapper.insert(orderDTO3);

        List<OrderDTO> orderDTOS = orderMapper.selects(1L, 0L);
        assertTrue("count = 1",
                orderDTOS.size() == 1);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));

        orderDTOS = orderMapper.selects(2L, 1L);
        assertTrue("count = 2",
                orderDTOS.size() == 2);
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(0).equals(orderDTO2));
        assertTrue("orderDTO3 = " + orderDTO3.toString(),
                orderDTOS.get(1).equals(orderDTO3));
    }

    @Test
    public void selectOrdersCountTest(){

        Long count = orderMapper.selectsCount();
        assertTrue("count = 0",
                count == 0);

        orderMapper.insert(orderDTO1);

        count = orderMapper.selectsCount();
        assertTrue("count = 1",
                count == 1);

        orderMapper.insert(orderDTO2);
        orderMapper.insert(orderDTO3);

        count = orderMapper.selectsCount();
        assertTrue("count = 3",
                count == 3);
    }

    @Test
    public void selectOrderTest(){

        orderMapper.insert(orderDTO1);
        orderMapper.insert(orderDTO3);
        orderMapper.insert(orderDTO2);

        List<OrderDTO> orderDTOS = orderMapper.selectAll();
        OrderDTO orderDTO = orderMapper.select(orderDTOS.get(1).getId());
        assertTrue("orderDTO3 = " + orderDTO3.toString(),
                orderDTO.equals(orderDTO3));
    }

    @Test
    public void selectOrderByBasketTest(){

        orderMapper.insert(orderDTO3);
        orderMapper.insert(orderDTO1);
        orderMapper.insert(orderDTO2);
        orderMapper.insert(orderDTO4);

        List<OrderDTO> orderDTOS = orderMapper.selectsByBasket(orderDTO1.getBasketDTO().getId());
        assertTrue("count = 2",
                orderDTOS.size() == 2);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));
        assertTrue("orderDTO4 = " + orderDTO4.toString(),
                orderDTOS.get(1).equals(orderDTO4));
    }

    @Test
    public void insertOrderTest(){

        orderMapper.insert(orderDTO1);

        List<OrderDTO> orderDTOS = orderMapper.selectAll();
        assertTrue("count = 1",
                orderDTOS.size() == 1);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));

        orderMapper.insert(orderDTO2);

        orderDTOS = orderMapper.selectAll();
        assertTrue("count = 2",
                orderDTOS.size() == 2);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(1).equals(orderDTO2));
    }

    @Test
    public void deleteOrderTest(){

        // add 1 and delete 1
        orderMapper.insert(orderDTO1);

        List<OrderDTO> orderDTOS = orderMapper.selectAll();
        orderMapper.delete(orderDTOS.get(0).getId());

        Long count = orderMapper.selectsCount();
        assertTrue("count = 0",
                count == 0);

        // add 2 and delete 2
        orderMapper.insert(orderDTO1);
        orderMapper.insert(orderDTO2);

        orderDTOS = orderMapper.selectAll();
        orderMapper.delete(orderDTOS.get(0).getId());
        orderMapper.delete(orderDTOS.get(1).getId());

        count = orderMapper.selectsCount();
        assertTrue("count = 0",
                count == 0);

        // add 2 and delete 1
        orderMapper.insert(orderDTO1);
        orderMapper.insert(orderDTO2);

        orderDTOS = orderMapper.selectAll();
        orderMapper.delete(orderDTOS.get(0).getId());

        orderDTOS = orderMapper.selectAll();
        assertTrue("count = 1",
                orderDTOS.size() == 1);
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(0).equals(orderDTO2));
    }

    @Test
    public void deleteOrderByBasketTest(){

        // add 1 and delete 1
        orderMapper.insert(orderDTO1);

        List<OrderDTO> orderDTOS = orderMapper.selectAll();
        orderMapper.deleteByBasket(orderDTOS.get(0).getBasketDTO().getId());

        Long count = orderMapper.selectsCount();
        assertTrue("count = 0",
                count == 0);

        // add 2 and delete 2
        orderMapper.insert(orderDTO1);
        orderMapper.insert(orderDTO2);

        orderDTOS = orderMapper.selectAll();
        orderMapper.deleteByBasket(orderDTOS.get(0).getBasketDTO().getId());
        orderMapper.deleteByBasket(orderDTOS.get(1).getBasketDTO().getId());

        count = orderMapper.selectsCount();
        assertTrue("count = 0",
                count == 0);

        // add 2 and delete 1
        orderMapper.insert(orderDTO1);
        orderMapper.insert(orderDTO2);

        orderDTOS = orderMapper.selectAll();
        orderMapper.deleteByBasket(orderDTOS.get(0).getBasketDTO().getId());

        orderDTOS = orderMapper.selectAll();
        assertTrue("count = 1",
                orderDTOS.size() == 1);
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(0).equals(orderDTO2));
    }
}
