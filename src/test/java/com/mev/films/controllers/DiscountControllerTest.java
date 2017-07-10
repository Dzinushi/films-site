package com.mev.films.controllers;

import com.mev.films.model.DiscountDTO;
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
import static org.easymock.EasyMock.createMock;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class DiscountControllerTest {

    @Autowired private DiscountService discountServiceMock;
    @Autowired private DiscountController discountController;

    public DiscountControllerTest() {
    }

    @Before
    public void setup(){
        discountServiceMock = createMock(DiscountService.class);
        discountController = new DiscountController(discountServiceMock);
    }

    @Test
    public void getAllDiscounts() throws Exception {

        expect(discountServiceMock.getAllDiscounts()).andStubAnswer(new IAnswer<List<DiscountDTO>>() {
            @Override
            public List<DiscountDTO> answer() throws Throwable {
                DiscountDTO discountDTO1 = new DiscountDTO("code1");
                DiscountDTO discountDTO2 = new DiscountDTO("code2");

//                discountDTO1.setId(1L);
//                discountDTO2.setId(2L);

                List<DiscountDTO> discountDTOS = new ArrayList<>();
                discountDTOS.add(discountDTO1);
                discountDTOS.add(discountDTO2);
                return discountDTOS;
            }
        });

        replay(discountServiceMock);
        List<DiscountDTO> discountDTOS = discountController.getAllDiscounts();
        verify(discountServiceMock);

        assert(discountDTOS.get(0).equals(new DiscountDTO("code1")));
        assert(discountDTOS.get(1).equals(new DiscountDTO("code2")));
    }
}