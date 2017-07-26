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

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.2F);
    private DiscountDTO discountDTO3 = new DiscountDTO("code3", 0.18F);

    @Before
    public void setup(){

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        for (DiscountDTO discountDTO : discountDTOS){
            discountMapper.deleteDiscount(discountDTO.getId());
        }
    }

    @Test
    public void selectDiscountsTest(){

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);
        discountMapper.insertDiscount(discountDTO3);

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        assertTrue("count = 3",
                discountDTOS.size() == 3);
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTOS.get(0).equals(discountDTO1));
        assertTrue("discountDTO2 = " + discountDTO2,
                discountDTOS.get(1).equals(discountDTO2));
        assertTrue("discountDTO3 = " + discountDTO3,
                discountDTOS.get(2).equals(discountDTO3));
    }

    @Test
    public void selectDiscountTest(){

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO3);
        discountMapper.insertDiscount(discountDTO2);

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        DiscountDTO discountDTO = discountMapper.selectDiscount(discountDTOS.get(1).getId());
        assertTrue("discountDTO3 = " + discountDTO3.toString(),
                discountDTO.equals(discountDTO3));
    }

    @Test
    public void selectDiscountByCodeTest(){

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO3);
        discountMapper.insertDiscount(discountDTO2);

        DiscountDTO discountDTO = discountMapper.selectDiscountByCode(discountDTO3.getCode());
        assertTrue("discountDTO3 = " + discountDTO3.toString(),
                discountDTO.equals(discountDTO3));
    }

    @Test
    public void insertDiscountTest(){

        discountMapper.insertDiscount(discountDTO1);

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        assertTrue("count = 1",
                discountDTOS.size() == 1);
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTOS.get(0).equals(discountDTO1));

        discountMapper.insertDiscount(discountDTO2);

        discountDTOS = discountMapper.selectDiscounts();
        assertTrue("count = 2",
                discountDTOS.size() == 2);
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTOS.get(0).equals(discountDTO1));
        assertTrue("discountDTO2 = " + discountDTO2,
                discountDTOS.get(1).equals(discountDTO2));
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
    public void deleteDiscountTest(){

        // insert 1 delete 1
        discountMapper.insertDiscount(discountDTO1);

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        discountMapper.deleteDiscount(discountDTOS.get(0).getId());
        discountDTOS = discountMapper.selectDiscounts();
        assertTrue("count = 0",
                discountDTOS.size() == 0);

        // insert 2 delete 2
        discountMapper.insertDiscount(discountDTO2);
        discountMapper.insertDiscount(discountDTO1);

        discountDTOS = discountMapper.selectDiscounts();
        discountMapper.deleteDiscount(discountDTOS.get(0).getId());
        discountMapper.deleteDiscount(discountDTOS.get(1).getId());
        discountDTOS = discountMapper.selectDiscounts();
        assertTrue("count = 0",
                discountDTOS.size() == 0);

        // insert 2 delete 1
        discountMapper.insertDiscount(discountDTO2);
        discountMapper.insertDiscount(discountDTO1);

        discountDTOS = discountMapper.selectDiscounts();
        discountMapper.deleteDiscount(discountDTOS.get(0).getId());
        discountDTOS = discountMapper.selectDiscounts();
        assertTrue("count = 1",
                discountDTOS.size() == 1);
        assertTrue("discountDTO1 = " + discountDTO1.toString(),
                discountDTOS.get(0).equals(discountDTO1));
    }

    @Test
    public void deleteDiscountByCodeTest(){

        // insert 1 delete 1
        discountMapper.insertDiscount(discountDTO1);

        discountMapper.deleteDiscountByCode(discountDTO1.getCode());
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        assertTrue("count = 0",
                discountDTOS.size() == 0);

        // insert 2 delete 2
        discountMapper.insertDiscount(discountDTO2);
        discountMapper.insertDiscount(discountDTO1);

        discountMapper.deleteDiscountByCode(discountDTO1.getCode());
        discountMapper.deleteDiscountByCode(discountDTO2.getCode());
        discountDTOS = discountMapper.selectDiscounts();
        assertTrue("count = 0",
                discountDTOS.size() == 0);

        // insert 2 delete 1
        discountMapper.insertDiscount(discountDTO2);
        discountMapper.insertDiscount(discountDTO1);

        discountMapper.deleteDiscountByCode(discountDTO2.getCode());
        discountDTOS = discountMapper.selectDiscounts();
        assertTrue("count = 1",
                discountDTOS.size() == 1);
        assertTrue("discountDTO1 = " + discountDTO1.toString(),
                discountDTOS.get(0).equals(discountDTO1));
    }
}
