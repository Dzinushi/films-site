//package com.mev.films.service;
//
//
//import com.mev.films.mappers.interfaces.UserMapper;
//import com.mev.films.mappers.interfaces.UserRoleMapper;
//import com.mev.films.model.UserDTO;
//import com.mev.films.model.UserRoleDTO;
//import com.mev.films.service.interfaces.UserService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
//import static junit.framework.TestCase.assertTrue;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
//public class UserServiceTest {
//
//    @Autowired private UserMapper userMapper;
//    @Autowired private UserRoleMapper userRoleMapper;
//
//    @Autowired private UserService userService;
//
//    private static UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
//    private static UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);
//
//    private static UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
//    private static UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");
//
//    @Before
//    public void setup(){
//
//        List<UserDTO> userDTOS = userService.getAllUsers();
//        for (UserDTO userDTO : userDTOS) {
//            userService.deleteUser(userDTO.getId());
//        }
//    }
//
//    @Test
//    public void getAllUsersTest(){
//
//        userMapper.insertUser(userDTO1);
//        userMapper.insertUser(userDTO2);
//
//        List<UserDTO> users = userService.getAllUsers();
//
//        assertTrue("login1 = " + users.get(0).getLogin(),
//                users.get(0).getLogin().equals(userDTO1.getLogin()));
//        assertTrue("password1 = " + users.get(0).getPassword(),
//                users.get(0).getPassword().equals(userDTO1.getPassword()));
//        assertTrue("enabled1 = " + users.get(0).getEnabled(),
//                users.get(0).getEnabled() == userDTO1.getEnabled());
//
//        assertTrue("login2 = " + users.get(1).getLogin(),
//                users.get(1).getLogin().equals(userDTO2.getLogin()));
//        assertTrue("password2 = " + users.get(1).getPassword(),
//                users.get(1).getPassword().equals(userDTO2.getPassword()));
//        assertTrue("enabled2 = " + users.get(1).getEnabled(),
//                users.get(1).getEnabled() == userDTO2.getEnabled());
//    }
//
//    @Test
//    public void getUserTest(){
//
//        userMapper.insertUser(userDTO1);
//        userMapper.insertUser(userDTO2);
//
//        UserDTO userDTO = userService.getUser(userDTO2.getLogin());
//        assertTrue("password2 = " + userDTO.getPassword(),
//                userDTO.getPassword().equals(userDTO2.getPassword()));
//        assertTrue("enabled2 = " + userDTO.getEnabled(),
//                userDTO.getEnabled() == userDTO2.getEnabled());
//    }
//
//    @Test
//    public void getUserRolesTest(){
//
//        userRoleMapper.insertUserRole(userRoleDTO1);
//        userRoleMapper.insertUserRole(userRoleDTO2);
//
//        List<UserRoleDTO> userRoles = userService.getUserRoles();
//
//        assertTrue("login1 = " + userRoles.get(0).getLogin(),
//                userRoles.get(0).getLogin().equals(userRoleDTO1.getLogin()));
//        assertTrue("role1 = " + userRoles.get(0).getRole(),
//                userRoles.get(0).getRole().equals(userRoleDTO1.getRole()));
//
//        assertTrue("login2 = " + userRoles.get(1).getLogin(),
//                userRoles.get(1).getLogin().equals(userRoleDTO2.getLogin()));
//        assertTrue("role2 = " + userRoles.get(1).getRole(),
//                userRoles.get(1).getRole().equals(userRoleDTO2.getRole()));
//    }
//
//    @Test
//    public void getUserRoleTest(){
//
//        userRoleMapper.insertUserRole(userRoleDTO1);
//        userRoleMapper.insertUserRole(userRoleDTO2);
//
//        List<UserRoleDTO> userRoles = userService.getUserRole(userDTO1.getLogin());
//        assertTrue("role1 = " + userRoles.get(0).getRole(),
//                userRoles.get(0).getRole().equals(userRoleDTO1.getRole()));
//    }
//
//    @Test
//    public void addUserTest(){
//
//        UserDTO userDTO3 = new UserDTO("user3", "user3", (short) 1);
//        UserRoleDTO userRoleDTO3 = new UserRoleDTO(userDTO3.getLogin(), "ROLE_USER");
//
//        userService.addUser(userDTO3, userRoleDTO3);
//
//        UserDTO getUser3 = userService.getUser(userDTO3.getLogin());
//        assertTrue("password3 = " + getUser3.getPassword(),
//                getUser3.getPassword().equals(userDTO3.getPassword()));
//        assertTrue("enabled2 = " + getUser3.getEnabled(),
//                getUser3.getEnabled() == userDTO3.getEnabled());
//
//        List<UserRoleDTO> getUserRole3 = userService.getUserRole(userDTO3.getLogin());
//        assertTrue("role3 = " + getUserRole3.get(0).getRole(),
//                getUserRole3.get(0).getRole().equals(userRoleDTO3.getRole()));
//    }
//
//    @Test
//    public void addUserRoleTest(){
//
//        UserRoleDTO userRoleDTO3 = new UserRoleDTO("user3", "ROLE_USER");
//
//        userService.addUserRole(userRoleDTO3);
//
//        List<UserRoleDTO> getUserRole3 = userService.getUserRole(userRoleDTO3.getLogin());
//        assertTrue("role3 = " + getUserRole3.get(0).getRole(),
//                getUserRole3.get(0).getRole().equals(userRoleDTO3.getRole()));
//    }
//
//    @Test
//    public void updateUserTest(){
//
//        userMapper.insertUser(userDTO2);
//
//        UserDTO getUserDTO2 = userService.getUser(userDTO2.getLogin());
//        getUserDTO2.setPassword("user22");
//        getUserDTO2.setEnabled((short) 2);
//
//        userService.updateUser(getUserDTO2);
//
//        getUserDTO2 = userService.getUser("user2");
//        assertTrue("password2 = " + getUserDTO2.getPassword(),
//                getUserDTO2.getPassword().equals("user22"));
//        assertTrue("enabled2 = " + getUserDTO2.getEnabled(),
//                getUserDTO2.getEnabled() == (short) 2);
//    }
//
//    @Test
//    public void updateUserRoleTest(){
//
//        userRoleMapper.insertUserRole(userRoleDTO1);
//
//        List<UserRoleDTO> getUserRole1 = userService.getUserRole(userDTO1.getLogin());
//        getUserRole1.get(0).setLogin("user11");
//        getUserRole1.get(0).setRole("USER_ADMIN");
//
//        userService.updateUserRole(getUserRole1.get(0));
//
//        getUserRole1 = userService.getUserRole("user11");
//        assertTrue("login2 = " + getUserRole1.get(0).getLogin(),
//                getUserRole1.get(0).getLogin().equals("user11"));
//        assertTrue("role2 = " + getUserRole1.get(0).getRole(),
//                getUserRole1.get(0).getRole().equals("USER_ADMIN"));
//    }
//
//    @Test
//    public void deleteUserTest(){
//
//        userMapper.insertUser(userDTO1);
//        userMapper.insertUser(userDTO2);
//        userRoleMapper.insertUserRole(userRoleDTO1);
//        userRoleMapper.insertUserRole(userRoleDTO2);
//
//        UserDTO getUserDTO1 = userService.getUser("user1");
//        assertTrue("password2 = " + getUserDTO1.getPassword(),
//                getUserDTO1.getPassword().equals("user1"));
//
//        UserDTO getUserDTO2 = userService.getUser("user2");
//        assertTrue("password2 = " + getUserDTO2.getPassword(),
//                getUserDTO2.getPassword().equals("user2"));
//
//        List<UserRoleDTO> getUserRoleDTO1 = userService.getUserRole(userRoleDTO1.getLogin());
//        assertTrue("role2 = " + getUserRoleDTO1.get(0).getRole(),
//                getUserRoleDTO1.get(0).getRole().equals(userRoleDTO1.getRole()));
//
//        List<UserRoleDTO> getUserRoleDTO2 = userService.getUserRole(userRoleDTO2.getLogin());
//        assertTrue("role2 = " + getUserRoleDTO1.get(0).getRole(),
//                getUserRoleDTO2.get(0).getRole().equals(userRoleDTO2.getRole()));
//
//        userService.deleteUser(userDTO1.getId());
//        userService.deleteUser(userDTO2.getId());
//
//        List<UserDTO> usersAfterDelete = userService.getAllUsers();
//        assertTrue("usersAll = " + usersAfterDelete.size(), usersAfterDelete.size() == 0);
//
//        List<UserRoleDTO> userRolesAfterDelete = userService.getUserRoles();
//        assertTrue("userRolesAll = " + userRolesAfterDelete.size(), userRolesAfterDelete.size() == 0);
//    }
//}
