package com.mev.films.service;


import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserDiscountDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.implement.ExceptionServiceImpl;
import com.mev.films.service.implement.UserDiscountServiceImpl;
import com.mev.films.service.interfaces.ExceptionService;
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
import static junit.framework.TestCase.fail;
import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class UserDiscountServiceTest {

    @Autowired private UserDiscountMapper userDiscountMapperMock;
    @Autowired private UserDiscountService userDiscountService;

    @Autowired private UserMapper userMapperMock;
    @Autowired private DiscountMapper discountMapperMock;

    @Autowired private ExceptionService exceptionService;

    private UserDTO userDTO1 = new UserDTO("login1", "pass1", (short) 1);
    private UserDTO userDTO2 = new UserDTO("login2", "pass2", (short) 2);

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.2F);

    private UserDiscountDTO userDiscountDTO1;
    private UserDiscountDTO userDiscountDTO2;

    @Before
    public void setup(){
        userDTO1.setId(1L);
        userDTO2.setId(2L);

        discountDTO1.setId(1L);
        discountDTO2.setId(2L);

        userDiscountDTO1 = new UserDiscountDTO(userDTO1, discountDTO1, false);
        userDiscountDTO2 = new UserDiscountDTO(userDTO2, discountDTO2, false);

        userDiscountDTO1.setId(6L);
        userDiscountDTO2.setId(7L);

        userMapperMock = createNiceMock(UserMapper.class);
        discountMapperMock = createNiceMock(DiscountMapper.class);
        userDiscountMapperMock = createNiceMock(UserDiscountMapper.class);

        exceptionService = new ExceptionServiceImpl(userMapperMock, discountMapperMock);

        userDiscountService = new UserDiscountServiceImpl(userDiscountMapperMock, exceptionService);
    }

    @Test
    public void getUserDiscountsTest() {
    }

    @Test
    public void getUserDiscountTest() {

        expect(userDiscountMapperMock.selectUserDiscount(userDiscountDTO1.getId())).andAnswer(new IAnswer<UserDiscountDTO>() {
            @Override
            public UserDiscountDTO answer() throws Throwable {
                return userDiscountDTO1;
            }
        });

        replay(userDiscountMapperMock);

        UserDiscountDTO userDiscountDTO = userDiscountService.getUserDiscount(userDiscountDTO1.getId());
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTO.equals(userDiscountDTO1));

        // check null
        try {
            userDiscountService.getUserDiscount(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            userDiscountService.getUserDiscount(-7L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(userDiscountMapperMock);
    }

    @Test
    public void getUserDiscountsByUserTest() {

        expect(userDiscountMapperMock.selectUserDiscountsByUser(userDiscountDTO1.getUserDTO().getId())).andAnswer(new IAnswer<List<UserDiscountDTO>>() {
            @Override
            public List<UserDiscountDTO> answer() throws Throwable {
                List<UserDiscountDTO> userDiscountDTOS = new ArrayList<>();
                userDiscountDTOS.add(userDiscountDTO1);
                return userDiscountDTOS;
            }
        });

        replay(userDiscountMapperMock);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscountsByUser(userDiscountDTO1.getUserDTO().getId());
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));

        // check user_id null
        try {
            userDiscountService.getUserDiscountsByUser(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_USER_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_USER_ID_PROVIDED).getMessage()));
        }

        // check user_id < 0
        try {
            userDiscountService.getUserDiscountsByUser(-7L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_USER_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_USER_ID_PROVIDED).getMessage()));
        }

        verify(userDiscountMapperMock);
    }

    @Test
    public void getUserDiscountByDiscount() {

        expect(userDiscountMapperMock.selectUserDiscountByDiscount(userDiscountDTO1.getDiscountDTO().getId())).andAnswer(new IAnswer<UserDiscountDTO>() {
            @Override
            public UserDiscountDTO answer() throws Throwable {
                return userDiscountDTO1;
            }
        });

        replay(userDiscountMapperMock);

        UserDiscountDTO userDiscountDTO = userDiscountService.getUserDiscountByDiscount(userDiscountDTO1.getDiscountDTO().getId());
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTO.equals(userDiscountDTO1));

        // check discount_id null
        try {
            userDiscountService.getUserDiscountByDiscount(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_DISCOUNT_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discount_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_DISCOUNT_ID_PROVIDED).getMessage()));
        }

        // check discount_id < 0
        try {
            userDiscountService.getUserDiscountByDiscount(-6L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_DISCOUNT_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("discount_id = -6",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_DISCOUNT_ID_PROVIDED).getMessage()));
        }

        verify(userDiscountMapperMock);
    }

    @Test
    public void addUserDiscount() {

        expect(userMapperMock.selectUser(userDTO1.getId())).andAnswer(new IAnswer<UserDTO>() {
            @Override
            public UserDTO answer() throws Throwable {
                return userDTO1;
            }
        });

        expect(discountMapperMock.selectDiscount(discountDTO1.getId())).andAnswer(new IAnswer<DiscountDTO>() {
            @Override
            public DiscountDTO answer() throws Throwable {
                return discountDTO1;
            }
        });

        replay(userDiscountMapperMock);
        replay(userMapperMock);
        replay(discountMapperMock);

        userDiscountService.addUserDiscount(userDiscountDTO1);

        // check null
        try {
            userDiscountService.addUserDiscount(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDiscountDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check user_id null
        try {
            userDiscountService.addUserDiscount(new UserDiscountDTO(null, userDiscountDTO2.getDiscountDTO(), userDiscountDTO2.isUsed()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_USER_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDiscountDTO.user_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_USER_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check user_id < 0
        try {
            UserDTO userDTO = new UserDTO("login", "pass", (short) 0);
            userDTO.setId(-7L);
            userDiscountService.addUserDiscount(new UserDiscountDTO(userDTO, userDiscountDTO2.getDiscountDTO(), userDiscountDTO2.isUsed()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_USER_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDiscountDTO.user_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_USER_ID_PROVIDED).getMessage()));
        }

        // check user_id not found
        try {
            userDiscountService.addUserDiscount(new UserDiscountDTO(userDiscountDTO2.getUserDTO(), userDiscountDTO2.getDiscountDTO(), userDiscountDTO2.isUsed()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_USER_ID_NOT_FOUND).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDiscountDTO.user_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_USER_ID_NOT_FOUND).getMessage()));
        }

        // check discount_id null
        try {
            userDiscountService.addUserDiscount(new UserDiscountDTO(userDiscountDTO1.getUserDTO(), null, userDiscountDTO1.isUsed()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_DISCOUNT_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDiscountDTO.discount_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_DISCOUNT_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check discount_id < 0
        try {
            DiscountDTO discountDTO = new DiscountDTO(discountDTO1.getCode(), discountDTO1.getValue());
            discountDTO.setId(-7L);
            userDiscountService.addUserDiscount(new UserDiscountDTO(userDiscountDTO1.getUserDTO(), discountDTO, userDiscountDTO1.isUsed()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_DISCOUNT_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDiscountDTO.discount_id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_DISCOUNT_ID_PROVIDED).getMessage()));
        }

        // check discount_id not found
        try {
            userDiscountService.addUserDiscount(new UserDiscountDTO(userDiscountDTO1.getUserDTO(), userDiscountDTO2.getDiscountDTO(), userDiscountDTO1.isUsed()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_DISCOUNT_ID_NOT_FOUND).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDiscountDTO.discount_id = not found",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_DISCOUNT_ID_NOT_FOUND).getMessage()));
        }

        // check used not found
        try {
            userDiscountService.addUserDiscount(new UserDiscountDTO(userDiscountDTO1.getUserDTO(), userDiscountDTO1.getDiscountDTO(), null));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_USED_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDiscountDTO.used = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_DISCOUNT_ERROR_WRONG_USED_PROVIDED).getMessage()));
        }

        verify(userDiscountMapperMock);
        verify(userMapperMock);
        verify(discountMapperMock);
    }
//
//    @Test
//    public void updateUserDiscount() {
//
//        expect(userDiscountService.getUserDiscounts()).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
//            @Override
//            public List<UserDiscountDTO> answer() throws Throwable {
//                List<UserDiscountDTO> userDiscountDTOS = new ArrayList<>();
//                userDiscountDTOS.add(userDiscountDTO1);
//
//                return userDiscountDTOS;
//            }
//        });
//
//        expect(userDiscountService.getUserDiscount(userDiscountDTO1.getId())).andStubAnswer(new IAnswer<UserDiscountDTO>() {
//            @Override
//            public UserDiscountDTO answer() throws Throwable {
//                return userDiscountDTO2;
//            }
//        });
//
//        replay(userDiscountMapperMock);
//
//        userDiscountService.addUserDiscount(userDiscountDTO1);
//
//        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscounts();
//        userDiscountDTOS.get(0).setUserDTO(userDiscountDTO2.getUserDTO());
//        userDiscountDTOS.get(0).setDiscountDTO(userDiscountDTO2.getDiscountDTO());
//        userDiscountDTOS.get(0).setUsed(userDiscountDTO2.isUsed());
//
//        userDiscountService.updateUserDiscount(userDiscountDTOS.get(0));
//
//        UserDiscountDTO userDiscountDTO = userDiscountService.getUserDiscount(userDiscountDTO1.getId());
//        assertTrue("userDiscountDTO1 = " + userDiscountDTOS.get(0).toString(),
//                userDiscountDTO.equals(userDiscountDTOS.get(0)));
//
//        verify(userDiscountMapperMock);
//    }
//
//    @Test
//    public void deleteUserDiscount() {
//
//        expect(userDiscountService.getUserDiscounts()).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
//            @Override
//            public List<UserDiscountDTO> answer() throws Throwable {
//                return new ArrayList<>();
//            }
//        });
//
//        replay(userDiscountMapperMock);
//
//        userDiscountService.addUserDiscount(userDiscountDTO1);
//        userDiscountService.deleteUserDiscount(userDiscountDTO1.getId());
//
//        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscounts();
//        assertTrue("count = 0",
//                userDiscountDTOS.size() == 0);
//
//        verify(userDiscountMapperMock);
//    }
//
//    @Test
//    public void deleteUserDiscountByDiscount() {
//
//        expect(userDiscountService.getUserDiscounts()).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
//            @Override
//            public List<UserDiscountDTO> answer() throws Throwable {
//                return new ArrayList<>();
//            }
//        });
//
//        replay(userDiscountMapperMock);
//
//        userDiscountService.addUserDiscount(userDiscountDTO1);
//        userDiscountService.deleteUserDiscountByDiscount(userDiscountDTO1.getDiscountDTO().getId());
//
//        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscounts();
//        assertTrue("count = 0",
//                userDiscountDTOS.size() == 0);
//
//        verify(userDiscountMapperMock);
//    }
//
//    @Test
//    public void deleteUserDiscountByUser() {
//
//        expect(userDiscountService.getUserDiscounts()).andStubAnswer(new IAnswer<List<UserDiscountDTO>>() {
//            @Override
//            public List<UserDiscountDTO> answer() throws Throwable {
//                return new ArrayList<>();
//            }
//        });
//
//        replay(userDiscountMapperMock);
//
//        userDiscountService.addUserDiscount(userDiscountDTO1);
//        userDiscountService.deleteUserDiscountByUser(userDiscountDTO1.getUserDTO().getId());
//
//        List<UserDiscountDTO> userDiscountDTOS = userDiscountService.getUserDiscounts();
//        assertTrue("count = 0",
//                userDiscountDTOS.size() == 0);
//
//        verify(userDiscountMapperMock);
//    }
}
