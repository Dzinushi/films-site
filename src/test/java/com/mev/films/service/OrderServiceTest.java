package com.mev.films.service;

import com.mev.films.mappers.interfaces.*;
import com.mev.films.model.*;
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
    @Autowired private BasketMapper basketMapperMock;

    @Autowired private ExceptionService exceptionService;

    private UserDTO userDTO1 = new UserDTO("user1", null, null);
    private UserDTO userDTO2 = new UserDTO("user2", null, null);
    private UserDTO userDTO3 = new UserDTO("user3", null, null);
    private UserDTO userDTO4 = new UserDTO("user4", null, null);

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 150, "image1.jpeg");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre2", (short) 200, 250, "image2.bmp");
    private FilmDTO filmDTO3 = new FilmDTO("film3", "genre3", (short) 250, 280, "image3.png");

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.18F);
    private DiscountDTO discountDTO3 = new DiscountDTO("code3", 0.12F);

    private BasketDTO basketDTO1;
    private BasketDTO basketDTO2;
    private BasketDTO basketDTO3;

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
        basketMapperMock = createNiceMock(BasketMapper.class);

        exceptionService = new ExceptionServiceImpl(userMapperMock, filmMapperMock, discountMapperMock);
        orderService = new OrderServiceImpl(orderMapperMock, basketMapperMock, exceptionService);

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

        basketDTO1 = new BasketDTO(userDTO1);
        basketDTO2 = new BasketDTO(userDTO2);
        basketDTO3 = new BasketDTO(userDTO3);

        basketDTO1.setId(1L);
        basketDTO2.setId(2L);
        basketDTO3.setId(3L);

        orderDTO1 = new OrderDTO(basketDTO1, filmDTO1, discountDTO1, true);
        orderDTO2 = new OrderDTO(basketDTO1, filmDTO2, discountDTO2, true);
        orderDTO3 = new OrderDTO(basketDTO2, filmDTO1, null, false);
        orderDTO4 = new OrderDTO(basketDTO3, filmDTO2, null, true);

        orderDTO1.setId(1L);
        orderDTO2.setId(2L);
        orderDTO3.setId(3L);
        orderDTO4.setId(4L);
    }

    @Test
    public void getOrdersTest(){

        expect(orderMapperMock.selects(2L, 1L)).andStubAnswer(new IAnswer<List<OrderDTO>>() {
            @Override
            public List<OrderDTO> answer() throws Throwable {
                List<OrderDTO> orderDTOS = new ArrayList<>();
                orderDTOS.add(orderDTO2);
                orderDTOS.add(orderDTO3);
                return orderDTOS;
            }
        });

        replay(orderMapperMock);

        List<OrderDTO> orderDTOS = orderService.getOrders(2L, 1L);
        assertTrue("count = 2",
                orderDTOS.size() == 2);
        assertTrue("orderDTO1 = " + orderDTO2.toString(),
                orderDTOS.get(0).equals(orderDTO2));
        assertTrue("orderDTO2 = " + orderDTO3.toString(),
                orderDTOS.get(1).equals(orderDTO3));

        // check number null
        try{
            orderService.getOrders(null, 1L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_NUMBER_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_NUMBER_PROVIDED).getMessage()));
        }

        // check number < 0
        try{
            orderService.getOrders(-7L, 1L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_NUMBER_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_NUMBER_PROVIDED).getMessage()));
        }

        // check number > 100
        try{
            orderService.getOrders(101L, 1L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_NUMBER_VALUE_MORE_THAN_100).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = 101",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_NUMBER_VALUE_MORE_THAN_100).getMessage()));
        }

        // check from null
        try{
            orderService.getOrders(2L, null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_FROM_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("from = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_FROM_PROVIDED).getMessage()));
        }

        // check from < 0
        try{
            orderService.getOrders(2L, -6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_FROM_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("from = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_WRONG_FROM_PROVIDED).getMessage()));
        }
    }

    @Test
    public void getOrdersCountTest(){
    }

    @Test
    public void getOrderTest(){

        expect(orderMapperMock.select(orderDTO2.getId())).andReturn(orderDTO2);

        replay(orderMapperMock);

        OrderDTO orderDTO = orderService.getOrder(orderDTO2.getId());
        assertTrue("orderDTO = " + orderDTO2.toString(),
                orderDTO.equals(orderDTO2));
        assertTrue("orderDTO.priceByDiscount = 205",
                orderDTO.getPriceByDiscount().equals(205));

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
    public void getOrderByBasketTest(){

        expect(orderMapperMock.selectsByBasket(orderDTO1.getBasketDTO().getId())).andAnswer(new IAnswer<List<OrderDTO>>() {
            @Override
            public List<OrderDTO> answer() throws Throwable {
                List<OrderDTO> orderDTOS = new ArrayList<>();
                orderDTOS.add(orderDTO1);
                orderDTOS.add(orderDTO2);
                return orderDTOS;
            }
        });

        replay(orderMapperMock);

        List<OrderDTO> orderDTOS = orderService.getOrderByBasket(orderDTO1.getBasketDTO().getId());
        assertTrue("count = 2",
                orderDTOS.size() == 2);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));
        assertTrue("orderDTO.priceByDiscount = 128",
                orderDTOS.get(0).getPriceByDiscount().equals(128));
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(1).equals(orderDTO2));
        assertTrue("orderDTO.priceByDiscount = 205",
                orderDTOS.get(1).getPriceByDiscount().equals(205));

        // check basket_id null
        try {
            orderService.getOrderByBasket(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basket_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage()));
        }

        // check basket_id < 0
        try {
            orderService.getOrderByBasket(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basket_id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(orderMapperMock);
    }

    @Test
    public void getOrderByBasketIsMarkTest(){

        expect(orderMapperMock.selectsByBasketIsMark(basketDTO1.getId())).andAnswer(new IAnswer<List<OrderDTO>>() {
            @Override
            public List<OrderDTO> answer() throws Throwable {
                List<OrderDTO> orderDTOS = new ArrayList<>();
                orderDTOS.add(orderDTO1);
                orderDTOS.add(orderDTO2);
                return orderDTOS;
            }
        });

        replay(orderMapperMock);

        List<OrderDTO> orderDTOS = orderService.getOrderByBasketIsMark(orderDTO1.getBasketDTO().getId());
        assertTrue("count = 2",
                orderDTOS.size() == 2);
        assertTrue("orderDTO1 = " + orderDTO1.toString(),
                orderDTOS.get(0).equals(orderDTO1));
        assertTrue("orderDTO.priceByDiscount = 128",
                orderDTOS.get(0).getPriceByDiscount().equals(128));
        assertTrue("orderDTO2 = " + orderDTO2.toString(),
                orderDTOS.get(1).equals(orderDTO2));
        assertTrue("orderDTO.priceByDiscount = 205",
                orderDTOS.get(1).getPriceByDiscount().equals(205));

        // check basket_id null
        try {
            orderService.getOrderByBasketIsMark(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basket_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage()));
        }

        // check basket_id < 0
        try {
            orderService.getOrderByBasketIsMark(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basket_id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(orderMapperMock);
    }

    @Test
    public void addOrderTest(){

        expect(userMapperMock.selectIdLogin(userDTO1.getId())).andStubReturn(userDTO1);
        expect(userMapperMock.selectIdLogin(userDTO2.getId())).andStubReturn(userDTO2);
        expect(userMapperMock.selectIdLogin(userDTO3.getId())).andStubReturn(userDTO3);

        expect(filmMapperMock.select(filmDTO1.getId())).andStubReturn(filmDTO1);
        expect(filmMapperMock.select(filmDTO2.getId())).andStubReturn(filmDTO2);

        expect(discountMapperMock.select(discountDTO1.getId())).andStubReturn(discountDTO1);
        expect(discountMapperMock.select(discountDTO2.getId())).andStubReturn(discountDTO2);

        expect(basketMapperMock.selectByUser(basketDTO1.getUserDTO().getId())).andStubReturn(basketDTO1);
        expect(basketMapperMock.selectByUser(basketDTO2.getUserDTO().getId())).andStubReturn(basketDTO2);
        expect(basketMapperMock.selectByUser(basketDTO3.getUserDTO().getId())).andStubReturn(basketDTO3);

        replay(userMapperMock);
        replay(filmMapperMock);
        replay(discountMapperMock);
        replay(basketMapperMock);

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

        // check basket null
        try {
            orderService.addOrder(new OrderDTO(null, orderDTO1.getFilmDTO(), orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.basketDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check user null
        try {
            BasketDTO basketDTO = new BasketDTO(null);
            orderService.addOrder(new OrderDTO(basketDTO, orderDTO1.getFilmDTO(), orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.userDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check user_id null
        try {
            UserDTO userDTO = new UserDTO(userDTO1.getLogin(), null, null);
            BasketDTO basketDTO = new BasketDTO(userDTO);
            orderService.addOrder(new OrderDTO(basketDTO, orderDTO1.getFilmDTO(), orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.userDTO.id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check user_id < 0
        try {
            UserDTO userDTO = new UserDTO(userDTO1.getLogin(), null, null);
            userDTO.setId(-6L);
            BasketDTO basketDTO = new BasketDTO(userDTO);
            orderService.addOrder(new OrderDTO(basketDTO, orderDTO1.getFilmDTO(), orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.userDTO.id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check user not found
        try {
            orderService.addOrder(new OrderDTO(new BasketDTO(userDTO4), orderDTO1.getFilmDTO(), orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_ID_NOT_FOUND).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.userDTO = not found",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_ID_NOT_FOUND).getMessage()));
        }

        // check userDTO compare false
        try {
            UserDTO userDTO = new UserDTO(userDTO2.getLogin(), null, null);
            userDTO.setId(1L);
            orderService.addOrder(new OrderDTO(new BasketDTO(userDTO), orderDTO1.getFilmDTO(), orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_COMPARE_FALSE).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.userDTO compare false",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_USER_COMPARE_FALSE).getMessage()));
        }

        // check film null
        try {
            orderService.addOrder(new OrderDTO(orderDTO1.getBasketDTO(), null, orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.filmDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check film_id null
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO1.getName(), filmDTO1.getGenre(), filmDTO1.getDuration(), filmDTO1.getPrice(), filmDTO1.getImage());
            orderService.addOrder(new OrderDTO(orderDTO1.getBasketDTO(), filmDTO, orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.filmDTO.id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_WRONG_ID_PROVIDED).getMessage()));
        }

        // check film_id < 0
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO1.getName(), filmDTO1.getGenre(), filmDTO1.getDuration(), filmDTO1.getPrice(), filmDTO1.getImage());
            filmDTO.setId(-7L);
            orderService.addOrder(new OrderDTO(orderDTO1.getBasketDTO(), filmDTO, orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.filmDTO.id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_WRONG_ID_PROVIDED).getMessage()));
        }

        // check film not found
        try {
            orderService.addOrder(new OrderDTO(orderDTO1.getBasketDTO(), filmDTO3, orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_ID_NOT_FOUND).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.filmDTO = not found",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_ID_NOT_FOUND).getMessage()));
        }

        // check filmDTO compare false
        try {
            FilmDTO filmDTO = new FilmDTO(filmDTO2.getName(), filmDTO1.getGenre(), filmDTO1.getDuration(), filmDTO1.getPrice(), filmDTO1.getImage());
            filmDTO.setId(1L);
            orderService.addOrder(new OrderDTO(orderDTO1.getBasketDTO(), filmDTO, orderDTO1.getDiscountDTO(), orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_COMPARE_FALSE).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.filmDTO compare false",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_FILM_COMPARE_FALSE).getMessage()));
        }

        // check discount_id null
        try {
            DiscountDTO discountDTO = new DiscountDTO("code", 11F);
            orderService.addOrder(new OrderDTO(orderDTO1.getBasketDTO(), orderDTO1.getFilmDTO(), discountDTO, orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.discount = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_WRONG_ID_PROVIDED).getMessage()));
        }

        // check discount_id < 0
        try {
            DiscountDTO discountDTO = new DiscountDTO("code", 11F);
            discountDTO.setId(-6L);
            orderService.addOrder(new OrderDTO(orderDTO1.getBasketDTO(), orderDTO1.getFilmDTO(), discountDTO, orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.discount = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_WRONG_ID_PROVIDED).getMessage()));
        }

        // check discount not found
        try {
            orderService.addOrder(new OrderDTO(orderDTO1.getBasketDTO(), orderDTO1.getFilmDTO(), discountDTO3, orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_ID_NOT_FOUND).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.discount = not found",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_ID_NOT_FOUND).getMessage()));
        }

        try {
            DiscountDTO discountDTO = new DiscountDTO(discountDTO2.getCode(), discountDTO1.getValue());
            discountDTO.setId(1L);
            orderService.addOrder(new OrderDTO(orderDTO1.getBasketDTO(), orderDTO1.getFilmDTO(), discountDTO, orderDTO1.isMark()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_COMPARE_FALSE).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("orderDTO.discountDTO compare false",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_DISCOUNT_COMPARE_FALSE).getMessage()));
        }

        verify(userMapperMock);
        verify(filmMapperMock);
        verify(discountMapperMock);
        verify(basketMapperMock);
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
    public void deleteOrderByBasketTest(){

        orderService.deleteOrderByBasket(orderDTO4.getBasketDTO().getId());

        // check id null
        try {
            orderService.deleteOrderByBasket(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            orderService.deleteOrderByBasket(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED).getMessage()));
        }
    }
}
