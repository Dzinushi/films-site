package com.mev.films.mappers;

import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.mappers.interfaces.UserDiscountMapper;
import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserDiscountDTO;
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

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectsAll();
        for (UserDiscountDTO userDiscountDTO : userDiscountDTOS){
            userDiscountMapper.delete(userDiscountDTO.getId());
        }

        List<UserDTO> userDTOS = userMapper.selectsAll();
        for (UserDTO userDTO : userDTOS){
            userMapper.deleteByLogin(userDTO.getLogin());
        }

        List<DiscountDTO> discountDTOS = discountMapper.selectsAll();
        for (DiscountDTO discountDTO : discountDTOS){
            discountMapper.deleteByCode(discountDTO.getCode());
        }


        userMapper.insert(userDTO1);
        userMapper.insert(userDTO2);

        discountMapper.insert(discountDTO1);
        discountMapper.insert(discountDTO2);
        discountMapper.insert(discountDTO3);

        userDTOS = userMapper.selectsIdLogin();
        discountDTOS = discountMapper.selectsAll();

        userDiscountDTO1 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(0), true);
        userDiscountDTO2 = new UserDiscountDTO(userDTOS.get(1), discountDTOS.get(1), false);
        userDiscountDTO3 = new UserDiscountDTO(userDTOS.get(0), discountDTOS.get(2), false);
    }

    @Test
    public void selectUserDiscountsTest(){

        userDiscountMapper.insert(userDiscountDTO1);
        userDiscountMapper.insert(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 2",
                userDiscountDTOS.size() == 2);
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(1).equals(userDiscountDTO2));
    }

    @Test
    public void selectUserDiscountTest() {

        userDiscountMapper.insert(userDiscountDTO1);
        userDiscountMapper.insert(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectsAll();
        UserDiscountDTO userDiscountDTO = userDiscountMapper.select(userDiscountDTOS.get(0).getId());

        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTO.equals(userDiscountDTO1));
    }

    @Test
    public void selectUserDiscountsByUserTest(){

        userDiscountMapper.insert(userDiscountDTO1);
        userDiscountMapper.insert(userDiscountDTO2);
        userDiscountMapper.insert(userDiscountDTO3);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectsAll();

        userDiscountDTOS = userDiscountMapper.selectsByUser(userDiscountDTOS.get(0).getUserDTO().getId());
        assertTrue("count = 2",
                userDiscountDTOS.size() == 2);
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));
        assertTrue("userDiscountDTO2 = " + userDiscountDTO3.toString(),
                userDiscountDTOS.get(1).equals(userDiscountDTO3));
    }

    @Test
    public void selectUserDiscountByDiscountTest(){

        userDiscountMapper.insert(userDiscountDTO1);
        userDiscountMapper.insert(userDiscountDTO2);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectsAll();

        UserDiscountDTO userDiscountDTO = userDiscountMapper.selectByDiscount(userDiscountDTOS.get(1).getDiscountDTO().getId());

        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTO.equals(userDiscountDTO2));
    }

    @Test
    public void insertUserDiscountTest(){

        userDiscountMapper.insert(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));

        userDiscountMapper.insert(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 2",
                userDiscountDTOS.size() == 2);
        assertTrue("userDiscountDTO1 = " + userDiscountDTO1.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO1));
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(1).equals(userDiscountDTO2));
    }

    @Test
    public void updateUserDiscountTest(){

        userDiscountMapper.insert(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectsAll();
        userDiscountDTOS.get(0).setUserDTO(userDiscountDTO2.getUserDTO());
        userDiscountDTOS.get(0).setDiscountDTO(userDiscountDTO2.getDiscountDTO());
        userDiscountDTOS.get(0).setUsed(userDiscountDTO2.isUsed());

        userDiscountMapper.update(userDiscountDTOS.get(0));

        List<UserDiscountDTO> userDiscountDTOSUpdate = userDiscountMapper.selectsAll();
        assertTrue("userDiscountDTO2 = " + userDiscountDTOS.get(0).toString(),
                userDiscountDTOSUpdate.get(0).equals(userDiscountDTOS.get(0)));
    }

    @Test
    public void deleteUserDiscountTest(){

        // insert 1 delete 1
        userDiscountMapper.insert(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectsAll();
        userDiscountMapper.delete(userDiscountDTOS.get(0).getId());

        userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 2
        userDiscountMapper.insert(userDiscountDTO1);
        userDiscountMapper.insert(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectsAll();
        userDiscountMapper.delete(userDiscountDTOS.get(0).getId());
        userDiscountMapper.delete(userDiscountDTOS.get(1).getId());

        userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 1
        userDiscountMapper.insert(userDiscountDTO1);
        userDiscountMapper.insert(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectsAll();
        userDiscountMapper.delete(userDiscountDTOS.get(0).getId());

        userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO2));
    }

    @Test
    public void deleteUserDiscountByDiscountTest(){

        // insert 1 delete 1
        userDiscountMapper.insert(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectsAll();
        userDiscountMapper.deleteByDiscount(userDiscountDTOS.get(0).getDiscountDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 2
        userDiscountMapper.insert(userDiscountDTO1);
        userDiscountMapper.insert(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectsAll();
        userDiscountMapper.deleteByDiscount(userDiscountDTOS.get(0).getDiscountDTO().getId());
        userDiscountMapper.deleteByDiscount(userDiscountDTOS.get(1).getDiscountDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 1
        userDiscountMapper.insert(userDiscountDTO1);
        userDiscountMapper.insert(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectsAll();
        userDiscountMapper.deleteByDiscount(userDiscountDTOS.get(0).getDiscountDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO2));
    }

    @Test
    public void deleteUserDiscountByUser(){

        // insert 1 delete 1
        userDiscountMapper.insert(userDiscountDTO1);

        List<UserDiscountDTO> userDiscountDTOS = userDiscountMapper.selectsAll();
        userDiscountMapper.deleteByUser(userDiscountDTOS.get(0).getUserDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 2
        userDiscountMapper.insert(userDiscountDTO1);
        userDiscountMapper.insert(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectsAll();
        userDiscountMapper.deleteByUser(userDiscountDTOS.get(0).getUserDTO().getId());
        userDiscountMapper.deleteByUser(userDiscountDTOS.get(1).getUserDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 0",
                userDiscountDTOS.size() == 0);

        // insert 2 delete 1
        userDiscountMapper.insert(userDiscountDTO1);
        userDiscountMapper.insert(userDiscountDTO2);

        userDiscountDTOS = userDiscountMapper.selectsAll();
        userDiscountMapper.deleteByUser(userDiscountDTOS.get(0).getUserDTO().getId());

        userDiscountDTOS = userDiscountMapper.selectsAll();
        assertTrue("count = 1",
                userDiscountDTOS.size() == 1);
        assertTrue("userDiscountDTO2 = " + userDiscountDTO2.toString(),
                userDiscountDTOS.get(0).equals(userDiscountDTO2));
    }
}