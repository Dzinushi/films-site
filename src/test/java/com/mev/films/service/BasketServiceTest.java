package com.mev.films.service;


import com.mev.films.model.*;
import com.mev.films.service.interfaces.BasketService;
import com.mev.films.service.interfaces.DiscountService;
import com.mev.films.service.interfaces.FilmService;
import com.mev.films.service.interfaces.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Objects;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class BasketServiceTest {

    @Autowired private BasketService basketService;
    @Autowired private UserService userService;
    @Autowired private FilmService filmService;
    @Autowired private DiscountService discountService;

    private static UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
    private static UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);

    private static UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private static UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 1, "imageUrl1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre1", (short) 200, 2, "imageUrl2");

    private DiscountDTO discountDTO1 = new DiscountDTO("code1");
    private DiscountDTO discountDTO2 = new DiscountDTO("code2");


    @Before
    public void setup(){
        List<BasketDTO> basketDTOS = basketService.getAllBaskets();
        for (BasketDTO basketDTO : basketDTOS){
            basketService.deleteBasket(basketDTO);
        }

        List<DiscountDTO> discountDTOS = discountService.getAllDiscounts();
        for (DiscountDTO discountDTO : discountDTOS){
            discountService.deleteDiscountByCode(discountDTO.getCode());
        }

        List<FilmDTO> filmDTOS = filmService.getAllFilms();
        for (FilmDTO filmDTO : filmDTOS){
            filmService.deleteFilmByImage(filmDTO.getImage());
        }

        List<UserDTO> userDTOS = userService.getAllUsers();
        for (UserDTO userDTO : userDTOS){
            userService.deleteUser(userDTO.getId());
        }
    }

    @Test
    public void getAllBasketsTest(){
        discountService.addDiscount(discountDTO1);
        discountService.addDiscount(discountDTO2);

        filmService.addFilm(filmDTO1);
        filmService.addFilm(filmDTO2);

        userService.addUser(userDTO1, userRoleDTO1);
        userService.addUser(userDTO2, userRoleDTO2);

        List<DiscountDTO> discountDTOS = discountService.getAllDiscounts();
        List<FilmDTO> filmDTOS = filmService.getAllFilms();
        List<UserDTO> userDTOS = userService.getAllUsers();

        BasketDTO basketDTO1 = new BasketDTO(userDTOS.get(0), filmDTOS.get(0), discountDTOS.get(0));
        BasketDTO basketDTO2 = new BasketDTO(userDTOS.get(1), filmDTOS.get(1), discountDTOS.get(1));

        basketService.addBasket(basketDTO1);
        basketService.addBasket(basketDTO2);

        List<BasketDTO> basketDTOS = basketService.getAllBaskets();
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(new BasketDTO(userDTOS.get(0), filmDTOS.get(0), discountDTOS.get(0))));
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTOS.get(1).equals(new BasketDTO(userDTOS.get(1), filmDTOS.get(1), discountDTOS.get(1))));
    }

    @Test
    public void getBasketByUserName() {

    }

    @Test
    public void addBasket(){

    }

    @Test
    public void updateBasket(){

    }

    @Test
    public void deleteBasket(){

    }

    @Test
    public void deleteBasketByUserName(){

    }
}
