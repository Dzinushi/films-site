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

        List<DiscountDTO> discountDTOS = discountMapper.selects();
        for (DiscountDTO discountDTO : discountDTOS){
            discountMapper.delete(discountDTO.getId());
        }
    }

    @Test
    public void selectDiscountsTest(){

        discountMapper.insert(discountDTO1);
        discountMapper.insert(discountDTO2);
        discountMapper.insert(discountDTO3);

        List<DiscountDTO> discountDTOS = discountMapper.selects();
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

        discountMapper.insert(discountDTO1);
        discountMapper.insert(discountDTO3);
        discountMapper.insert(discountDTO2);

        List<DiscountDTO> discountDTOS = discountMapper.selects();
        DiscountDTO discountDTO = discountMapper.select(discountDTOS.get(1).getId());
        assertTrue("discountDTO3 = " + discountDTO3.toString(),
                discountDTO.equals(discountDTO3));
    }

    @Test
    public void selectDiscountByCodeTest(){

        discountMapper.insert(discountDTO1);
        discountMapper.insert(discountDTO3);
        discountMapper.insert(discountDTO2);

        DiscountDTO discountDTO = discountMapper.selectByCode(discountDTO3.getCode());
        assertTrue("discountDTO3 = " + discountDTO3.toString(),
                discountDTO.equals(discountDTO3));
    }

    @Test
    public void insertDiscountTest(){

        discountMapper.insert(discountDTO1);

        List<DiscountDTO> discountDTOS = discountMapper.selects();
        assertTrue("count = 1",
                discountDTOS.size() == 1);
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTOS.get(0).equals(discountDTO1));

        discountMapper.insert(discountDTO2);

        discountDTOS = discountMapper.selects();
        assertTrue("count = 2",
                discountDTOS.size() == 2);
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTOS.get(0).equals(discountDTO1));
        assertTrue("discountDTO2 = " + discountDTO2,
                discountDTOS.get(1).equals(discountDTO2));
    }

    @Test
    public void updateDiscount(){

        discountMapper.insert(discountDTO1);

        DiscountDTO getDiscountDTO = discountMapper.selectByCode(discountDTO1.getCode());
        getDiscountDTO.setCode("code11");

        discountMapper.update(getDiscountDTO);

        DiscountDTO discountDTO = discountMapper.selectByCode("code11");
        assertTrue("discountDTO1 = " + discountDTO1,
                discountDTO.equals(getDiscountDTO));
    }

    @Test
    public void deleteDiscountTest(){

        // insert 1 delete 1
        discountMapper.insert(discountDTO1);

        List<DiscountDTO> discountDTOS = discountMapper.selects();
        discountMapper.delete(discountDTOS.get(0).getId());
        discountDTOS = discountMapper.selects();
        assertTrue("count = 0",
                discountDTOS.size() == 0);

        // insert 2 delete 2
        discountMapper.insert(discountDTO2);
        discountMapper.insert(discountDTO1);

        discountDTOS = discountMapper.selects();
        discountMapper.delete(discountDTOS.get(0).getId());
        discountMapper.delete(discountDTOS.get(1).getId());
        discountDTOS = discountMapper.selects();
        assertTrue("count = 0",
                discountDTOS.size() == 0);

        // insert 2 delete 1
        discountMapper.insert(discountDTO2);
        discountMapper.insert(discountDTO1);

        discountDTOS = discountMapper.selects();
        discountMapper.delete(discountDTOS.get(0).getId());
        discountDTOS = discountMapper.selects();
        assertTrue("count = 1",
                discountDTOS.size() == 1);
        assertTrue("discountDTO1 = " + discountDTO1.toString(),
                discountDTOS.get(0).equals(discountDTO1));
    }

    @Test
    public void deleteDiscountByCodeTest(){

        // insert 1 delete 1
        discountMapper.insert(discountDTO1);

        discountMapper.deleteByCode(discountDTO1.getCode());
        List<DiscountDTO> discountDTOS = discountMapper.selects();
        assertTrue("count = 0",
                discountDTOS.size() == 0);

        // insert 2 delete 2
        discountMapper.insert(discountDTO2);
        discountMapper.insert(discountDTO1);

        discountMapper.deleteByCode(discountDTO1.getCode());
        discountMapper.deleteByCode(discountDTO2.getCode());
        discountDTOS = discountMapper.selects();
        assertTrue("count = 0",
                discountDTOS.size() == 0);

        // insert 2 delete 1
        discountMapper.insert(discountDTO2);
        discountMapper.insert(discountDTO1);

        discountMapper.deleteByCode(discountDTO2.getCode());
        discountDTOS = discountMapper.selects();
        assertTrue("count = 1",
                discountDTOS.size() == 1);
        assertTrue("discountDTO1 = " + discountDTO1.toString(),
                discountDTOS.get(0).equals(discountDTO1));
    }
}
