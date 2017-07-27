package com.mev.films.mappers;

import com.mev.films.mappers.interfaces.*;
import com.mev.films.model.*;
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
public class BasketMapperTest {

    @Autowired private BasketMapper basketMapper;
    @Autowired private UserMapper userMapper;
    @Autowired private UserRoleMapper userRoleMapper;

    private static UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
    private static UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);

    private static UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private static UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");

    private BasketDTO basketDTO1;
    private BasketDTO basketDTO2;

    @Before
    public void setup(){

        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        for (BasketDTO basketDTO : basketDTOS){
            basketMapper.deleteBasket(basketDTO.getId());
        }

        List<UserDTO> userDTOS = userMapper.selectUsers();
        for (UserDTO userDTO : userDTOS){
            userMapper.deleteUserByLogin(userDTO.getLogin());
        }

        userMapper.insertUser(userDTO1);
        userMapper.insertUser(userDTO2);

        userRoleMapper.insertUserRole(userRoleDTO1);
        userRoleMapper.insertUserRole(userRoleDTO2);

        userDTOS = userMapper.selectUsersIdLogin();

        basketDTO1 = new BasketDTO(userDTOS.get(0));
        basketDTO2 = new BasketDTO(userDTOS.get(1));
    }

    @Test
    public void selectBasketsTest(){

        basketMapper.insertBasket(basketDTO1);
        basketMapper.insertBasket(basketDTO2);

        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 2",
                basketDTOS.size() == 2);
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(basketDTO1));
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTOS.get(1).equals(basketDTO2));
    }

    @Test
    public void selectBasketTest(){
        basketMapper.insertBasket(basketDTO1);
        basketMapper.insertBasket(basketDTO2);

        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        BasketDTO basketDTO = basketMapper.selectBasket(basketDTOS.get(1).getId());
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTO.equals(basketDTOS.get(1)));
    }

    @Test
    public void selectBasketByUserTest() {

        basketMapper.insertBasket(basketDTO1);
        basketMapper.insertBasket(basketDTO2);

        BasketDTO basketDTO = basketMapper.selectBasketByUser(basketDTO1.getUserDTO().getId());

        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTO.equals(basketDTO1));
    }

    @Test
    public void insertBasketTest(){

        basketMapper.insertBasket(basketDTO1);

        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 1",
                basketDTOS.size() == 1);
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(basketDTO1));

        basketMapper.insertBasket(basketDTO2);

        basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 2",
                basketDTOS.size() == 2);
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(basketDTO1));
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTOS.get(1).equals(basketDTO2));
    }

    @Test
    public void deleteBasketTest(){

        // insert 1 delete 1
        basketMapper.insertBasket(basketDTO1);

        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        basketMapper.deleteBasket(basketDTOS.get(0).getId());

        basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 0",
                basketDTOS.size() == 0);

        // insert 2 delete 2
        basketMapper.insertBasket(basketDTO1);
        basketMapper.insertBasket(basketDTO2);

        basketDTOS = basketMapper.selectBaskets();
        basketMapper.deleteBasket(basketDTOS.get(0).getId());
        basketMapper.deleteBasket(basketDTOS.get(1).getId());

        basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 0",
                basketDTOS.size() == 0);

        // insert 2 delete 1
        basketMapper.insertBasket(basketDTO1);
        basketMapper.insertBasket(basketDTO2);

        basketDTOS = basketMapper.selectBaskets();
        basketMapper.deleteBasket(basketDTOS.get(0).getId());

        basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 1",
                basketDTOS.size() == 1);
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTOS.get(0).equals(basketDTO2));
    }

    @Test
    public void deleteBasketByUserTest(){

        // insert 1 delete 1
        basketMapper.insertBasket(basketDTO1);

        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        basketMapper.deleteBasketByUser(basketDTOS.get(0).getUserDTO().getId());

        basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 0",
                basketDTOS.size() == 0);

        // insert 2 delete 2
        basketMapper.insertBasket(basketDTO1);
        basketMapper.insertBasket(basketDTO2);

        basketDTOS = basketMapper.selectBaskets();
        basketMapper.deleteBasketByUser(basketDTOS.get(0).getUserDTO().getId());
        basketMapper.deleteBasketByUser(basketDTOS.get(1).getUserDTO().getId());

        basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 0",
                basketDTOS.size() == 0);

        // insert 2 delete 1
        basketMapper.insertBasket(basketDTO1);
        basketMapper.insertBasket(basketDTO2);

        basketDTOS = basketMapper.selectBaskets();
        basketMapper.deleteBasketByUser(basketDTOS.get(0).getUserDTO().getId());

        basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 1",
                basketDTOS.size() == 1);
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTOS.get(0).equals(basketDTO2));
    }
}
