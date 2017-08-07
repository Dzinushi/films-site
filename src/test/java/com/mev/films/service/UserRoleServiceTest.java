package com.mev.films.service;

import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.mappers.interfaces.UserRoleMapper;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.implement.ExceptionServiceImpl;
import com.mev.films.service.implement.UserRoleServiceImpl;
import com.mev.films.service.implement.UserServiceImpl;
import com.mev.films.service.interfaces.ExceptionService;
import com.mev.films.service.interfaces.UserRoleService;
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

import static com.mev.films.service.implement.ExceptionServiceImpl.Errors.*;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class UserRoleServiceTest {

    @Autowired private UserRoleService userRoleService;
    @Autowired private UserService userService;

    @Autowired private UserRoleMapper userRoleMapperMock;
    @Autowired private UserMapper userMapperMock;

    @Autowired private ExceptionService exceptionService;

    private static UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
    private static UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);

    private static UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private static UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");


    @Before
    public void setup(){

        userMapperMock = createNiceMock(UserMapper.class);
        userRoleMapperMock = createNiceMock(UserRoleMapper.class);

        exceptionService = new ExceptionServiceImpl(userMapperMock);

        userService = new UserServiceImpl(userMapperMock, exceptionService);
        userRoleService = new UserRoleServiceImpl(userRoleMapperMock, userService, exceptionService);

        userDTO1.setId(1L);
        userDTO2.setId(2L);

        userRoleDTO1.setId(1L);
        userRoleDTO2.setId(2L);
    }

    @Test
    public void getUserRolesTest(){

        expect(userRoleMapperMock.selects(1L, 1L)).andStubAnswer(new IAnswer<List<UserRoleDTO>>() {
            @Override
            public List<UserRoleDTO> answer() throws Throwable {
                List<UserRoleDTO> userRoleDTOS = new ArrayList<>();
                userRoleDTOS.add(userRoleDTO2);
                return userRoleDTOS;
            }
        });

        replay(userRoleMapperMock);

        List<UserRoleDTO> userRoleDTOS = userRoleService.getUserRoles(1L, 1L);
        assertTrue("count = 1",
                userRoleDTOS.size() == 1);
        assertTrue("userRoleDTO1 = " + userRoleDTO2.toString(),
                userRoleDTOS.get(0).equals(userRoleDTO2));

        // check number null
        try {
            userRoleService.getUserRoles(null, 1L);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_NUMBER_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_NUMBER_PROVIDED).getMessage()));
        }

        // check number < 1
        try {
            userRoleService.getUserRoles(0L, 1L);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_NUMBER_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = 0",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_NUMBER_PROVIDED).getMessage()));
        }

        // check number > 100
        try {
            userRoleService.getUserRoles(101L, 1L);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_NUMBER_VALUE_MORE_THAN_100).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("number = 101",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_NUMBER_VALUE_MORE_THAN_100).getMessage()));
        }

        // check from null
        try {
            userRoleService.getUserRoles(1L, null);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_FROM_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("from = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_FROM_PROVIDED).getMessage()));
        }

        // check from < 0
        try {
            userRoleService.getUserRoles(1L, -1L);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_FROM_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("from = -1",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_FROM_PROVIDED).getMessage()));
        }

        verify(userRoleMapperMock);
    }

    @Test
    public void getUserRolesCountTest(){
    }

    @Test
    public void getUserRoleTest(){

        expect(userRoleMapperMock.select(2L)).andAnswer(new IAnswer<UserRoleDTO>() {
            @Override
            public UserRoleDTO answer() throws Throwable {
                return userRoleDTO2;
            }
        });

        replay(userRoleMapperMock);

        // check valid id
        UserRoleDTO userRoleDTO = userRoleService.getUserRole(2L);
        assertTrue(userRoleDTO2.toString(),
                userRoleDTO.equals(userRoleDTO2));

        // check id set 'null'
        try {
            userRoleService.getUserRole(null);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_role_id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id set '-1'
        try {
            userRoleService.getUserRole(-1L);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("user_role_id = -1",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        verify(userRoleMapperMock);
    }

    @Test
    public void getUserRoleByLoginTest(){

        expect(userRoleMapperMock.selectByLogin("user1")).andAnswer(new IAnswer<UserRoleDTO>() {
            @Override
            public UserRoleDTO answer() throws Throwable {
                return userRoleDTO1;
            }
        });

        replay(userRoleMapperMock);

        // check valid login
        UserRoleDTO userRoleDTO = userRoleService.getUserRoleByLogin("user1");
        assertTrue(userRoleDTO1.toString(),
                userRoleDTO.equals(userRoleDTO1));

        // check login set null
        try {
            userRoleDTO = userRoleService.getUserRoleByLogin(null);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_LOGIN_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("login = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_LOGIN_PROVIDED).getMessage()));
        }

        verify(userRoleMapperMock);
    }

    @Test
    public void addUserRoleTest(){

        expect(userMapperMock.selectByLogin("user1")).andAnswer(new IAnswer<UserDTO>() {
            @Override
            public UserDTO answer() throws Throwable {
                return userDTO1;
            }
        });

        replay(userRoleMapperMock);
        replay(userMapperMock);

        userRoleService.addUserRole(userRoleDTO1);

        // check null
        try {
            userRoleService.addUserRole(null);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userRoleDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check role null
        try {
            userRoleService.addUserRole(new UserRoleDTO(userRoleDTO1.getLogin(), null));
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ROLE).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userRoleDTO.role = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ROLE).getMessage()));
        }

        // check user not found (user == null)
        try {
            userRoleService.addUserRole(new UserRoleDTO(userRoleDTO2.getLogin(), userRoleDTO2.getRole()));
            fail(new ExceptionServiceImpl(USER_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }
    }

    @Test
    public void updateUserRoleTest(){

        expect(userMapperMock.selectByLogin("user1")).andAnswer(new IAnswer<UserDTO>() {
            @Override
            public UserDTO answer() throws Throwable {
                return userDTO1;
            }
        });

        replay(userRoleMapperMock);
        replay(userMapperMock);

        userRoleService.updateUserRole(userRoleDTO1);

        // check null
        try {
            userRoleService.updateUserRole(null);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userRoleDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }

        // check id null
        try {
            UserRoleDTO userRoleDTO = new UserRoleDTO(userRoleDTO1.getLogin(), userRoleDTO1.getRole());
            userRoleService.updateUserRole(userRoleDTO);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userRoleDTO.id = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check id < 0
        try {
            UserRoleDTO userRoleDTO = new UserRoleDTO(userRoleDTO1.getLogin(), userRoleDTO1.getRole());
            userRoleDTO.setId(-5L);
            userRoleService.updateUserRole(userRoleDTO);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ID_PROVIDED).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userRoleDTO.id = -5",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ID_PROVIDED).getMessage()));
        }

        // check role null
        try {
            UserRoleDTO userRoleDTO = new UserRoleDTO(userRoleDTO1.getLogin(), null);
            userRoleDTO.setId(1L);
            userRoleService.updateUserRole(userRoleDTO);
            fail(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ROLE).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userRoleDTO.role = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ROLE_ERROR_WRONG_ROLE).getMessage()));
        }

        // check user not found (user = null)
        try {
            UserRoleDTO userRoleDTO = new UserRoleDTO(userRoleDTO2.getLogin(), userRoleDTO2.getRole());
            userRoleDTO.setId(1L);
            userRoleService.updateUserRole(userRoleDTO);
            fail(new ExceptionServiceImpl(USER_ERROR_NULL_POINTER_EXCEPTION).getMessage());
        } catch (ExceptionServiceImpl e){
            assertTrue("userDTO = null",
                    e.getMessage().equals(new ExceptionServiceImpl(USER_ERROR_NULL_POINTER_EXCEPTION).getMessage()));
        }
    }
}
