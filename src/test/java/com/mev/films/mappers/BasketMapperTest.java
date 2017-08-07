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

        List<BasketDTO> basketDTOS = basketMapper.selectAll();
        for (BasketDTO basketDTO : basketDTOS){
            basketMapper.delete(basketDTO.getId());
        }

        List<UserDTO> userDTOS = userMapper.selectsAll();
        for (UserDTO userDTO : userDTOS){
            userMapper.deleteByLogin(userDTO.getLogin());
        }

        userMapper.insert(userDTO1);
        userMapper.insert(userDTO2);

        userRoleMapper.insert(userRoleDTO1);
        userRoleMapper.insert(userRoleDTO2);

        userDTOS = userMapper.selectsIdLogin();

        basketDTO1 = new BasketDTO(userDTOS.get(0));
        basketDTO2 = new BasketDTO(userDTOS.get(1));
    }

    @Test
    public void selectBasketsTest(){

        basketMapper.insert(basketDTO1);
        basketMapper.insert(basketDTO2);

        List<BasketDTO> basketDTOS = basketMapper.selects(1L, 0L);
        assertTrue("count = 1",
                basketDTOS.size() == 1);
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(basketDTO1));

        basketDTOS = basketMapper.selects(1L, 1L);
        assertTrue("count = 1",
                basketDTOS.size() == 1);
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTOS.get(0).equals(basketDTO2));
    }

    @Test
    public void selectBasketsCountTest(){

        Long count = basketMapper.selectsCount();
        assertTrue("count = 0",
                count == 0);

        basketMapper.insert(basketDTO2);
        count = basketMapper.selectsCount();
        assertTrue("count = 1",
                count == 1);

        basketMapper.insert(basketDTO1);
        count = basketMapper.selectsCount();
        assertTrue("count = 2",
                count == 2);
    }

    @Test
    public void selectBasketTest(){

        basketMapper.insert(basketDTO1);
        basketMapper.insert(basketDTO2);

        List<BasketDTO> basketDTOS = basketMapper.selectAll();
        BasketDTO basketDTO = basketMapper.select(basketDTOS.get(1).getId());
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTO.equals(basketDTOS.get(1)));
    }

    @Test
    public void selectBasketByUserTest() {

        basketMapper.insert(basketDTO1);
        basketMapper.insert(basketDTO2);

        BasketDTO basketDTO = basketMapper.selectByUser(basketDTO1.getUserDTO().getId());

        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTO.equals(basketDTO1));
    }

    @Test
    public void insertBasketTest(){

        basketMapper.insert(basketDTO1);

        List<BasketDTO> basketDTOS = basketMapper.selectAll();
        assertTrue("count = 1",
                basketDTOS.size() == 1);
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(basketDTO1));

        basketMapper.insert(basketDTO2);

        basketDTOS = basketMapper.selectAll();
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
        basketMapper.insert(basketDTO1);

        List<BasketDTO> basketDTOS = basketMapper.selectAll();
        basketMapper.delete(basketDTOS.get(0).getId());

        Long count = basketMapper.selectsCount();
        System.out.println("count = " + count);
        assertTrue("count = 0",
                count == 0);

        // insert 2 delete 2
        basketMapper.insert(basketDTO1);
        basketMapper.insert(basketDTO2);

        basketDTOS = basketMapper.selectAll();
        basketMapper.delete(basketDTOS.get(0).getId());
        basketMapper.delete(basketDTOS.get(1).getId());

        count = basketMapper.selectsCount();
        System.out.println("count = " + count);
        assertTrue("count = 0",
                count == 0);

        // insert 2 delete 1
        basketMapper.insert(basketDTO1);
        basketMapper.insert(basketDTO2);

        basketDTOS = basketMapper.selectAll();
        basketMapper.delete(basketDTOS.get(0).getId());

        basketDTOS = basketMapper.selectAll();
        assertTrue("count = 1",
                basketDTOS.size() == 1);
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTOS.get(0).equals(basketDTO2));
    }

    @Test
    public void deleteBasketByUserTest(){

        // insert 1 delete 1
        basketMapper.insert(basketDTO1);

        List<BasketDTO> basketDTOS = basketMapper.selectAll();
        basketMapper.deleteByUser(basketDTOS.get(0).getUserDTO().getId());

        Long count = basketMapper.selectsCount();
        assertTrue("count = 0",
                count == 0);

        // insert 2 delete 2
        basketMapper.insert(basketDTO1);
        basketMapper.insert(basketDTO2);

        basketDTOS = basketMapper.selectAll();
        basketMapper.deleteByUser(basketDTOS.get(0).getUserDTO().getId());
        basketMapper.deleteByUser(basketDTOS.get(1).getUserDTO().getId());

        count = basketMapper.selectsCount();
        assertTrue("count = 0",
                count == 0);

        // insert 2 delete 1
        basketMapper.insert(basketDTO1);
        basketMapper.insert(basketDTO2);

        basketDTOS = basketMapper.selectAll();
        basketMapper.deleteByUser(basketDTOS.get(0).getUserDTO().getId());

        basketDTOS = basketMapper.selectAll();
        assertTrue("count = 1",
                basketDTOS.size() == 1);
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTOS.get(0).equals(basketDTO2));
    }
}
