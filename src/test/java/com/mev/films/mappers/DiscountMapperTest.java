package com.mev.films.mappers;


import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.model.DiscountDTO;
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
public class DiscountMapperTest {

    @Autowired private DiscountMapper discountMapper;

    private DiscountDTO discountDTO1 = new DiscountDTO("code1");
    private DiscountDTO discountDTO2 = new DiscountDTO("code2");

    @Before
    public void setup(){
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        for (DiscountDTO discountDTO : discountDTOS){
            discountMapper.deleteDiscountByCode(discountDTO.getCode());
        }
    }

    @Test
    public void selectDiscountsTest(){
        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTOS.get(0).equals(discountDTO1));
        assertTrue("discountDTO2 = " + discountDTO2,
                discountDTOS.get(1).equals(discountDTO2));
    }

    @Test
    public void selectDiscountTest(){
        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        DiscountDTO discountDTO = discountMapper.selectDiscount(discountDTOS.get(1).getId())   ;
        assertTrue("discountDTO2 = " + discountDTOS.get(1).toString(),
                discountDTO.equals(discountDTOS.get(1)));
    }

    @Test
    public void insertDiscountTest(){
        discountMapper.insertDiscount(discountDTO1);

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        assertTrue("count = 1",
                discountDTOS.size() == 1);
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTOS.get(0).equals(discountDTO1));
    }

    @Test
    public void updateDiscount(){
        discountMapper.insertDiscount(discountDTO1);

        DiscountDTO getDiscountDTO = discountMapper.selectDiscountByCode(discountDTO1.getCode());
        getDiscountDTO.setCode("code11");

        discountMapper.updateDiscount(getDiscountDTO);

        DiscountDTO discountDTO = discountMapper.selectDiscountByCode("code11");
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTO.equals(getDiscountDTO));
    }

    @Test
    public void deleteDiscountByCodeTest(){
        discountMapper.insertDiscount(discountDTO1);

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        assertTrue("list count = 1",
                discountDTOS.size() == 1);

        discountMapper.deleteDiscountByCode(discountDTO1.getCode());

        discountDTOS = discountMapper.selectDiscounts();
        assertTrue("list count = 1",
                discountDTOS.size() == 0);
    }
}
