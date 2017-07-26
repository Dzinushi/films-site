package com.mev.films.service;

import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.mappers.interfaces.UserRoleMapper;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.implement.UserRoleServiceImpl;
import com.mev.films.service.implement.UserServiceImpl;
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

import static junit.framework.TestCase.assertTrue;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class UserRoleServiceTest {

    @Autowired private UserRoleService userRoleService;
    @Autowired private UserService userService;

    @Autowired private UserRoleMapper userRoleMapperMock;
    @Autowired private UserMapper userMapperMock;

    private static UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
    private static UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);

    private static UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private static UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");


    @Before
    public void setup(){

        userMapperMock = createNiceMock(UserMapper.class);
        userRoleMapperMock = createNiceMock(UserRoleMapper.class);

        userService = new UserServiceImpl(userMapperMock);
        userRoleService = new UserRoleServiceImpl(userRoleMapperMock, userService);

        userDTO1.setId(1L);
        userDTO2.setId(2L);

        userRoleDTO1.setId(1L);
        userRoleDTO2.setId(2L);
    }

    @Test
    public void selectUserRolesTest(){

        expect(userRoleService.getUserRoles()).andAnswer(new IAnswer<List<UserRoleDTO>>() {
            @Override
            public List<UserRoleDTO> answer() throws Throwable {
                List<UserRoleDTO> userRoleDTOS = new ArrayList<>();
                userRoleDTOS.add(userRoleDTO1);
                userRoleDTOS.add(userRoleDTO2);

                return userRoleDTOS;
            }
        });

        replay(userMapperMock);
        replay(userRoleMapperMock);

        userRoleService.getUserRoles();
    }

    @Test
    public void selectUserRoleTest(){

        expect(userRoleService.getUserRole(2L)).andAnswer(new IAnswer<UserRoleDTO>() {
            @Override
            public UserRoleDTO answer() throws Throwable {
                return userRoleDTO2;
            }
        });

        replay(userMapperMock);
        replay(userRoleMapperMock);

        UserRoleDTO userRoleDTO = userRoleService.getUserRole(2L);
        assertTrue("UserRoleDTO1 = " + userRoleDTO1,
                userRoleDTO.equals(userRoleDTO1));
    }
//
//    @Test
//    public void updateUserRoleTest(){
//
//        userMapperMock.insertUser(userDTO1, userRoleDTO1);
//
//        UserRoleDTO getUserRole1 = userRoleMapperMock.selectUserRoleByLogin(userDTO1.getLogin());
//        getUserRole1.setRole("USER_ADMIN");
//
//        userRoleMapperMock.updateUserRole(getUserRole1);
//
//        UserRoleDTO userRoleDTO = userRoleMapperMock.selectUserRole(getUserRole1.getId());
//        assertTrue("UserRoleDTO1 = " + userRoleDTO1,
//                userRoleDTO.equals(getUserRole1));
//    }
}
