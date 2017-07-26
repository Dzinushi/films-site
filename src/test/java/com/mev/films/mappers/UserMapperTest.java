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
public class UserMapperTest {

    @Autowired private UserMapper userMapper;
    @Autowired private UserRoleMapper userRoleMapper;

    private static UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
    private static UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);

    @Before
    public void setup(){

        List<UserDTO> userDTOS = userMapper.selectUsers();
        for (UserDTO userDTO : userDTOS) {
            userMapper.deleteUser(userDTO.getId());
        }
    }

    @Test
    public void selectUsersTest(){

        userMapper.insertUser(userDTO1);
        userMapper.insertUser(userDTO2);

        List<UserDTO> users = userMapper.selectUsers();
        assertTrue("count = 2",
                users.size() == 2);
        assertTrue("userDTO1 = " + users.get(0),
                users.get(0).equals(userDTO1));
        assertTrue("userDTO2 = " + users.get(1),
                users.get(1).equals(userDTO2));
    }

    @Test
    public void selectUserTest(){

        userMapper.insertUser(userDTO1);
        userMapper.insertUser(userDTO2);

        List<UserDTO> users = userMapper.selectUsers();
        UserDTO userDTO = userMapper.selectUser(users.get(1).getId());

        assertTrue("userDTO2 = " + users.get(1),
                userDTO.equals(users.get(1)));
    }

    @Test
    public void selectUsersSortByLoginTest(){

        userMapper.insertUser(userDTO2);
        userMapper.insertUser(userDTO1);

        List<UserDTO> userDTOS = userMapper.selectUsersSortByLogin();
        assertTrue("count = 2",
                userDTOS.size() == 2);
        assertTrue("userDTO2 = " + userDTO1.toString(),
                userDTOS.get(0).equals(userDTO1));
        assertTrue("userDTO1 = " + userDTO2.toString(),
                userDTOS.get(1).equals(userDTO2));
    }

    @Test
    public void selectUsersIdLogin(){

        userMapper.insertUser(userDTO1);
        userMapper.insertUser(userDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsersIdLogin();
        assertTrue("count = 2",
                userDTOS.size() == 2);
        assertTrue("login1 = " + userDTO1.getLogin(),
                userDTOS.get(0).getLogin().equals(userDTO1.getLogin()));
        assertTrue("password1 = null",
                userDTOS.get(0).getPassword() == null);

        assertTrue("login2 = " + userDTO2.getLogin(),
                userDTOS.get(1).getLogin().equals(userDTO2.getLogin()));
        assertTrue("password2 = null",
                userDTOS.get(1).getPassword() == null);
    }

    @Test
    public void selectUserByLoginTest(){

        userMapper.insertUser(userDTO1);
        userMapper.insertUser(userDTO2);

        UserDTO userDTO = userMapper.selectUserByLogin(userDTO2.getLogin());
        assertTrue("userDTO2 = " + userDTO,
                userDTO.equals(userDTO2));
    }

    @Test
    public void insertUserTest(){

        userMapper.insertUser(userDTO1);

        List<UserDTO> userDTOS = userMapper.selectUsers();
        assertTrue("count = 1",
                userDTOS.size() == 1);
        assertTrue("UserDTO1 = " + userDTO1,
                userDTOS.get(0).equals(userDTO1));

        userMapper.insertUser(userDTO2);

        userDTOS = userMapper.selectUsers();
        assertTrue("count = 1",
                userDTOS.size() == 1);
        assertTrue("userDTO1 = " + userDTO1,
                userDTOS.get(0).equals(userDTO1));
        assertTrue("userDTO2 = " + userDTO2,
                userDTOS.get(1).equals(userDTO2));
    }

    @Test
    public void updateUserTest(){

        userMapper.insertUser(userDTO1);
        userMapper.insertUser(userDTO2);

        UserDTO getUserDTO2 = userMapper.selectUserByLogin(userDTO2.getLogin());
        getUserDTO2.setPassword("user2p");
        getUserDTO2.setEnabled((short) 2);

        userMapper.updateUser(getUserDTO2);

        UserDTO userDTO = userMapper.selectUserByLogin("user2");
        assertTrue("userDTO2 = " + getUserDTO2,
                userDTO.equals(getUserDTO2));
    }

    @Test
    public void deleteUserTest(){

        UserRoleDTO userRoleDTO1 = new UserRoleDTO(userDTO1.getLogin(), "USER_ROLE");
        UserRoleDTO userRoleDTO2 = new UserRoleDTO(userDTO2.getLogin(), "USER_ROLE");

        // insert 1 delete 1
        userMapper.insertUser(userDTO2);
        userRoleMapper.insertUserRole(userRoleDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsers();
        userMapper.deleteUser(userDTOS.get(0).getId());

        userDTOS = userMapper.selectUsers();
        assertTrue("count = 0",
                userDTOS.size() == 0);

        List<UserRoleDTO> userRoleDTOS = userRoleMapper.selectUserRoles();
        assertTrue("count = 0",
                userRoleDTOS.size() == 0);

        // insert 2 delete 2
        userMapper.insertUser(userDTO2);
        userMapper.insertUser(userDTO1);

        userDTOS = userMapper.selectUsers();
        userMapper.deleteUser(userDTOS.get(0).getId());
        userMapper.deleteUser(userDTOS.get(1).getId());

        userDTOS = userMapper.selectUsers();
        assertTrue("count = 0",
                userDTOS.size() == 0);

        userRoleDTOS = userRoleMapper.selectUserRoles();
        assertTrue("count = 0",
                userRoleDTOS.size() == 0);

        // insert 2 delete 1
        userMapper.insertUser(userDTO2);
        userMapper.insertUser(userDTO1);
        userRoleMapper.insertUserRole(userRoleDTO1);
        userRoleMapper.insertUserRole(userRoleDTO2);

        userDTOS = userMapper.selectUsers();
        userMapper.deleteUser(userDTOS.get(0).getId());

        userDTOS = userMapper.selectUsers();
        assertTrue("count = 1",
                userDTOS.size() == 1);
        assertTrue("userDTO1 = " + userDTO1.toString(),
                userDTOS.get(0).equals(userDTO1));

        userRoleDTOS = userRoleMapper.selectUserRoles();
        assertTrue("count = 0",
                userRoleDTOS.size() == 0);
        assertTrue("userRoleDTO1 = " + userRoleDTOS.get(0).toString(),
                userRoleDTOS.get(0).equals(userRoleDTO1));
    }
}
