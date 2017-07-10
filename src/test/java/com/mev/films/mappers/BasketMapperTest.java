package com.mev.films.mappers;

import com.mev.films.mappers.interfaces.BasketMapper;
import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.mappers.interfaces.FilmMapper;
import com.mev.films.mappers.interfaces.UserMapper;
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
    @Autowired private FilmMapper filmMapper;
    @Autowired private DiscountMapper discountMapper;

    private static UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
    private static UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);

    private static UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private static UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 1, "imageUrl1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre2", (short) 200, 2, "imageUrl2");

    private DiscountDTO discountDTO1 = new DiscountDTO("code1");
    private DiscountDTO discountDTO2 = new DiscountDTO("code2");


    @Before
    public void setup(){
        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        for (BasketDTO basketDTO : basketDTOS){
            basketMapper.deleteBasketByUserFilm(basketDTO);
        }

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        for (DiscountDTO discountDTO : discountDTOS){
            discountMapper.deleteDiscountByCode(discountDTO.getCode());
        }

        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        for (FilmDTO filmDTO : filmDTOS){
            filmMapper.deleteFilmByImage(filmDTO.getImage());
        }

        List<UserDTO> userDTOS = userMapper.selectUsers();
        for (UserDTO userDTO : userDTOS){
            userMapper.deleteUser(userDTO.getId());
        }
    }

    @Test
    public void getAllBasketsTest(){
        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);

        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        List<UserDTO> userDTOS = userMapper.selectUsers();

        BasketDTO basketDTO1 = new BasketDTO(1L, userDTOS.get(0), filmDTOS.get(0), discountDTOS.get(0));
        BasketDTO basketDTO2 = new BasketDTO(2L, userDTOS.get(1), filmDTOS.get(1), discountDTOS.get(1));

        basketMapper.insertBasket(basketDTO1);
        basketMapper.insertBasket(basketDTO2);

        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();

        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(new BasketDTO(1L, userDTOS.get(0), filmDTOS.get(0), discountDTOS.get(0))));
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTOS.get(1).equals(new BasketDTO(2L, userDTOS.get(1), filmDTOS.get(1), discountDTOS.get(1))));
    }

    @Test
    public void getBasketByUserTest() {
        discountMapper.insertDiscount(discountDTO1);
        filmMapper.insertFilm(filmDTO1);
        userMapper.insertUser(userDTO1, userRoleDTO1);

        DiscountDTO discountDTO = discountMapper.selectDiscountByCode(discountDTO1.getCode());
        FilmDTO filmDTO = filmMapper.selectFilmByImage(filmDTO1.getImage());
        UserDTO userDTO = userMapper.selectUserByLogin(userDTO1.getLogin());

        BasketDTO basketDTO1 = new BasketDTO(1L, userDTO, filmDTO, discountDTO);

        basketMapper.insertBasket(basketDTO1);
        List<BasketDTO> basketDTOS = basketMapper.selectBasketByUser(basketDTO1.getUserDTO().getId());
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(new BasketDTO(1L, userDTO, filmDTO, discountDTO)));
    }

    @Test
    public void addBasketTest(){
        discountMapper.insertDiscount(discountDTO1);
        filmMapper.insertFilm(filmDTO1);
        userMapper.insertUser(userDTO1, userRoleDTO1);

        DiscountDTO discountDTO = discountMapper.selectDiscountByCode(discountDTO1.getCode());
        FilmDTO filmDTO = filmMapper.selectFilmByImage(filmDTO1.getImage());
        UserDTO userDTO = userMapper.selectUserByLogin(userDTO1.getLogin());

        BasketDTO basketDTO1 = new BasketDTO(1L, userDTO, filmDTO, discountDTO);

        basketMapper.insertBasket(basketDTO1);
        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(new BasketDTO(1L, userDTO, filmDTO, discountDTO)));
    }

    @Test
    public void updateBasketTest(){
        discountMapper.insertDiscount(discountDTO1);
        discountMapper.insertDiscount(discountDTO2);

        filmMapper.insertFilm(filmDTO1);
        filmMapper.insertFilm(filmDTO2);

        userMapper.insertUser(userDTO1, userRoleDTO1);
        userMapper.insertUser(userDTO2, userRoleDTO2);

        List<DiscountDTO> discountDTOS = discountMapper.selectDiscounts();
        List<FilmDTO> filmDTOS = filmMapper.selectFilms();
        List<UserDTO> userDTOS = userMapper.selectUsers();

        BasketDTO basketDTO1 = new BasketDTO(userDTOS.get(0), filmDTOS.get(0), discountDTOS.get(0));
        basketMapper.insertBasket(basketDTO1);

        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        basketDTOS.get(0).setUserDTO(userDTOS.get(1));
        basketDTOS.get(0).setFilmDTO(filmDTOS.get(1));
        basketDTOS.get(0).setDiscountDTO(discountDTOS.get(1));

        basketMapper.updateBasket(basketDTOS.get(0));
        basketDTOS = basketMapper.selectBaskets();

        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(new BasketDTO(userDTOS.get(1), filmDTOS.get(1), discountDTOS.get(1))));
    }

    @Test
    public void deleteBasketTest(){
        discountMapper.insertDiscount(discountDTO1);
        filmMapper.insertFilm(filmDTO1);
        userMapper.insertUser(userDTO1, userRoleDTO1);

        DiscountDTO discountDTO = discountMapper.selectDiscountByCode(discountDTO1.getCode());
        FilmDTO filmDTO = filmMapper.selectFilmByImage(filmDTO1.getImage());
        UserDTO userDTO = userMapper.selectUserByLogin(userDTO1.getLogin());

        BasketDTO basketDTO1 = new BasketDTO(1L, userDTO, filmDTO, discountDTO);

        basketMapper.insertBasket(basketDTO1);
        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 1", basketDTOS.size() == 1);

        basketMapper.deleteBasket(basketDTOS.get(0).getId());
        basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 0", basketDTOS.size() == 0);
    }

    @Test
    public void deleteBasketByUserTest(){
        discountMapper.insertDiscount(discountDTO1);
        filmMapper.insertFilm(filmDTO1);
        userMapper.insertUser(userDTO1, userRoleDTO1);

        DiscountDTO discountDTO = discountMapper.selectDiscountByCode(discountDTO1.getCode());
        FilmDTO filmDTO = filmMapper.selectFilmByImage(filmDTO1.getImage());
        UserDTO userDTO = userMapper.selectUserByLogin(userDTO1.getLogin());

        BasketDTO basketDTO1 = new BasketDTO(1L, userDTO, filmDTO, discountDTO);

        basketMapper.insertBasket(basketDTO1);
        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 1", basketDTOS.size() == 1);

        basketMapper.deleteBasketByUser(basketDTOS.get(0).getUserDTO().getId());
        basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 0", basketDTOS.size() == 0);
    }

    @Test
    public void deleteBasketByUserFilmTest(){
        discountMapper.insertDiscount(discountDTO1);
        filmMapper.insertFilm(filmDTO1);
        userMapper.insertUser(userDTO1, userRoleDTO1);

        DiscountDTO discountDTO = discountMapper.selectDiscountByCode(discountDTO1.getCode());
        FilmDTO filmDTO = filmMapper.selectFilmByImage(filmDTO1.getImage());
        UserDTO userDTO = userMapper.selectUserByLogin(userDTO1.getLogin());

        BasketDTO basketDTO1 = new BasketDTO(1L, userDTO, filmDTO, discountDTO);

        basketMapper.insertBasket(basketDTO1);
        List<BasketDTO> basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 1", basketDTOS.size() == 1);

        basketMapper.deleteBasketByUserFilm(basketDTOS.get(0));
        basketDTOS = basketMapper.selectBaskets();
        assertTrue("count = 0", basketDTOS.size() == 0);
    }
}
