package com.mev.films.service;


import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserDiscountDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.implement.UserDiscountServiceImpl;
import com.mev.films.service.interfaces.UserDiscountService;
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
public class UserDiscountServiceTest {

    @Autowired private UserDiscountMapper userDiscountMapperMock;
    @Autowired private UserDiscountService userDiscountService;

    private UserDTO userDTO1 = new UserDTO("login1", "pass1", (short) 1);
    private UserDTO userDTO2 = new UserDTO("login2", "pass2", (short) 2);

    private UserRoleDTO userRoleDTO1 = new UserRoleDTO("login1", "ROLE_ADMIN");
    private UserRoleDTO userRoleDTO2 = new UserRoleDTO("login2", "ROLE_USER");

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.2F);

    private UserDiscountDTO userDiscountDTO1;
    private UserDiscountDTO userDiscountDTO2;

    @Before
    public void setup(){
        userDTO1.setId(1L);
        userDTO2.setId(2L);

        userRoleDTO1.setId(1L);
        userRoleDTO2.setId(2L);

        discountDTO1.setId(1L);
        userRoleDTO2.setId(2L);

        userDiscountDTO1 = new UserDiscountDTO(userDTO1, discountDTO1, false);
        userDiscountDTO2 = new UserDiscountDTO(userDTO2, discountDTO2, false);

        userDiscountMapperMock = createNiceMock(UserDiscountMapper.class);
        userDiscountService = new UserDiscountServiceImpl(userDiscountMapperMock);
    }

    @Test
    public void getUserDiscountsTest() {

        expect(userDiscountService.getUserDiscounts()).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
            @Override
            public List<UserDiscountDTO> answer() throws Throwable {

                List<UserDiscountDTO> userDiscountDTOS = new ArrayList<>();
                userDiscountDTOS.add(userDiscountDTO1);
                userDiscountDTOS.add(userDiscountDTO2);

                return userDiscountDTOS;
            }
        });

        replay(userDiscountMapperMock);

        userDiscountService.addUserDiscount(userDiscountDTO1);
        userDiscountService.addUserDiscount(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapperMock.selectUserDiscounts();
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(1).equals(userDiscountDTO2));

        verify(userDiscountMapperMock);
    }

    @Test
    public void getUserDiscountTest() {

        expect(userDiscountService.getUserDiscount(userDiscountDTO1.getId())).andStubAnswer(new IAnswer<UserDiscountDTO>() {
            @Override
            public UserDiscountDTO answer() throws Throwable {
                return userDiscountDTO1;
            }
        });

        replay(userDiscountMapperMock);

        userDiscountService.addUserDiscount(userDiscountDTO1);

        UserDiscountDTO userDiscountDTO = userDiscountService.getUserDiscount(userDiscountDTO1.getId());
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTO.equals(userDiscountDTO1));

        verify(userDiscountMapperMock);
    }

    @Test
    public void getUserDiscountsByUserTest() {

        expect(userDiscountService.getUserDiscountsByUser(userDiscountDTO1.getUserDTO().getId())).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
            @Override
            public List<UserDiscountDTO> answer() throws Throwable {
                List<UserDiscountDTO> userDiscountDTOS = new ArrayList<>();
                userDiscountDTOS.add(userDiscountDTO1);

                return userDiscountDTOS;
            }
        });

        replay(userDiscountMapperMock);

        userDiscountService.addUserDiscount(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscountsByUser(userDiscountDTO1.getUserDTO().getId());
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));

        verify(userDiscountMapperMock);
    }

    @Test
    public void getUserDiscountByDiscount() {

        expect(userDiscountService.getUserDiscountByDiscount(userDiscountDTO1.getDiscountDTO().getId())).andStubAnswer(new IAnswer<UserDiscountDTO>() {
            @Override
            public UserDiscountDTO answer() throws Throwable {
                return userDiscountDTO1;
            }
        });

        replay(userDiscountMapperMock);

        UserDiscountDTO userDiscountDTO = userDiscountService.getUserDiscountByDiscount(userDiscountDTO1.getDiscountDTO().getId());
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTO.equals(userDiscountDTO1));

        verify(userDiscountMapperMock);
    }

    @Test
    public void addUserDiscount() {

        expect(userDiscountService.getUserDiscounts()).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
            @Override
            public List<UserDiscountDTO> answer() throws Throwable {
                List<UserDiscountDTO> userDiscountDTOS = new ArrayList<>();
                userDiscountDTOS.add(userDiscountDTO1);

                return userDiscountDTOS;
            }
        });

        replay(userDiscountMapperMock);

        userDiscountService.addUserDiscount(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscounts();
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscount1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));

        verify(userDiscountMapperMock);
    }

    @Test
    public void updateUserDiscount() {

        expect(userDiscountService.getUserDiscounts()).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
            @Override
            public List<UserDiscountDTO> answer() throws Throwable {
                List<UserDiscountDTO> userDiscountDTOS = new ArrayList<>();
                userDiscountDTOS.add(userDiscountDTO1);

                return userDiscountDTOS;
            }
        });

        expect(userDiscountService.getUserDiscount(userDiscountDTO1.getId())).andStubAnswer(new IAnswer<UserDiscountDTO>() {
            @Override
            public UserDiscountDTO answer() throws Throwable {
                return userDiscountDTO2;
            }
        });

        replay(userDiscountMapperMock);

        userDiscountService.addUserDiscount(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscounts();
        userDiscountDTOS.get(0).setUserDTO(userDiscountDTO2.getUserDTO());
        userDiscountDTOS.get(0).setDiscountDTO(userDiscountDTO2.getDiscountDTO());
        userDiscountDTOS.get(0).setUsed(userDiscountDTO2.isUsed());

        userDiscountService.updateUserDiscount(userDiscountDTOS.get(0));

        UserDiscountDTO userDiscountDTO = userDiscountService.getUserDiscount(userDiscountDTO1.getId());
        assertTrue("userDiscountDTO1 = " + userDiscountDTOS.get(0).toString(),
                userDiscountDTO.equals(userDiscountDTOS.get(0)));

        verify(userDiscountMapperMock);
    }

    @Test
    public void deleteUserDiscount() {

        expect(userDiscountService.getUserDiscounts()).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
            @Override
            public List<UserDiscountDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        replay(userDiscountMapperMock);

        userDiscountService.addUserDiscount(userDiscountDTO1);
        userDiscountService.deleteUserDiscount(userDiscountDTO1.getId());

        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        verify(userDiscountMapperMock);
    }

    @Test
    public void deleteUserDiscountByDiscount() {

        expect(userDiscountService.getUserDiscounts()).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
            @Override
            public List<UserDiscountDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        replay(userDiscountMapperMock);

        userDiscountService.addUserDiscount(userDiscountDTO1);
        userDiscountService.deleteUserDiscountByDiscount(userDiscountDTO1.getDiscountDTO().getId());

        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        verify(userDiscountMapperMock);
    }

    @Test
    public void deleteUserDiscountByUser() {

        expect(userDiscountService.getUserDiscounts()).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
            @Override
            public List<UserDiscountDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        replay(userDiscountMapperMock);

        userDiscountService.addUserDiscount(userDiscountDTO1);
        userDiscountService.deleteUserDiscountByUser(userDiscountDTO1.getUserDTO().getId());

        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        verify(userDiscountMapperMock);
    }
}
