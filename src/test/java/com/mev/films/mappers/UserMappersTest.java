package com.mev.films.mappers;


import com.mev.films.mappers.interfaces.UserMapper;
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
public class UserMappersTest {

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
    public void getAllUsersTest(){

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        List<UserDTO> users = userMapper.selectUsers();

        assertTrue("UserDTO1 = " + users.get(0),
                users.get(0).equals(userDTO1));
        assertTrue("UserDTO2 = " + users.get(1),
                users.get(1).equals(userDTO2));
    }

    @Test
    public void getUserTest(){

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        UserDTO userDTO = userMapper.selectUserByLogin(userDTO2.getLogin());
        assertTrue("UserDTO2 = " + userDTO,
                userDTO.equals(userDTO2));
    }

    @Test
    public void addUserTest(){

        userMapper.insertUser(userDTO1, userRoleDTO1);

        UserDTO getUser3 = userMapper.selectUserByLogin(userDTO1.getLogin());
        assertTrue("UserDTO1 = " + userDTO1,
                getUser3.equals(userDTO1));
    }

    @Test
    public void updateUserTest(){

        userMapper.insertUser(userDTO2, userRoleDTO2);

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

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        UserDTO getUserDTO1 = userMapper.selectUserByLogin(userDTO1.getLogin());
        UserDTO getUserDTO2 = userMapper.selectUserByLogin(userDTO2.getLogin());

        userMapper.deleteUser(getUserDTO1.getId());
        userMapper.deleteUser(getUserDTO2.getId());

        List<UserDTO> usersAfterDelete = userMapper.selectUsers();
        assertTrue("usersAll = " + usersAfterDelete.size(), usersAfterDelete.size() == 0);
    }
}
