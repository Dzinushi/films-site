package com.mev.films.service;


import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.service.implement.DiscountServiceImpl;
import com.mev.films.service.implement.ExceptionServiceImpl;
import com.mev.films.service.interfaces.DiscountService;
import com.mev.films.service.interfaces.ExceptionService;
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
public class DiscountServiceTest {

    @Autowired private DiscountService discountService;
    @Autowired private DiscountMapper discountMapperMock;

    @Autowired private ExceptionService exceptionService;

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.2F);

    @Before
    public void setup(){
        discountMapperMock = createNiceMock(DiscountMapper.class);

        exceptionService = new ExceptionServiceImpl(discountMapperMock);

        discountService = new DiscountServiceImpl(discountMapperMock, exceptionService);

        discountDTO1.setId(1L);
        discountDTO2.setId(2L);
    }

    @Test
    public void getDiscountsTest(){
    }

    @Test
    public void getDiscount(){

        expect(discountMapperMock.selectDiscount(discountDTO1.getId())).andAnswer(new IAnswer<DiscountDTO>() {
            @Override
            public DiscountDTO answer() throws Throwable {
                return discountDTO1;
            }
        });

        replay(discountMapperMock);

        DiscountDTO discountDTO = discountService.getDiscount(discountDTO1.getId());
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTO.equals(discountDTO1));

        // check null
        try {
            discountService.getDiscount(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discount_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check null
        try {
            discountService.getDiscount(-7L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discount_id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(discountMapperMock);
    }

    @Test
    public void getDiscountByCode(){

        expect(discountMapperMock.selectDiscountByCode(discountDTO2.getCode())).andAnswer(new IAnswer<DiscountDTO>() {
            @Override
            public DiscountDTO answer() throws Throwable {
                return discountDTO2;
            }
        });

        replay(discountMapperMock);

        DiscountDTO discountDTO = discountService.getDiscountByCode(discountDTO2.getCode());
        assertTrue("discountDTO = " + discountDTO.toString(),
                discountDTO.equals(discountDTO2));

        // check null
        try {
            discountService.getDiscountByCode(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("code = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED).getMessage()));
        }

        verify(discountMapperMock);
    }

    @Test
    public void addDiscountTest(){

        replay(discountMapperMock);

        discountService.addDiscount(discountDTO1);

        // check null
        try {
            discountService.addDiscount(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check code null
        try {
            discountService.addDiscount(new DiscountDTO(null, discountDTO1.getValue()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO.code = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED).getMessage()));
        }

        // check value null
        try {
            discountService.addDiscount(new DiscountDTO(discountDTO1.getCode(), null));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO.value = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage()));
        }

        // check value less
        try {
            discountService.addDiscount(new DiscountDTO(discountDTO1.getCode(), 0.049F));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO.value = 0.049",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage()));
        }

        // check value more
        try {
            discountService.addDiscount(new DiscountDTO(discountDTO1.getCode(), 0.76F));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO.value = 0.076",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage()));
        }

        verify(discountMapperMock);
    }

    @Test
    public void updateDiscount(){

        replay(discountMapperMock);

        discountService.updateDiscount(discountDTO1);

        // check null
        try {
            discountService.updateDiscount(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check id null
        try {
            DiscountDTO discountDTO = new DiscountDTO(discountDTO1.getCode(), discountDTO1.getValue());
            discountService.updateDiscount(discountDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO.id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            DiscountDTO discountDTO = new DiscountDTO(discountDTO1.getCode(), discountDTO1.getValue());
            discountDTO.setId(-6L);
            discountService.updateDiscount(discountDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO.id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check code null
        try {
            DiscountDTO discountDTO = new DiscountDTO(null, discountDTO1.getValue());
            discountDTO.setId(6L);
            discountService.updateDiscount(discountDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO.code = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED).getMessage()));
        }

        // check value null
        try {
            DiscountDTO discountDTO = new DiscountDTO(discountDTO1.getCode(), null);
            discountDTO.setId(6L);
            discountService.updateDiscount(discountDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO.value = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage()));
        }

        // check value less
        try {
            DiscountDTO discountDTO = new DiscountDTO(discountDTO1.getCode(), 0.049F);
            discountDTO.setId(6L);
            discountService.updateDiscount(discountDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO.value = 0.049",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage()));
        }

        // check value more
        try {
            DiscountDTO discountDTO = new DiscountDTO(discountDTO1.getCode(), 0.76F);
            discountDTO.setId(6L);
            discountService.updateDiscount(discountDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discountDTO.value = 0.076",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED).getMessage()));
        }

        verify(discountMapperMock);
    }

    @Test
    public void deleteDiscountTest(){

        replay(discountMapperMock);

        discountService.deleteDiscount(discountDTO1.getId());

        // check id null
        try {
            discountService.deleteDiscount(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discount_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            discountService.deleteDiscount(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discount_id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(discountMapperMock);
    }

    @Test
    public void deleteDiscountByCodeTest(){

        replay(discountMapperMock);

        discountService.deleteDiscountByCode(discountDTO1.getCode());

        // check code null
        try {
            discountService.deleteDiscountByCode(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e) {
            assertTrue("discount_code = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED).getMessage()));
        }

        verify(discountMapperMock);
    }
}
