package com.mev.films.service;


import com.mev.films.mappers.interfaces.*;
import com.mev.films.model.*;
import com.mev.films.service.implement.*;
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
public class BasketServiceTest {

    @Autowired private BasketService basketService;
    @Autowired private BasketMapper basketMapperMock;

    @Autowired private OrderMapper orderMapperMock;

    @Autowired private ExceptionService exceptionService;

    private static UserDTO userDTO1 = new UserDTO("user1", null, null);
    private static UserDTO userDTO2 = new UserDTO("user2", null, null);
    private static UserDTO userDTO3 = new UserDTO("user3", null, null);

    private BasketDTO basketDTO1;
    private BasketDTO basketDTO2;

    @Before
    public void setup(){
        basketMapperMock = createNiceMock(BasketMapper.class);
        orderMapperMock = createNiceMock(OrderMapper.class);

        exceptionService = new ExceptionServiceImpl(orderMapperMock);

        basketService = new BasketServiceImpl(basketMapperMock, exceptionService);

        userDTO1.setId(1L);
        userDTO2.setId(2L);
        userDTO3.setId(3L);

        basketDTO1 = new BasketDTO(userDTO1);
        basketDTO2 = new BasketDTO(userDTO2);

        basketDTO1.setId(1L);
        basketDTO2.setId(2L);
    }

    @Test
    public void getBasketsTest(){
    }

    @Test
    public void getBasketTest(){

        expect(basketMapperMock.selectBasket(basketDTO2.getId())).andReturn(basketDTO2);

        replay(basketMapperMock);

        BasketDTO basketDTO = basketService.getBasket(basketDTO2.getId());
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTO.equals(basketDTO2));

        // check id null
        try {
            basketService.getBasket(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basket_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            basketService.getBasket(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basket_id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(basketMapperMock);
    }

    @Test
    public void getBasketByUserTest() {

        expect(basketMapperMock.selectBasketByUser(basketDTO1.getUserDTO().getId())).andReturn(basketDTO1);

        replay(basketMapperMock);

        BasketDTO basketDTO = basketService.getBasketByUser(basketDTO1.getUserDTO().getId());
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTO.equals(basketDTO1));

        verify(basketMapperMock);
    }

    @Test
    public void addBasketTest(){

        expect(orderMapperMock.selectOrderByUser(basketDTO1.getUserDTO().getId())).andStubAnswer(new IAnswer<List<OrderDTO>>() {
            @Override
            public List<OrderDTO> answer() throws Throwable {
                List<OrderDTO> orderDTOS = new ArrayList<>();
                orderDTOS.add(new OrderDTO());
                return orderDTOS;
            }
        });

        replay(orderMapperMock);

        basketService.addBasket(basketDTO1);

        // check null
        try {
            basketService.addBasket(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basketDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check user null
        try {
            BasketDTO basketDTO = new BasketDTO(null);
            basketDTO.setId(1L);
            basketService.addBasket(basketDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basketDTO.id < 0",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check user_id null
        try {
            UserDTO userDTO = new UserDTO(null, null, null);
            BasketDTO basketDTO = new BasketDTO(userDTO);
            basketDTO.setId(1L);
            basketService.addBasket(basketDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basketDTO.userDTO.id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check user_id < 0
        try {
            UserDTO userDTO = new UserDTO(null, null, null);
            userDTO.setId(-6L);
            BasketDTO basketDTO = new BasketDTO(userDTO);
            basketDTO.setId(1L);
            basketService.addBasket(basketDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basketDTO.userDTO.id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check user orders not found
        try {
            BasketDTO basketDTO = new BasketDTO(userDTO3);
            basketDTO.setId(1L);
            basketService.addBasket(basketDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_ORDERS_NOT_FOUND_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("basketDTO.orderDTOS = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_ORDERS_NOT_FOUND_EXCEPTION).getMessage()));
        }

        verify(orderMapperMock);
    }

    @Test
    public void deleteBasketTest(){

        replay(basketMapperMock);

        basketService.deleteBasket(basketDTO1.getId());

        // check id null
        try {
            basketService.deleteBasket(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            basketService.deleteBasket(-8L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = -8",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(basketMapperMock);
    }

    @Test
    public void deleteBasketByUserTest(){

        replay(basketMapperMock);

        basketService.deleteBasketByUser(basketDTO1.getUserDTO().getId());

        // check id null
        try {
            basketService.deleteBasketByUser(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            basketService.deleteBasketByUser(-8L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = -8",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.BASKET_ERROR_USER_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(basketMapperMock);
    }
}
