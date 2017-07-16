package com.mev.films.mappers;

import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserDiscountDTO;
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
public class UserDiscountMapperTest {

    @Autowired private UserMapper userMapper;
    @Autowired private DiscountMapper discountMapper;
    @Autowired private UserDiscountMapper userDiscountMapper;

    private UserDTO userDTO1 = new UserDTO("user1", "password1", (short) 0);
    private UserDTO userDTO2 = new UserDTO("user2", "password2", (short) 1);

    private UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");

    private DiscountDTO discountDTO1 = new DiscountDTO("code1");
    private DiscountDTO discountDTO2 = new DiscountDTO("code2");

    @Before
    public void setup(){

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        for (UserDiscountDTO userDiscountDTO : userDiscountDTOS){
            userDiscountMapper.deleteUserDiscount(userDiscountDTO.getId());
        }

        List<UserDTO> userDTOS = userMapper.selectUsers();
        for (UserDTO userDTO : userDTOS){
            userMapper.deleteUser(userDTO.getId());
        }

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        for (DiscountDTO discountDTO : discountDTOS){
            discountMapper.deleteDiscountByCode(discountDTO.getCode());
        }
    }

    @Test
    public void selectUserDiscountsTest(){

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsersIdLogin();
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();

        UserDiscountDTO userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);
        UserDiscountDTO userDiscountDTO2 = new UserDiscountDTO(userDTOS.get(1), discountDTOS.get(1), false);

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(1).equals(userDiscountDTO2));
    }

    @Test
    public void selectUserDiscountTest() {

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsersIdLogin();
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();

        UserDiscountDTO userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);
        UserDiscountDTO userDiscountDTO2 = new UserDiscountDTO(userDTOS.get(1), discountDTOS.get(1), false);

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();

        UserDiscountDTO userDiscountDTO = userDiscountMapper.selectUserDiscount(userDiscountDTOS.get(0).getId());

        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTO.equals(userDiscountDTO1));
    }

    @Test
    public void selectUserDiscountsByUserTest(){

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsersIdLogin();
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();

        UserDiscountDTO userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);
        UserDiscountDTO userDiscountDTO2 = new UserDiscountDTO(userDTOS.get(1), discountDTOS.get(1), false);

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();

        userDiscountDTOS = userDiscountMapper.selectUserDiscountsByUser(userDiscountDTOS.get(0).getUserDTO().getId());

        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));
    }

    @Test
    public void selectUserDiscountByDiscountTest(){

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsersIdLogin();
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();

        UserDiscountDTO userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);
        UserDiscountDTO userDiscountDTO2 = new UserDiscountDTO(userDTOS.get(1), discountDTOS.get(1), false);

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();

        UserDiscountDTO userDiscountDTO = userDiscountMapper.selectUserDiscountByDiscount(userDiscountDTOS.get(1).getDiscountDTO().getId());

        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTO.equals(userDiscountDTO2));
    }

    @Test
    public void insertUserDiscountTest(){

        userMapper.insertUser(userDTO2, userRoleDTO2);
        discountMapper.insertDiscount(discountDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsersIdLogin();
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();

        UserDiscountDTO userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));
    }

    @Test
    public void updateUserDiscountTest(){

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsersIdLogin();
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();

        UserDiscountDTO userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountDTOS.get(0).setUserDTO(userDTOS.get(1));
        userDiscountDTOS.get(0).setDiscountDTO(discountDTOS.get(1));
        userDiscountDTOS.get(0).setUsed(false);

        userDiscountMapper.updateUserDiscount(userDiscountDTOS.get(0));

        List<UserDiscountDTO> userDiscountDTOSUpdate = userDiscountMapper.selectUserDiscounts();
        assertTrue("userDiscountDTO2 = " + userDiscountDTOS.get(0).toString(),
                userDiscountDTOSUpdate.get(0).equals(userDiscountDTOS.get(0)));
    }

    @Test
    public void deleteUserDiscountTest(){

        userMapper.insertUser(userDTO2, userRoleDTO2);
        discountMapper.insertDiscount(discountDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsersIdLogin();
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();

        UserDiscountDTO userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscount(userDiscountDTOS.get(0).getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);
    }

    @Test
    public void deleteUserDiscountByDiscountTest(){

        userMapper.insertUser(userDTO2, userRoleDTO2);
        discountMapper.insertDiscount(discountDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsersIdLogin();
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();

        UserDiscountDTO userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscountByDiscount(userDiscountDTOS.get(0).getDiscountDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);
    }

    @Test
    public void deleteUserDiscountByUser(){

        userMapper.insertUser(userDTO2, userRoleDTO2);
        discountMapper.insertDiscount(discountDTO2);

        List<UserDTO> userDTOS = userMapper.selectUsersIdLogin();
        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();

        UserDiscountDTO userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscountByUser(userDiscountDTOS.get(0).getUserDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);
    }
}