package com.mev.films.service;

import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.mappers.interfaces.UserRoleMapper;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.implement.UserServiceImpl;
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
import static org.easymock.EasyMock.*;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class UserServiceTest {
//
//    @Autowired private UserService userService;
//
//    @Autowired private UserMapper userMapperMock;
//    @Autowired private UserRoleMapper userRoleMapperMock;
//
//    private static UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
//    private static UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);
//
//    private static UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
//    private static UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");
//
//    @Before
//    public void setup(){
//        userMapperMock = createNiceMock(UserMapper.class);
//        userRoleMapperMock = createNiceMock(UserRoleMapper.class);
//        userService = new UserServiceImpl(userMapperMock, userRoleMapperMock);
//
//        userDTO1.setId(1L);
//        userDTO2.setId(2L);
//        userRoleDTO1.setId(1L);
//        userRoleDTO2.setId(2L);
//    }
//
//    @Test
//    public void getUsersTest(){
//
//        expect(userService.getUsers()).andStubAnswer(new IAnswer<List<UserDTO>>() {
//            @Override
//            public List<UserDTO> answer() throws Throwable {
//                List<UserDTO> userDTOS = new ArrayList<>();
//                userDTOS.add(userDTO1);
//                userDTOS.add(userDTO2);
//                return userDTOS;
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO1, userRoleDTO1);
//        userService.addUser(userDTO2, userRoleDTO2);
//
//        List<UserDTO> userDTOS = userService.getUsers();
//        assertTrue("UserDTO1 = " + userDTO1,
//                userDTOS.get(0).equals(userDTO1));
//        assertTrue("UserDTO2 = " + userDTO2,
//                userDTOS.get(1).equals(userDTO2));
//
//        verify(userMapperMock);
//        verify(userRoleMapperMock);
//    }
//
//    @Test
//    public void getUserTest(){
//
//        expect(userService.getUser(userDTO2.getId())).andStubAnswer(new IAnswer<UserDTO>() {
//            @Override
//            public UserDTO answer() throws Throwable {
//                return userDTO2;
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO1, userRoleDTO1);
//        userService.addUser(userDTO2, userRoleDTO2);
//
//        UserDTO userDTO = userService.getUser(userDTO2.getId());
//        assertTrue("UserDTO2 = " + userDTO2,
//                userDTO.equals(userDTO2));
//
//        verify(userMapperMock);
//        verify(userRoleMapperMock);
//    }
//
//    @Test
//    public void getUsersSortByLoginTest(){
//        expect(userService.getUsersSortByLogin()).andStubAnswer(new IAnswer<List<UserDTO>>() {
//            @Override
//            public List<UserDTO> answer() throws Throwable {
//                List<UserDTO> userDTOS = new ArrayList<>();
//                userDTOS.add(userDTO1);
//                userDTOS.add(userDTO2);
//
//                return userDTOS;
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO2, userRoleDTO2);
//        userService.addUser(userDTO1, userRoleDTO1);
//
//        List<UserDTO> userDTOS = userService.getUsersSortByLogin();
//        assertTrue("userDTO1 = " + userDTO1.toString(),
//                userDTOS.get(0).equals(userDTO1));
//        assertTrue("userDTO2 = " + userDTO2.toString(),
//                userDTOS.get(1).equals(userDTO2));
//
//        verify(userMapperMock);
//        verify(userRoleMapperMock);
//    }
//
//    @Test
//    public void getUserByLoginTest(){
//
//        expect(userService.getUserByLogin(userDTO2.getLogin())).andStubAnswer(new IAnswer<UserDTO>() {
//            @Override
//            public UserDTO answer() throws Throwable {
//                return userDTO2;
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO2, userRoleDTO2);
//
//        UserDTO userDTO = userService.getUserByLogin(userDTO2.getLogin());
//        assertTrue("UserDTO2 = " + userDTO2,
//                userDTO.equals(userDTO2));
//
//        verify(userMapperMock);
//        verify(userRoleMapperMock);
//    }
//
//    @Test
//    public void getUserRolesTest(){
//
//        expect(userService.getUserRoles()).andStubAnswer(new IAnswer<List<UserRoleDTO>>() {
//            @Override
//            public List<UserRoleDTO> answer() throws Throwable {
//                List<UserRoleDTO> userRoleDTOS = new ArrayList<>();
//                userRoleDTOS.add(userRoleDTO1);
//                userRoleDTOS.add(userRoleDTO2);
//                return userRoleDTOS;
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO1, userRoleDTO1);
//        userService.addUser(userDTO2, userRoleDTO2);
//
//        List<UserRoleDTO> userRoles = userService.getUserRoles();
//
//        assertTrue("userRoleDTO1 = " + userRoles.get(0),
//                userRoles.get(0).equals(userRoleDTO1));
//        assertTrue("userRoleDTO2 = " + userRoles.get(1),
//                userRoles.get(1).equals(userRoleDTO2));
//
//        verify(userMapperMock);
//        verify(userRoleMapperMock);
//    }
//
//    @Test
//    public void getUserRoleTest(){
//
//        expect(userService.getUserRole(userRoleDTO1.getId())).andStubAnswer(new IAnswer<UserRoleDTO>() {
//            @Override
//            public UserRoleDTO answer() throws Throwable {
//                return userRoleDTO1;
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO1, userRoleDTO1);
//
//        UserRoleDTO userRole = userService.getUserRole(userRoleDTO1.getId());
//        assertTrue("userRoleDTO1 = " + userRoleDTO1,
//                userRole.equals(userRoleDTO1));
//
//        verify(userMapperMock);
//        verify(userRoleMapperMock);
//    }
//
//    @Test
//    public void getUserRoleByLoginTest(){
//
//        expect(userService.getUserRoleByLogin(userDTO1.getLogin())).andStubAnswer(new IAnswer<UserRoleDTO>() {
//            @Override
//            public UserRoleDTO answer() throws Throwable {
//                return userRoleDTO1;
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO1, userRoleDTO1);
//
//        UserRoleDTO userRole = userService.getUserRoleByLogin(userDTO1.getLogin());
//        assertTrue("userRoleDTO1 = " + userRoleDTO1,
//                userRole.equals(userRoleDTO1));
//
//        verify(userMapperMock);
//        verify(userRoleMapperMock);
//    }
//
//    @Test
//    public void addUserTest(){
//
//        expect(userService.getUserByLogin(userDTO2.getLogin())).andStubAnswer(new IAnswer<UserDTO>() {
//            @Override
//            public UserDTO answer() throws Throwable {
//                return userDTO2;
//            }
//        });
//
//        expect(userService.getUserRoleByLogin(userDTO2.getLogin())).andStubAnswer(new IAnswer<UserRoleDTO>() {
//            @Override
//            public UserRoleDTO answer() throws Throwable {
//                return userRoleDTO2;
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO2, userRoleDTO2);
//
//
//        UserDTO userDTO = userService.getUserByLogin(userDTO2.getLogin());
//        assertTrue("userDTO2 = " + userDTO2,
//                userDTO.equals(userDTO2));
//
//        UserRoleDTO userRoleDTO = userService.getUserRoleByLogin(userDTO2.getLogin());
//        assertTrue("userRoleDTO2 = " + userRoleDTO2,
//                userRoleDTO.equals(userRoleDTO2));
//    }
//
//    @Test
//    public void updateUserTest(){
//
//        expect(userService.getUserByLogin(userDTO2.getLogin())).andStubAnswer(new IAnswer<UserDTO>() {
//            @Override
//            public UserDTO answer() throws Throwable {
//                return userDTO2;
//            }
//        });
//
//        expect(userService.getUsers()).andStubAnswer(new IAnswer <List<UserDTO>>() {
//            @Override
//            public List<UserDTO> answer() throws Throwable {
//                UserDTO userDTO = new UserDTO();
//                userDTO.setLogin("user2");
//                userDTO.setPassword("user22");
//                userDTO.setEnabled((short) 2);
//                List<UserDTO> userDTOS = new ArrayList<>();
//                userDTOS.add(userDTO);
//                return userDTOS;
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO2, userRoleDTO2);
//
//        UserDTO getUserDTO2 = userService.getUserByLogin(userDTO2.getLogin());
//        getUserDTO2.setPassword("user22");
//        getUserDTO2.setEnabled((short) 2);
//
//        userService.updateUser(getUserDTO2);
//
//        List<UserDTO> userDTOS = userService.getUsers();
//        assertTrue("userDTO2 = " + getUserDTO2,
//                userDTOS.get(0).equals(getUserDTO2));
//
//        verify(userMapperMock);
//        verify(userRoleMapperMock);
//    }
//
//    @Test
//    public void updateUserRoleTest(){
//
//        expect(userService.getUserRoleByLogin(userRoleDTO1.getLogin())).andStubAnswer(new IAnswer<UserRoleDTO>() {
//            @Override
//            public UserRoleDTO answer() throws Throwable {
//                return userRoleDTO1;
//            }
//        });
//
//        expect(userService.getUserRoles()).andStubAnswer(new IAnswer<List<UserRoleDTO>>() {
//            @Override
//            public List<UserRoleDTO> answer() throws Throwable {
//                UserRoleDTO userRoleDTO = new UserRoleDTO("user1", "USER_ADMIN");
//                List<UserRoleDTO> userRoleDTOS = new ArrayList<>();
//                userRoleDTOS.add(userRoleDTO);
//                return userRoleDTOS;
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO1, userRoleDTO1);
//
//        UserRoleDTO getUserRole1 = userService.getUserRoleByLogin(userRoleDTO1.getLogin());
//        getUserRole1.setRole("USER_ADMIN");
//
//        userService.updateUserRole(getUserRole1);
//
//        List<UserRoleDTO> userRoleDTOS = userService.getUserRoles();
//        assertTrue("userRoleDTO1 = " + getUserRole1,
//                userRoleDTOS.get(0).equals(getUserRole1));
//    }
//
//    @Test
//    public void deleteUserTest(){
//
//        expect(userService.getUserByLogin(userDTO1.getLogin())).andStubAnswer(new IAnswer<UserDTO>() {
//            @Override
//            public UserDTO answer() throws Throwable {
//                return userDTO1;
//            }
//        });
//
//        expect(userService.getUserByLogin(userDTO2.getLogin())).andStubAnswer(new IAnswer<UserDTO>() {
//            @Override
//            public UserDTO answer() throws Throwable {
//                return userDTO2;
//            }
//        });
//
//        expect(userService.getUsers()).andStubAnswer(new IAnswer<List<UserDTO>>() {
//            @Override
//            public List<UserDTO> answer() throws Throwable {
//                return new ArrayList<>();
//            }
//        });
//
//        expect(userService.getUserRoles()).andStubAnswer(new IAnswer<List<UserRoleDTO>>() {
//            @Override
//            public List<UserRoleDTO> answer() throws Throwable {
//                return new ArrayList<>();
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO1, userRoleDTO1);
//        userService.addUser(userDTO2, userRoleDTO2);
//
//        UserDTO getUserDTO1 = userService.getUserByLogin(userDTO1.getLogin());
//        UserDTO getUserDTO2 = userService.getUserByLogin(userDTO2.getLogin());
//
//        userService.deleteUser(getUserDTO1.getId());
//        userService.deleteUser(getUserDTO2.getId());
//
//        List<UserDTO> usersAfterDelete = userService.getUsers();
//        assertTrue("usersAll = " + usersAfterDelete.size(), usersAfterDelete.size() == 0);
//
//        List<UserRoleDTO> userRolesAfterDelete = userService.getUserRoles();
//        assertTrue("userRolesAll = " + userRolesAfterDelete.size(), userRolesAfterDelete.size() == 0);
//    }
//
//    @Test
//    public void deleteUserByLogin(){
//        expect(userService.getUserByLogin(userDTO1.getLogin())).andStubAnswer(new IAnswer<UserDTO>() {
//            @Override
//            public UserDTO answer() throws Throwable {
//                return userDTO1;
//            }
//        });
//
//        expect(userService.getUserByLogin(userDTO2.getLogin())).andStubAnswer(new IAnswer<UserDTO>() {
//            @Override
//            public UserDTO answer() throws Throwable {
//                return userDTO2;
//            }
//        });
//
//        expect(userService.getUsers()).andStubAnswer(new IAnswer<List<UserDTO>>() {
//            @Override
//            public List<UserDTO> answer() throws Throwable {
//                return new ArrayList<>();
//            }
//        });
//
//        expect(userService.getUserRoles()).andStubAnswer(new IAnswer<List<UserRoleDTO>>() {
//            @Override
//            public List<UserRoleDTO> answer() throws Throwable {
//                return new ArrayList<>();
//            }
//        });
//
//        replay(userMapperMock);
//        replay(userRoleMapperMock);
//
//        userService.addUser(userDTO1, userRoleDTO1);
//        userService.addUser(userDTO2, userRoleDTO2);
//
//        UserDTO getUserDTO1 = userService.getUserByLogin(userDTO1.getLogin());
//        UserDTO getUserDTO2 = userService.getUserByLogin(userDTO2.getLogin());
//
//        userService.deleteUserByLogin(getUserDTO1.getLogin());
//        userService.deleteUserByLogin(getUserDTO2.getLogin());
//
//        List<UserDTO> usersAfterDelete = userService.getUsers();
//        assertTrue("usersAll = " + usersAfterDelete.size(), usersAfterDelete.size() == 0);
//
//        List<UserRoleDTO> userRolesAfterDelete = userService.getUserRoles();
//        assertTrue("userRolesAll = " + userRolesAfterDelete.size(), userRolesAfterDelete.size() == 0);
//    }
}