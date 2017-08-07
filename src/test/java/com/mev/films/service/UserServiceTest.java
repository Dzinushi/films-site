package com.mev.films.service;

import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.model.UserDTO;
import com.mev.films.service.implement.ExceptionServiceImpl;
import com.mev.films.service.implement.UserServiceImpl;
import com.mev.films.service.interfaces.ExceptionService;
import com.mev.films.service.interfaces.UserService;
import org.easymock.IAnswer;
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
public class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserMapper userMapperMock;

    @Autowired private ExceptionService exceptionService;

    private UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
    private UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);

    @Before
    public void setup(){
        userMapperMock = createNiceMock(UserMapper.class);

        exceptionService = new ExceptionServiceImpl(userMapperMock);

        userService = new UserServiceImpl(userMapperMock, exceptionService);

        userDTO1.setId(1L);
        userDTO2.setId(2L);
    }

    @Test
    public void getUsersTest(){
    }

    @Test
    public void getUserTest(){

        expect(userMapperMock.select(userDTO2.getId())).andStubAnswer(new IAnswer<UserDTO>() {
            @Override
            public UserDTO answer() throws Throwable {
                return userDTO2;
            }
        });

        replay(userMapperMock);

        // check valid id
        UserDTO userDTO = userService.getUser(userDTO2.getId());
        assertTrue("userDTO2 = " + userDTO2,
                userDTO.equals(userDTO2));

        // check null
        try {
            userService.getUser(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            userService.getUser(-7L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(userMapperMock);
    }

    @Test
    public void getUsersSortByLoginTest(){
    }

    @Test
    public void getUserByLoginTest(){

        expect(userMapperMock.selectByLogin(userDTO2.getLogin())).andStubAnswer(new IAnswer<UserDTO>() {
            @Override
            public UserDTO answer() throws Throwable {
                return userDTO2;
            }
        });

        replay(userMapperMock);

        UserDTO userDTO = userService.getUserByLogin(userDTO2.getLogin());
        assertTrue("userDTO2 = " + userDTO2,
                userDTO.equals(userDTO2));

        // check null
        try {
            userService.getUserByLogin(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_LOGIN_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("login = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_LOGIN_PROVIDED).getMessage()));
        }

        verify(userMapperMock);
    }

    @Test
    public void getUsersIdLoginTest(){
    }

    @Test
    public void addUserTest(){

        userService.addUser(userDTO2);

        // check null
        try {
            userService.addUser(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check login null
        try {
            userService.addUser(new UserDTO(null, userDTO2.getPassword(), userDTO2.getEnabled()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_LOGIN_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO.login = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_LOGIN_PROVIDED).getMessage()));
        }

        // check password null
        try {
            userService.addUser(new UserDTO(userDTO2.getLogin(), null, userDTO2.getEnabled()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_PASSWORD_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO.password = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_PASSWORD_PROVIDED).getMessage()));
        }

        // check enable null
        try {
            userService.addUser(new UserDTO(userDTO2.getLogin(), userDTO2.getPassword(), null));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO.enable = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage()));
        }

        // check enable < 0
        try {
            userService.addUser(new UserDTO(userDTO2.getLogin(), userDTO2.getPassword(), (short) -7));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO.enable = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage()));
        }
    }

    @Test
    public void updateUserTest(){

        userService.updateUser(userDTO2);

        // check null
        try {
            userService.updateUser(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check id null
        try {
            userService.updateUser(new UserDTO(userDTO1.getLogin(), userDTO1.getPassword(), userDTO1.getEnabled()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO.id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id null
        try {
            UserDTO userDTO = new UserDTO(userDTO1.getLogin(), userDTO1.getPassword(), userDTO1.getEnabled());
            userDTO.setId(-7L);
            userService.updateUser(userDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO.id = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check login null
        try {
            UserDTO userDTO = new UserDTO(null, userDTO2.getPassword(), userDTO2.getEnabled());
            userDTO.setId(7L);
            userService.updateUser(userDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_LOGIN_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO.login = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_LOGIN_PROVIDED).getMessage()));
        }

        // check password null
        try {
            UserDTO userDTO = new UserDTO(userDTO2.getLogin(), null, userDTO2.getEnabled());
            userDTO.setId(7L);
            userService.updateUser(userDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_PASSWORD_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO.password = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_PASSWORD_PROVIDED).getMessage()));
        }

        // check enable null
        try {
            UserDTO userDTO = new UserDTO(userDTO2.getLogin(), userDTO2.getPassword(), null);
            userDTO.setId(7L);
            userService.updateUser(userDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO.enable = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage()));
        }

        // check enable < 0
        try {
            UserDTO userDTO = new UserDTO(userDTO2.getLogin(), userDTO2.getPassword(), (short) -7);
            userDTO.setId(7L);
            userService.updateUser(userDTO);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO.enable = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage()));
        }
    }

    @Test
    public void deleteUserByLogin(){

        replay(userMapperMock);

        userService.deleteUserByLogin(userDTO1.getLogin());

        // check null
        try {
            userService.deleteUserByLogin(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_LOGIN_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("login = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_LOGIN_PROVIDED).getMessage()));
        }
    }
}