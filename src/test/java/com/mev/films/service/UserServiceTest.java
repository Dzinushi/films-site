package com.mev.films.service;

import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.mappers.interfaces.UserRoleMapper;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserInfoDTO;
import com.mev.films.model.UserRoleDTO;
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

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.easymock.EasyMock.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserMapper userMapperMock;
    @Autowired private UserRoleMapper userRoleMapperMock;

    @Autowired private ExceptionService exceptionService;

    private UserInfoDTO userInfoDTO1 = new UserInfoDTO("user1", "pass1", (short) 1, "ROLE_USER");
    private UserInfoDTO userInfoDTO2 = new UserInfoDTO("user2", "pass2", (short) 1, "ROLE_ADMIN");

    private UserDTO userDTO1;
    private UserDTO userDTO2;

    private UserRoleDTO userRoleDTO1;
    private UserRoleDTO userRoleDTO2;

    @Before
    public void setup(){

        userMapperMock = createNiceMock(UserMapper.class);
        userRoleMapperMock = createNiceMock(UserRoleMapper.class);

        exceptionService = new ExceptionServiceImpl(userMapperMock, userRoleMapperMock);

        userService = new UserServiceImpl(userMapperMock, userRoleMapperMock, exceptionService);

        userDTO1 = new UserDTO(userInfoDTO1.getLogin(), userInfoDTO1.getPassword(), userInfoDTO1.getEnabled());
        userDTO1.setId(1L);

        userDTO2 = new UserDTO(userInfoDTO2.getLogin(), userInfoDTO2.getPassword(), userInfoDTO2.getEnabled());
        userDTO2.setId(2L);

        userRoleDTO1 = new UserRoleDTO(userInfoDTO1.getLogin(), userInfoDTO1.getRole());
        userRoleDTO1.setId(1L);

        userRoleDTO2 = new UserRoleDTO(userInfoDTO2.getLogin(), userInfoDTO2.getRole());
        userRoleDTO2.setId(2L);
    }

    @Test
    public void getUsersTest(){

        expect(userMapperMock.selects(1L, 1L)).andStubAnswer(new IAnswer<List<UserDTO>>() {
            @Override
            public List<UserDTO> answer() throws Throwable {
                List<UserDTO> userDTOS = new ArrayList<>();
                userDTOS.add(userDTO2);
                return userDTOS;
            }
        });

        replay(userMapperMock);

        List<UserDTO> userDTOS = userService.getUsers(1L, 1L);
        assertTrue("count = 1",
                userDTOS.size() == 1);
        assertTrue("userDTO1 = " + userDTO2.toString(),
                userDTOS.get(0).equals(userDTO2));

        // check number null
        try {
            userService.getUsers(null, 1L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_NUMBER_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_NUMBER_PROVIDED).getMessage()));
        }

        // check number < 1
        try {
            userService.getUsers(0L, 1L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_NUMBER_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = 0",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_NUMBER_PROVIDED).getMessage()));
        }

        // check number > 100
        try {
            userService.getUsers(101L, 1L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_NUMBER_VALUE_MORE_THAN_100).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = 101",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_NUMBER_VALUE_MORE_THAN_100).getMessage()));
        }

        // check from null
        try {
            userService.getUsers(1L, null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_FROM_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("from = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_FROM_PROVIDED).getMessage()));
        }

        // check from < 0
        try {
            userService.getUsers(1L, -1L);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_FROM_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("from = -1",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_FROM_PROVIDED).getMessage()));
        }

        verify(userMapperMock);
    }

    @Test
    public void getUsersCountTest(){
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

        userService.addUser(userInfoDTO2);

        // check null
        try {
            userService.addUser(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_INFO_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userInfoDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_INFO_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check login null
        try {
            userService.addUser(new UserInfoDTO(null, userInfoDTO2.getPassword(), userInfoDTO2.getEnabled(), userInfoDTO1.getRole()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_LOGIN_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userInfoDTO.login = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_LOGIN_PROVIDED).getMessage()));
        }

        // check password null
        try {
            userService.addUser(new UserInfoDTO(userInfoDTO2.getLogin(), null, userInfoDTO2.getEnabled(), userInfoDTO2.getRole()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_PASSWORD_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userInfoDTO.password = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_PASSWORD_PROVIDED).getMessage()));
        }

        // check enable null
        try {
            userService.addUser(new UserInfoDTO(userInfoDTO2.getLogin(), userInfoDTO2.getPassword(), null, userInfoDTO2.getRole()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userInfoDTO.enable = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage()));
        }

        // check enable < 0
        try {
            userService.addUser(new UserInfoDTO(userInfoDTO2.getLogin(), userInfoDTO2.getPassword(), (short) -7, userInfoDTO2.getRole()));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userInfoDTO.enable = -7",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ERROR_WRONG_ENABLE_PROVIDED).getMessage()));
        }

        // check role null
        try {
            userService.addUser(new UserInfoDTO(userInfoDTO2.getLogin(), userInfoDTO2.getPassword(), (short) 1, null));
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ROLE_ERROR_WRONG_ROLE).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userInfoDTO.role = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_ROLE_ERROR_WRONG_ROLE).getMessage()));
        }
    }

    @Test
    public void updateUserTest(){

        userService.updateUser(userDTO2);

        // check null
        try {
            userService.updateUser(null);
            fail(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_INFO_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(ExceptionServiceImpl.Errors.USER_INFO_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
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