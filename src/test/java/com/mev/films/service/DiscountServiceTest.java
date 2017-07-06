package com.mev.films.service;


import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.service.interfaces.DiscountService;
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
public class DiscountServiceTest {

    @Autowired private DiscountService discountService;

    private DiscountDTO discountDTO1 = new DiscountDTO("code1");
    private DiscountDTO discountDTO2 = new DiscountDTO("code2");

    @Before
    public void setup(){
        List<DiscountDTO> discountDTOS = discountService.getAllDiscounts();
        for (DiscountDTO discountDTO : discountDTOS){
            discountService.deleteDiscountByCode(discountDTO.getCode());
        }
    }

    @Test
    public void getAllDiscountsTest(){
        discountService.addDiscount(discountDTO1);
        discountService.addDiscount(discountDTO2);

        List<DiscountDTO> discountDTOS = discountService.getAllDiscounts();
        assertTrue("code1 = " + discountDTO1.getCode(),
                discountDTOS.get(0).getCode().equals(discountDTO1.getCode()));
        assertTrue("code2 = " + discountDTO2.getCode(),
                discountDTOS.get(1).getCode().equals(discountDTO2.getCode()));
    }

    @Test
    public void addDiscountTest(){
        discountService.addDiscount(discountDTO1);

        DiscountDTO discountDTO = discountService.getDiscountByCode(discountDTO1.getCode());
        assertTrue("code1 = " + discountDTO1.getCode(),
                discountDTO.getCode().equals(discountDTO1.getCode()));
    }

    @Test
    public void updateDiscount(){
        discountService.addDiscount(discountDTO1);

        DiscountDTO getDiscountDTO = discountService.getDiscountByCode(discountDTO1.getCode());
        getDiscountDTO.setCode("code11");

        discountService.updateDiscount(getDiscountDTO);

        DiscountDTO discountDTO = discountService.getDiscountByCode("code11");
        assertTrue("code1 = " + discountDTO1.getCode(),
                discountDTO.getCode().equals("code11"));
    }

    @Test
    public void deleteDiscountByCodeTest(){
        discountService.addDiscount(discountDTO1);

        List<DiscountDTO> discountDTOS = discountService.getAllDiscounts();
        assertTrue("list count = 1",
                discountDTOS.size() == 1);

        discountService.deleteDiscountByCode(discountDTO1.getCode());

        discountDTOS = discountService.getAllDiscounts();
        assertTrue("list count = 1",
                discountDTOS.size() == 0);
    }
}
