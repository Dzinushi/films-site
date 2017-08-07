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

        List<UserDTO> userDTOS = userMapper.selects();
        for (UserDTO userDTO : userDTOS) {
            userMapper.deleteByLogin(userDTO.getLogin());
        }
    }

    @Test
    public void selectUsersTest(){

        userMapper.insert(userDTO1);
        userMapper.insert(userDTO2);

        List<UserDTO> users = userMapper.selects();
        assertTrue("count = 2",
                users.size() == 2);
        assertTrue("userDTO1 = " + users.get(0),
                users.get(0).equals(userDTO1));
        assertTrue("userDTO2 = " + users.get(1),
                users.get(1).equals(userDTO2));
    }

    @Test
    public void selectUserTest(){

        userMapper.insert(userDTO1);
        userMapper.insert(userDTO2);

        List<UserDTO> users = userMapper.selects();
        UserDTO userDTO = userMapper.select(users.get(1).getId());

        assertTrue("userDTO2 = " + users.get(1),
                userDTO.equals(users.get(1)));
    }

    @Test
    public void selectUsersSortByLoginTest(){

        userMapper.insert(userDTO2);
        userMapper.insert(userDTO1);

        List<UserDTO> userDTOS = userMapper.selectsSortByLogin();
        assertTrue("count = 2",
                userDTOS.size() == 2);
        assertTrue("userDTO2 = " + userDTO1.toString(),
                userDTOS.get(0).equals(userDTO1));
        assertTrue("userDTO1 = " + userDTO2.toString(),
                userDTOS.get(1).equals(userDTO2));
    }

    @Test
    public void selectUsersIdLogin(){

        userMapper.insert(userDTO1);
        userMapper.insert(userDTO2);

        List<UserDTO> userDTOS = userMapper.selectsIdLogin();
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

        userMapper.insert(userDTO1);
        userMapper.insert(userDTO2);

        UserDTO userDTO = userMapper.selectByLogin(userDTO2.getLogin());
        assertTrue("userDTO2 = " + userDTO,
                userDTO.equals(userDTO2));
    }

    @Test
    public void insertUserTest(){

        userMapper.insert(userDTO1);

        List<UserDTO> userDTOS = userMapper.selects();
        assertTrue("count = 1",
                userDTOS.size() == 1);
        assertTrue("UserDTO1 = " + userDTO1,
                userDTOS.get(0).equals(userDTO1));

        userMapper.insert(userDTO2);

        userDTOS = userMapper.selects();
        assertTrue("count = 2",
                userDTOS.size() == 2);
        assertTrue("userDTO1 = " + userDTO1,
                userDTOS.get(0).equals(userDTO1));
        assertTrue("userDTO2 = " + userDTO2,
                userDTOS.get(1).equals(userDTO2));
    }

    @Test
    public void updateUserTest(){

        userMapper.insert(userDTO1);
        userMapper.insert(userDTO2);

        UserDTO getUserDTO2 = userMapper.selectByLogin(userDTO2.getLogin());
        getUserDTO2.setPassword("user2p");
        getUserDTO2.setEnabled((short) 2);

        userMapper.update(getUserDTO2);

        UserDTO userDTO = userMapper.selectByLogin("user2");
        assertTrue("userDTO2 = " + getUserDTO2,
                userDTO.equals(getUserDTO2));
    }

    @Test
    public void deleteUserByLoginTest(){

        UserRoleDTO userRoleDTO1 = new UserRoleDTO(userDTO1.getLogin(), "USER_ROLE");
        UserRoleDTO userRoleDTO2 = new UserRoleDTO(userDTO2.getLogin(), "USER_ROLE");

        // insert 1 delete 1
        userMapper.insert(userDTO2);
        userRoleMapper.insert(userRoleDTO2);

        userMapper.deleteByLogin(userDTO2.getLogin());

        List<UserDTO> userDTOS = userMapper.selects();
        assertTrue("count = 0",
                userDTOS.size() == 0);

        List<UserRoleDTO> userRoleDTOS = userRoleMapper.select();
        assertTrue("count = 0",
                userRoleDTOS.size() == 0);

        // insert 2 delete 2
        userMapper.insert(userDTO2);
        userMapper.insert(userDTO1);
        userRoleMapper.insert(userRoleDTO2);
        userRoleMapper.insert(userRoleDTO1);

        userMapper.deleteByLogin(userDTO2.getLogin());
        userMapper.deleteByLogin(userDTO1.getLogin());

        userDTOS = userMapper.selects();
        assertTrue("count = 0",
                userDTOS.size() == 0);

        userRoleDTOS = userRoleMapper.select();
        assertTrue("count = 0",
                userRoleDTOS.size() == 0);

        // insert 2 delete 1
        userMapper.insert(userDTO2);
        userMapper.insert(userDTO1);
        userRoleMapper.insert(userRoleDTO2);
        userRoleMapper.insert(userRoleDTO1);

        userMapper.deleteByLogin(userDTO2.getLogin());

        userDTOS = userMapper.selects();
        assertTrue("count = 1",
                userDTOS.size() == 1);
        assertTrue("userDTO1 = " + userDTO1.toString(),
                userDTOS.get(0).equals(userDTO1));

        userRoleDTOS = userRoleMapper.select();
        assertTrue("count = 1",
                userRoleDTOS.size() == 1);
        assertTrue("userRoleDTO1 = " + userRoleDTOS.get(0).toString(),
                userRoleDTOS.get(0).equals(userRoleDTO1));
    }
}
