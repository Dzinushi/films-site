package com.mev.films.service;

import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.mappers.interfaces.FilmMapper;
import com.mev.films.mappers.interfaces.OrderMapper;
import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.model.FilmDTO;
import com.mev.films.model.OrderDTO;
import com.mev.films.model.UserDTO;
import com.mev.films.service.implement.ExceptionServiceImpl;
import com.mev.films.service.implement.OrderServiceImpl;
import com.mev.films.service.interfaces.ExceptionService;
import com.mev.films.service.interfaces.OrderService;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.easymock.EasyMock.*;

public class OrderServiceTest {

    @Autowired private OrderService orderService;
    @Autowired private OrderMapper orderMapperMock;

    @Autowired private UserMapper userMapperMock;
    @Autowired private FilmMapper filmMapperMock;
    @Autowired private DiscountMapper discountMapperMock;

    @Autowired private ExceptionService exceptionService;

    private UserDTO userDTO1 = new UserDTO("user1", "pass1", (short) 1);
    private UserDTO userDTO2 = new UserDTO("user2", "pass2", (short) 2);
    private UserDTO userDTO3 = new UserDTO("user3", "pass3", (short) 3);
    private UserDTO userDTO4 = new UserDTO("user4", "pass4", (short) 4);

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 150, "image1.jpeg");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre2", (short) 200, 250, "image2.bmp");
    private FilmDTO filmDTO3 = new FilmDTO("film3", "genre3", (short) 250, 280, "image3.png");

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 18F);
    private DiscountDTO discountDTO3 = new DiscountDTO("code3", 12F);

    private OrderDTO orderDTO1;
    private OrderDTO orderDTO2;
    private OrderDTO orderDTO3;
    private OrderDTO orderDTO4;


    @Before
    public void setup(){
        orderMapperMock = createNiceMock(OrderMapper.class);
        userMapperMock = createNiceMock(UserMapper.class);
        filmMapperMock = createNiceMock(FilmMapper.class);
        discountMapperMock = createNiceMock(DiscountMapper.class);

        exceptionService = new ExceptionServiceImpl(userMapperMock, filmMapperMock, discountMapperMock);
        orderService = new OrderServiceImpl(orderMapperMock, exceptionService);

        userDTO1.setId(1L);
        userDTO2.setId(2L);
        userDTO3.setId(3L);
        userDTO4.setId(4L);

        filmDTO1.setId(1L);
        filmDTO2.setId(2L);
        filmDTO3.setId(3L);

        discountDTO1.setId(1L);
        discountDTO2.setId(2L);
        discountDTO3.setId(3L);

        orderDTO1 = new OrderDTO(userDTO1, filmDTO1, discountDTO1, true);
        orderDTO2 = new OrderDTO(userDTO1, filmDTO2, discountDTO2, true);
        orderDTO3 = new OrderDTO(userDTO2, filmDTO1, null, false);
        orderDTO4 = new OrderDTO(userDTO3, filmDTO2, null, true);

        orderDTO1.setId(1L);
        orderDTO2.setId(2L);
        orderDTO3.setId(3L);
        orderDTO4.setId(4L);
    }

    @Test
    public void getOrdersTest(){
    }

    @Test
    public void getOrderTest(){

        expect(orderMapperMock.selectOrder(orderDTO1.getId())).andReturn(orderDTO1);

        replay(orderMapperMock);

        OrderDTO orderDTO = orderService.getOrder(orderDTO1.getId());
        assertTrue("orderDTO = " + orderDTO1.toString(),
                orderDTO.equals(orderDTO1));

        // check id null
        try {
            orderService.getOrder(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            orderService.getOrder(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(orderMapperMock);
    }

    @Test
    public void getOrderByUserTest(){

        expect(orderMapperMock.selectOrderByUser(orderDTO1.getUserDTO().getId())).andAnswer(new IAnswer<List<OrderDTO>>() {
            @Override
            public List<OrderDTO> answer() throws Throwable {
                List<OrderDTO> orderDTOS = new ArrayList<>();
                orderDTOS.add(orderDTO1);
                orderDTOS.add(orderDTO2);
                return orderDTOS;
            }
        });

        replay(orderMapperMock);

        List<OrderDTO> orderDTOS = orderService.getOrderByUser(orderDTO1.getUserDTO().getId());
        assertTrue("count = 2",
                orderDTOS.size() == 2);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(1).equals(orderDTO2));

        // check user_id null
        try {
            orderService.getOrderByUser(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check user_id < 0
        try {
            orderService.getOrderByUser(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(orderMapperMock);
    }

    @Test
    public void getOrderByUserIsMarkTest(){

        expect(orderMapperMock.selectOrderByUserIsMark(userDTO1.getId())).andAnswer(new IAnswer<List<OrderDTO>>() {
            @Override
            public List<OrderDTO> answer() throws Throwable {
                List<OrderDTO> orderDTOS = new ArrayList<>();
                orderDTOS.add(orderDTO1);
                orderDTOS.add(orderDTO2);
                return orderDTOS;
            }
        });

        replay(orderMapperMock);

        List<OrderDTO> orderDTOS = orderService.getOrderByUserIsMark(orderDTO1.getUserDTO().getId());
        assertTrue("count = 2",
                orderDTOS.size() == 2);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(1).equals(orderDTO2));

        // check user_id null
        try {
            orderService.getOrderByUserIsMark(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check user_id < 0
        try {
            orderService.getOrderByUserIsMark(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(orderMapperMock);
    }

    @Test
    public void addOrderTest(){

        expect(userMapperMock.selectUser(userDTO1.getId())).andStubReturn(userDTO1);
        expect(userMapperMock.selectUser(userDTO2.getId())).andStubReturn(userDTO1);
        expect(userMapperMock.selectUser(userDTO3.getId())).andStubReturn(userDTO3);

        expect(filmMapperMock.selectFilm(filmDTO1.getId())).andStubReturn(filmDTO1);
        expect(filmMapperMock.selectFilm(filmDTO2.getId())).andStubReturn(filmDTO2);

        expect(discountMapperMock.selectDiscount(discountDTO1.getId())).andStubReturn(discountDTO1);
        expect(discountMapperMock.selectDiscount(discountDTO2.getId())).andStubReturn(discountDTO2);

        replay(userMapperMock);
        replay(filmMapperMock);
        replay(discountMapperMock);

        orderService.addOrder(orderDTO1);
        orderService.addOrder(orderDTO2);
        orderService.addOrder(orderDTO3);
        orderService.addOrder(orderDTO4);

        // check null
        try {
            orderService.addOrder(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check user null
        try {
            orderService.addOrder(new OrderDTO(null, orderDTO1.getFilmDTO(), orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.userDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check user_id null
        try {
            UserDTO userDTO = new UserDTO(userDTO1.getLogin(), userDTO1.getPassword(), userDTO1.getEnabled());
            orderService.addOrder(new OrderDTO(userDTO, orderDTO1.getFilmDTO(), orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.userDTO.id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check user_id < 0
        try {
            UserDTO userDTO = new UserDTO(userDTO1.getLogin(), userDTO1.getPassword(), userDTO1.getEnabled());
            userDTO.setId(-6L);
            orderService.addOrder(new OrderDTO(userDTO, orderDTO1.getFilmDTO(), orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.userDTO.id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check user not found
        try {
            orderService.addOrder(new OrderDTO(userDTO4, orderDTO1.getFilmDTO(), orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_ID_NOT_FOUND).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.userDTO = not found",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_ID_NOT_FOUND).getMessage()));
        }

        // check film null
        try {
            orderService.addOrder(new OrderDTO(orderDTO1.getUserDTO(), null, orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.filmDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check film_id null
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO1.getName(), filmDTO1.getGenre(), filmDTO1.getDuration(), filmDTO1.getPrice(), filmDTO1.getImage());
            orderService.addOrder(new OrderDTO(orderDTO1.getUserDTO(), filmDTO, orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.filmDTO.id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_WRONG_ID_PROVIDED).getMessage()));
        }

        // check film_id < 0
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO1.getName(), filmDTO1.getGenre(), filmDTO1.getDuration(), filmDTO1.getPrice(), filmDTO1.getImage());
            filmDTO.setId(-7L);
            orderService.addOrder(new OrderDTO(orderDTO1.getUserDTO(), filmDTO, orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.filmDTO.id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_WRONG_ID_PROVIDED).getMessage()));
        }

        // check film not found
        try {
            orderService.addOrder(new OrderDTO(orderDTO1.getUserDTO(), filmDTO3, orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_ID_NOT_FOUND).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.filmDTO = not found",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_ID_NOT_FOUND).getMessage()));
        }

        // check discount_id null
        try {
            DiscountDTO discountDTO = new DiscountDTO("code", 11F);
            orderService.addOrder(new OrderDTO(orderDTO1.getUserDTO(), orderDTO1.getFilmDTO(), discountDTO, orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.discount = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_WRONG_ID_PROVIDED).getMessage()));
        }

        // check discount_id < 0
        try {
            DiscountDTO discountDTO = new DiscountDTO("code", 11F);
            discountDTO.setId(-6L);
            orderService.addOrder(new OrderDTO(orderDTO1.getUserDTO(), orderDTO1.getFilmDTO(), discountDTO, orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.discount = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_WRONG_ID_PROVIDED).getMessage()));
        }

        // check discount not found
        try {
            orderService.addOrder(new OrderDTO(orderDTO1.getUserDTO(), orderDTO1.getFilmDTO(), discountDTO3, orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_ID_NOT_FOUND).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.discount = not found",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_ID_NOT_FOUND).getMessage()));
        }

        verify(userMapperMock);
        verify(filmMapperMock);
        verify(discountMapperMock);
    }

    @Test
    public void deleteOrderTest(){

        orderService.deleteOrder(orderDTO4.getId());

        // check id null
        try {
            orderService.deleteOrder(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            orderService.deleteOrder(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }
    }

    @Test
    public void deleteOrderByUserTest(){

        orderService.deleteOrder(orderDTO4.getId());

        // check id null
        try {
            orderService.deleteOrderByUser(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            orderService.deleteOrderByUser(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }
    }
}
