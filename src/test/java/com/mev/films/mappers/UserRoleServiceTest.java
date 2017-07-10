package com.mev.films.mappers;

import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.mappers.interfaces.UserRoleMapper;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;
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
public class UserRoleServiceTest {

    @Autowired private UserRoleMapper userRoleMapper;
    @Autowired private UserMapper userMapper;

    private static UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
    private static UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);

    private static UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private static UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");


    @Before
    public void setup(){
        List<UserDTO> userDTOS = userMapper.selectUsers();
        for (UserDTO userDTO : userDTOS) {
            userMapper.deleteUser(userDTO.getId());
        }
    }

    @Test
    public void getUserRolesTest(){

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        List<UserRoleDTO> userRoles = userRoleMapper.selectUserRoles();

        assertTrue("login1 = " + userRoles.get(0).getLogin(),
                userRoles.get(0).getLogin().equals(userRoleDTO1.getLogin()));
        assertTrue("role1 = " + userRoles.get(0).getRole(),
                userRoles.get(0).getRole().equals(userRoleDTO1.getRole()));
    }

    @Test
    public void getUserRoleTest(){

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        UserRoleDTO userRoleDTO = userRoleMapper.selectUserRoleByLogin(userDTO1.getLogin());
        assertTrue("UserRoleDTO1 = " + userRoleDTO1,
                userRoleDTO.equals(userRoleDTO1));
    }

    @Test
    public void updateUserRoleTest(){

        userMapper.insertUser(userDTO1, userRoleDTO1);

        UserRoleDTO getUserRole1 = userRoleMapper.selectUserRoleByLogin(userDTO1.getLogin());
        getUserRole1.setRole("USER_ADMIN");

        userRoleMapper.updateUserRole(getUserRole1);

        UserRoleDTO userRoleDTO = userRoleMapper.selectUserRole(getUserRole1.getId());
        assertTrue("UserRoleDTO1 = " + userRoleDTO1,
                userRoleDTO.equals(getUserRole1));
    }
}
