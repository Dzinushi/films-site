package com.mev.films.service;


import com.mev.films.mappers.interfaces.*;
import com.mev.films.model.*;
import com.mev.films.service.implement.BasketServiceImpl;
import com.mev.films.service.implement.DiscountServiceImpl;
import com.mev.films.service.implement.FilmServiceImpl;
import com.mev.films.service.implement.UserServiceImpl;
import com.mev.films.service.interfaces.BasketService;
import com.mev.films.service.interfaces.DiscountService;
import com.mev.films.service.interfaces.FilmService;
import com.mev.films.service.interfaces.UserService;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertTrue;
import static org.easymock.EasyMock.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test-dispatcher.xml")
public class BasketServiceTest {

    @Autowired private BasketService basketService;
    @Autowired private UserService userService;
    @Autowired private FilmService filmService;
    @Autowired private DiscountService discountService;

    @Autowired private BasketMapper basketMapperMock;
    @Autowired private UserMapper userMapperMock;
    @Autowired private UserRoleMapper userRoleMapperMock;
    @Autowired private FilmMapper filmMapperMock;
    @Autowired private DiscountMapper discountMapperMock;
    @Autowired private UserDiscountMapper userDiscountMapperMock;

    private static UserDTO userDTO1 = new UserDTO("user1", "user1", (short) 1);
    private static UserDTO userDTO2 = new UserDTO("user2", "user2", (short) 1);

    private static UserRoleDTO userRoleDTO1 = new UserRoleDTO("user1", "ROLE_USER");
    private static UserRoleDTO userRoleDTO2 = new UserRoleDTO("user2", "ROLE_ADMIN");

    private FilmDTO filmDTO1 = new FilmDTO("film1", "genre1", (short) 100, 100, "imageUrl1");
    private FilmDTO filmDTO2 = new FilmDTO("film2", "genre2", (short) 200, 200, "imageUrl2");

    private DiscountDTO discountDTO1 = new DiscountDTO("code1", 0.15F);
    private DiscountDTO discountDTO2 = new DiscountDTO("code2", 0.2F);

    private BasketDTO basketDTO1;
    private BasketDTO basketDTO2;

    @Before
    public void setup(){
        basketMapperMock = createNiceMock(BasketMapper.class);
        userMapperMock = createNiceMock(UserMapper.class);
        userRoleMapperMock = createNiceMock(UserRoleMapper.class);
        filmMapperMock = createNiceMock(FilmMapper.class);
        discountMapperMock = createNiceMock(DiscountMapper.class);
        userDiscountMapperMock = createNiceMock(UserDiscountMapper.class);

        basketService = new BasketServiceImpl(basketMapperMock, userDiscountMapperMock);
        userService = new UserServiceImpl(userMapperMock, userRoleMapperMock);
        filmService = new FilmServiceImpl(filmMapperMock);
        discountService = new DiscountServiceImpl(discountMapperMock);


        expect(discountService.getDiscounts()).andStubAnswer(new IAnswer<List<DiscountDTO>>() {
            @Override
            public List<DiscountDTO> answer() throws Throwable {
                List<DiscountDTO> discountDTOS = new ArrayList<>();
                discountDTOS.add(discountDTO1);
                discountDTOS.add(discountDTO2);
                return discountDTOS;
            }
        });

        expect(filmService.getFilms()).andStubAnswer(new IAnswer<List<FilmDTO>>() {
            @Override
            public List<FilmDTO> answer() throws Throwable {
                List<FilmDTO> filmDTOS = new ArrayList<>();
                filmDTOS.add(filmDTO1);
                filmDTOS.add(filmDTO2);
                return filmDTOS;
            }
        });

        expect(userService.getUsers()).andStubAnswer(new IAnswer<List<UserDTO>>() {
            @Override
            public List<UserDTO> answer() throws Throwable {
                List<UserDTO> userDTOS = new ArrayList<>();
                userDTOS.add(userDTO1);
                userDTOS.add(userDTO2);
                return userDTOS;
            }
        });

        replay(discountMapperMock);
        replay(filmMapperMock);
        replay(userMapperMock);
        replay(userRoleMapperMock);
        replay(userDiscountMapperMock);

        discountService.addDiscount(discountDTO1);
        discountService.addDiscount(discountDTO2);

        filmService.addFilm(filmDTO1);
        filmService.addFilm(filmDTO2);

        userService.addUser(userDTO1, userRoleDTO1);
        userService.addUser(userDTO2, userRoleDTO2);

        List<DiscountDTO> discountDTOS = discountService.getDiscounts();
        List<FilmDTO> filmDTOS = filmService.getFilms();
        List<UserDTO> userDTOS = userService.getUsers();

        basketDTO1 = new BasketDTO(1L, userDTOS.get(0), filmDTOS.get(0), discountDTOS.get(0));
        basketDTO2 = new BasketDTO(2L, userDTOS.get(1), filmDTOS.get(1), discountDTOS.get(1));

        BasketServiceImpl.priceByDiscount(basketDTO1);
        BasketServiceImpl.priceByDiscount(basketDTO2);
    }

    @Test
    public void getBasketsTest(){

        expect(basketService.getBaskets()).andStubAnswer(new IAnswer<List<BasketDTO>>() {
            @Override
            public List<BasketDTO> answer() throws Throwable {
                List<BasketDTO> basketDTOS = new ArrayList<>();
                basketDTOS.add(new BasketDTO(userDTO1, filmDTO1, discountDTO1));
                basketDTOS.add(new BasketDTO(userDTO2, filmDTO2, discountDTO2));
                return basketDTOS;
            }
        });

        replay(basketMapperMock);

        basketService.addBasket(basketDTO1);
        basketService.addBasket(basketDTO2);

        List<BasketDTO> basketDTOS = basketService.getBaskets();

        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(basketDTO1));
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTOS.get(1).equals(basketDTO2));

        verify(basketMapperMock);
        verify(discountMapperMock);
        verify(filmMapperMock);
        verify(userMapperMock);
        verify(userRoleMapperMock);
    }

    @Test
    public void getBasketTest(){

        expect(basketService.getBasket(basketDTO2.getId())).andStubAnswer(new IAnswer<BasketDTO>() {
            @Override
            public BasketDTO answer() throws Throwable {
                return basketDTO2;
            }
        });

        replay(basketMapperMock);

        basketService.addBasket(basketDTO1);
        BasketDTO basketDTO = basketService.getBasket(basketDTO2.getId());
        assertTrue("basketDTO2 = " + basketDTO2.toString(),
                basketDTO.equals(basketDTO2));

        verify(basketMapperMock);
        verify(discountMapperMock);
        verify(filmMapperMock);
        verify(userMapperMock);
        verify(userRoleMapperMock);
    }

    @Test
    public void getBasketByUserTest() {

        expect(basketService.getBasketByUser(userDTO1.getId())).andStubAnswer(new IAnswer<List<BasketDTO>>() {
            @Override
            public List<BasketDTO> answer() throws Throwable {
                List<BasketDTO> basketDTOS = new ArrayList<>();
                basketDTOS.add(new BasketDTO(userDTO1, filmDTO1, discountDTO1));
                return basketDTOS;
            }
        });

        replay(basketMapperMock);

        basketService.addBasket(basketDTO1);
        List<BasketDTO> basketDTOS = basketService.getBasketByUser(basketDTO1.getUserDTO().getId());
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(basketDTO1));

        verify(basketMapperMock);
        verify(discountMapperMock);
        verify(filmMapperMock);
        verify(userMapperMock);
        verify(userRoleMapperMock);
    }

    @Test
    public void addBasketTest(){

        expect(basketService.getBaskets()).andStubAnswer(new IAnswer<List<BasketDTO>>() {
            @Override
            public List<BasketDTO> answer() throws Throwable {
                List<BasketDTO> basketDTOS = new ArrayList<>();
                basketDTOS.add(new BasketDTO(userDTO1, filmDTO1, discountDTO1));
                return basketDTOS;
            }
        });

        replay(basketMapperMock);

        basketService.addBasket(basketDTO1);
        List<BasketDTO> basketDTOS = basketService.getBaskets();
        assertTrue("basketDTO1 = " + basketDTO1.toString(),
                basketDTOS.get(0).equals(basketDTO1));

        verify(basketMapperMock);
        verify(discountMapperMock);
        verify(filmMapperMock);
        verify(userMapperMock);
        verify(userRoleMapperMock);
    }

    @Test
    public void updateBasketTest(){

        expect(basketService.getBaskets()).andStubAnswer(new IAnswer<List<BasketDTO>>() {
            @Override
            public List<BasketDTO> answer() throws Throwable {
                List<BasketDTO> basketDTOS = new ArrayList<>();
                basketDTOS.add(new BasketDTO(userDTO1, filmDTO1, discountDTO1));
                return basketDTOS;
            }
        });

        expect(basketService.getBasketByUser(userDTO2.getId())).andStubAnswer(new IAnswer<List<BasketDTO>>() {
            @Override
            public List<BasketDTO> answer() throws Throwable {
                List<BasketDTO> basketDTOS = new ArrayList<>();
                BasketDTO basketDTO = new BasketDTO(userDTO2, filmDTO2, discountDTO2);
                basketDTOS.add(basketDTO);
                return basketDTOS;
            }
        });

        replay(basketMapperMock);

        basketService.addBasket(basketDTO1);

        List<BasketDTO> getBasketDTOS = basketService.getBaskets();
        getBasketDTOS.get(0).setUserDTO(basketDTO2.getUserDTO());
        getBasketDTOS.get(0).setFilmDTO(basketDTO2.getFilmDTO());
        getBasketDTOS.get(0).setDiscountDTO(basketDTO2.getDiscountDTO());

        basketService.updateBasket(getBasketDTOS.get(0));
        List<BasketDTO> basketDTOS = basketService.getBasketByUser(basketDTO2.getUserDTO().getId());

        assertTrue("basketDTO1 = " + basketDTO2.toString(),
                basketDTOS.get(0).equals(basketDTO2));

        verify(basketMapperMock);
        verify(discountMapperMock);
        verify(filmMapperMock);
        verify(userMapperMock);
        verify(userRoleMapperMock);
    }

    @Test
    public void deleteBasketTest(){

        expect(basketService.getBaskets()).andStubAnswer(new IAnswer<List<BasketDTO>>() {
            @Override
            public List<BasketDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        replay(basketMapperMock);

        basketService.addBasket(basketDTO1);

        basketService.deleteBasket(basketDTO1.getId());
        List<BasketDTO> basketDTOS = basketService.getBaskets();
        assertTrue("count = 0", basketDTOS.size() == 0);

        verify(basketMapperMock);
        verify(discountMapperMock);
        verify(filmMapperMock);
        verify(userMapperMock);
        verify(userRoleMapperMock);
    }

    @Test
    public void deleteBasketByUserTest(){

        expect(basketService.getBaskets()).andStubAnswer(new IAnswer<List<BasketDTO>>() {
            @Override
            public List<BasketDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        replay(basketMapperMock);

        basketService.addBasket(basketDTO1);

        basketService.deleteBasketByUser(basketDTO1.getUserDTO().getId());
        List<BasketDTO> basketDTOS = basketService.getBaskets();
        assertTrue("count = 0", basketDTOS.size() == 0);

        verify(basketMapperMock);
        verify(discountMapperMock);
        verify(filmMapperMock);
        verify(userMapperMock);
        verify(userRoleMapperMock);
    }

    @Test
    public void deleteBasketByUserFilmTest(){

        expect(basketService.getBaskets()).andStubAnswer(new IAnswer<List<BasketDTO>>() {
            @Override
            public List<BasketDTO> answer() throws Throwable {
                return new ArrayList<>();
            }
        });

        replay(basketMapperMock);

        basketService.addBasket(basketDTO1);

        basketService.deleteBasketByUserFilm(basketDTO1);
        List<BasketDTO> basketDTOS = basketService.getBaskets();
        assertTrue("count = 0", basketDTOS.size() == 0);

        verify(basketMapperMock);
        verify(discountMapperMock);
        verify(filmMapperMock);
        verify(userMapperMock);
        verify(userRoleMapperMock);
    }
}
