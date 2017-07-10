package com.mev.films.service;


import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.service.implement.DiscountServiceImpl;
import com.mev.films.service.interfaces.DiscountService;
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
public class DiscountServiceTest {

    @Autowired private DiscountService discountService;
    @Autowired private DiscountMapper discountMapperMock;

    private DiscountDTO discountDTO1 = new DiscountDTO("code1");
    private DiscountDTO discountDTO2 = new DiscountDTO("code2");

    @Before
    public void setup(){
        discountMapperMock = createNiceMock(DiscountMapper.class);
        discountService = new DiscountServiceImpl(discountMapperMock);
    }

    @Test
    public void getAllDiscountsTest(){

        expect(discountService.getAllDiscounts()).andStubAnswer(new IAnswer<List<DiscountDTO>>() {
            @Override
            public List<DiscountDTO> answer() throws Throwable {
                List<DiscountDTO> discountDTOS = new ArrayList<>();
                discountDTOS.add(discountDTO1);
                discountDTOS.add(discountDTO2);
                return discountDTOS;
            }
        });

        replay(discountMapperMock);

        discountService.addDiscount(discountDTO1);
        discountService.addDiscount(discountDTO2);

        List<DiscountDTO> discountDTOS = discountService.getAllDiscounts();
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTOS.get(0).equals(discountDTO1));
        assertTrue("discountDTO2 = " + discountDTO2,
                discountDTOS.get(1).equals(discountDTO2));

        verify(discountMapperMock);
    }

    @Test
    public void addDiscountTest(){

        expect(discountService.getDiscountByCode(discountDTO1.getCode())).andStubAnswer(new IAnswer<DiscountDTO>() {
            @Override
            public DiscountDTO answer() throws Throwable {
                return discountDTO1;
            }
        });

        replay(discountMapperMock);

        discountService.addDiscount(discountDTO1);

        DiscountDTO discountDTO = discountService.getDiscountByCode(discountDTO1.getCode());
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTO.equals(discountDTO1));

        verify(discountMapperMock);
    }

    @Test
    public void updateDiscount(){

        expect(discountService.getDiscountByCode(discountDTO2.getCode())).andStubAnswer(new IAnswer<DiscountDTO>() {
            @Override
            public DiscountDTO answer() throws Throwable {
                return discountDTO2;
            }
        });

        expect(discountService.getDiscountByCode("code22")).andStubAnswer(new IAnswer<DiscountDTO>() {
            @Override
            public DiscountDTO answer() throws Throwable {
                return new DiscountDTO("code22");
            }
        });

        replay(discountMapperMock);

        discountService.addDiscount(discountDTO2);

        DiscountDTO getDiscountDTO = discountService.getDiscountByCode(discountDTO2.getCode());
        getDiscountDTO.setCode("code22");

        discountService.updateDiscount(getDiscountDTO);

        DiscountDTO discountDTO = discountService.getDiscountByCode("code22");
        assertTrue("discountDTO2 = " + discountDTO2,
                discountDTO.equals(getDiscountDTO));

        verify(discountMapperMock);
    }

    @Test
    public void deleteDiscountByCodeTest(){

        expect(discountService.getAllDiscounts()).andStubAnswer(new IAnswer<List<DiscountDTO>>() {
            @Override
            public List<DiscountDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        replay(discountMapperMock);

        discountService.addDiscount(discountDTO1);

        discountService.deleteDiscountByCode(discountDTO1.getCode());

        List<DiscountDTO> discountDTOS = discountService.getAllDiscounts();
        assertTrue("list count = 0",
                discountDTOS.size() == 0);

        verify(discountMapperMock);
    }
}
