package com.mev.films.mappers;

import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.mappers.interfaces.UserRoleMapper;
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

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.2F);
    private DiscountDTO discountDTO3 = new DiscountDTO("code3", 0.18F);

    private UserDiscountDTO userDiscountDTO1;
    private UserDiscountDTO userDiscountDTO2;
    private UserDiscountDTO userDiscountDTO3;

    @Before
    public void setup(){

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        for (UserDiscountDTO userDiscountDTO : userDiscountDTOS){
            userDiscountMapper.deleteUserDiscount(userDiscountDTO.getId());
        }

        List<UserDTO> userDTOS = userMapper.selectUsers();
        for (UserDTO userDTO : userDTOS){
            userMapper.deleteUserByLogin(userDTO.getLogin());
        }

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        for (DiscountDTO discountDTO : discountDTOS){
            discountMapper.deleteDiscountByCode(discountDTO.getCode());
        }


        userMapper.insertUser(userDTO1);
        userMapper.insertUser(userDTO2);

        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);
        discountMapper.insertDiscount(discountDTO3);

        userDTOS = userMapper.selectUsersIdLogin();
        discountDTOS = discountMapper.selectDiscounts();

        userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);
        userDiscountDTO2 = new UserDiscountDTO(userDTOS.get(1), discountDTOS.get(1), false);
        userDiscountDTO3 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(2), false);
    }

    @Test
    public void selectUserDiscountsTest(){

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 2",
                userDiscountDTOS.size() == 2);
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(1).equals(userDiscountDTO2));
    }

    @Test
    public void selectUserDiscountTest() {

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        UserDiscountDTO userDiscountDTO = userDiscountMapper.selectUserDiscount(userDiscountDTOS.get(0).getId());

        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTO.equals(userDiscountDTO1));
    }

    @Test
    public void selectUserDiscountsByUserTest(){

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);
        userDiscountMapper.insertUserDiscount(userDiscountDTO3);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();

        userDiscountDTOS = userDiscountMapper.selectUserDiscountsByUser(userDiscountDTOS.get(0).getUserDTO().getId());
        assertTrue("count = 2",
                userDiscountDTOS.size() == 2);
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));
        assertTrue("userDiscountDTO2 = " + userDiscountDTO3.toString(),
                userDiscountDTOS.get(1).equals(userDiscountDTO3));
    }

    @Test
    public void selectUserDiscountByDiscountTest(){

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();

        UserDiscountDTO userDiscountDTO = userDiscountMapper.selectUserDiscountByDiscount(userDiscountDTOS.get(1).getDiscountDTO().getId());

        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTO.equals(userDiscountDTO2));
    }

    @Test
    public void insertUserDiscountTest(){

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));

        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 2",
                userDiscountDTOS.size() == 2);
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(1).equals(userDiscountDTO2));
    }

    @Test
    public void updateUserDiscountTest(){

        userDiscountMapper.insertUserDiscount(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountDTOS.get(0).setUserDTO(userDiscountDTO2.getUserDTO());
        userDiscountDTOS.get(0).setDiscountDTO(userDiscountDTO2.getDiscountDTO());
        userDiscountDTOS.get(0).setUsed(userDiscountDTO2.isUsed());

        userDiscountMapper.updateUserDiscount(userDiscountDTOS.get(0));

        List<UserDiscountDTO> userDiscountDTOSUpdate = userDiscountMapper.selectUserDiscounts();
        assertTrue("userDiscountDTO2 = " + userDiscountDTOS.get(0).toString(),
                userDiscountDTOSUpdate.get(0).equals(userDiscountDTOS.get(0)));
    }

    @Test
    public void deleteUserDiscountTest(){

        // insert 1 delete 1
        userDiscountMapper.insertUserDiscount(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscount(userDiscountDTOS.get(0).getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 2
        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscount(userDiscountDTOS.get(0).getId());
        userDiscountMapper.deleteUserDiscount(userDiscountDTOS.get(1).getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 1
        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscount(userDiscountDTOS.get(0).getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO2));
    }

    @Test
    public void deleteUserDiscountByDiscountTest(){

        // insert 1 delete 1
        userDiscountMapper.insertUserDiscount(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscountByDiscount(userDiscountDTOS.get(0).getDiscountDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 2
        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscountByDiscount(userDiscountDTOS.get(0).getDiscountDTO().getId());
        userDiscountMapper.deleteUserDiscountByDiscount(userDiscountDTOS.get(1).getDiscountDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 1
        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscountByDiscount(userDiscountDTOS.get(0).getDiscountDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO2));
    }

    @Test
    public void deleteUserDiscountByUser(){

        // insert 1 delete 1
        userDiscountMapper.insertUserDiscount(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscountByUser(userDiscountDTOS.get(0).getUserDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 2
        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscountByUser(userDiscountDTOS.get(0).getUserDTO().getId());
        userDiscountMapper.deleteUserDiscountByUser(userDiscountDTOS.get(1).getUserDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 1
        userDiscountMapper.insertUserDiscount(userDiscountDTO1);
        userDiscountMapper.insertUserDiscount(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        userDiscountMapper.deleteUserDiscountByUser(userDiscountDTOS.get(0).getUserDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectUserDiscounts();
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO2));
    }
}