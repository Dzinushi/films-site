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
public class UserRoleMapperTest {

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
            userMapper.deleteUserByLogin(userDTO.getLogin());
        }
    }

    @Test
    public void selectUserRoles(){

        userMapper.insertUser(userDTO1);
        userMapper.insertUser(userDTO2);

        userRoleMapper.insertUserRole(userRoleDTO1);
        userRoleMapper.insertUserRole(userRoleDTO2);

        List<UserRoleDTO> userRoleDTOS = userRoleMapper.selectUserRoles();
        assertTrue("count = 2",
                userRoleDTOS.size() == 2);
        assertTrue("userRoleDTO1 = " + userRoleDTO1.toString(),
                userRoleDTOS.get(0).equals(userRoleDTO1));
        assertTrue("userRoleDTO2 = " + userRoleDTO2.toString(),
                userRoleDTOS.get(1).equals(userRoleDTO2));
    }

    @Test
    public void selectUserRole(){

        userMapper.insertUser(userDTO2);
        userMapper.insertUser(userDTO1);

        userRoleMapper.insertUserRole(userRoleDTO2);
        userRoleMapper.insertUserRole(userRoleDTO1);

        List<UserRoleDTO> userRoleDTOS = userRoleMapper.selectUserRoles();
        UserRoleDTO userRoleDTO = userRoleMapper.selectUserRole(userRoleDTOS.get(1).getId());
        assertTrue("userRoleDTO1 = " + userRoleDTO2.toString(),
                userRoleDTO.equals(userRoleDTO1));
    }

    @Test
    public void selectUserRoleByLogin(){

        userMapper.insertUser(userDTO2);
        userMapper.insertUser(userDTO1);

        userRoleMapper.insertUserRole(userRoleDTO2);
        userRoleMapper.insertUserRole(userRoleDTO1);

        UserRoleDTO userRoleDTO = userRoleMapper.selectUserRoleByLogin(userRoleDTO1.getLogin());
        assertTrue("userRoleDTO1 = " + userRoleDTO1.toString(),
                userRoleDTO.equals(userRoleDTO1));
    }

    @Test
    public void insertUserRole(){

        userMapper.insertUser(userDTO2);
        userRoleMapper.insertUserRole(userRoleDTO2);

        List<UserRoleDTO> userRoleDTOS = userRoleMapper.selectUserRoles();
        assertTrue("count = 1",
                userRoleDTOS.size() == 1);
        assertTrue("userRoleDTO2 = " + userRoleDTO2.toString(),
                userRoleDTOS.get(0).equals(userRoleDTO2));

        userMapper.insertUser(userDTO1);
        userRoleMapper.insertUserRole(userRoleDTO1);

        userRoleDTOS = userRoleMapper.selectUserRoles();
        assertTrue("count = 2",
                userRoleDTOS.size() == 2);
        assertTrue("userRoleDTO2 = " + userRoleDTO2.toString(),
                userRoleDTOS.get(0).equals(userRoleDTO2));
        assertTrue("userRoleDTO1 = " + userRoleDTO1.toString(),
                userRoleDTOS.get(1).equals(userRoleDTO1));
    }

    @Test
    public void updateUserRole(){

        userMapper.insertUser(userDTO2);
        userRoleMapper.insertUserRole(userRoleDTO2);

        List<UserRoleDTO> userRoleDTOS = userRoleMapper.selectUserRoles();
        UserRoleDTO userRoleDTO = userRoleDTOS.get(0);
        userRoleDTO.setRole("USER_BANNED");

        userRoleMapper.updateUserRole(userRoleDTO);

        userRoleDTOS = userRoleMapper.selectUserRoles();
        assertTrue("userRoleDTO = " + userRoleDTO.toString(),
                userRoleDTOS.get(0).equals(userRoleDTO));
    }
}
