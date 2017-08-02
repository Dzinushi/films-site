package com.mev.films.service;


import com.mev.films.mappers.interfaces.*;
import com.mev.films.model.*;
import com.mev.films.service.implement.*;
import com.mev.films.service.interfaces.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
