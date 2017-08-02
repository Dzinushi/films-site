package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.*;
import com.mev.films.model.*;
import com.mev.films.service.interfaces.ExceptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExceptionServiceImpl extends RuntimeException implements ExceptionService {

    @Autowired private UserMapper userMapper;
    @Autowired private DiscountMapper discountMapper;
    @Autowired private FilmMapper filmMapper;
    @Autowired private OrderMapper orderMapper;
    @Autowired private BasketMapper basketMapper;
    @Autowired private UserDiscountMapper userDiscountMapper;

    private Logger LOG = LogManager.getLogger();

    public enum Errors{

        // UserRoleService
        USER_ROLE_ERROR_NULL_POINTER_EXCEPTION,
        USER_ROLE_ERROR_WRONG_ID_PROVIDED,
        USER_ROLE_ERROR_WRONG_LOGIN_PROVIDED,
        USER_ROLE_ERROR_WRONG_ROLE,

        // UserService
        USER_ERROR_NULL_POINTER_EXCEPTION,
        USER_ERROR_WRONG_ID_PROVIDED,
        USER_ERROR_WRONG_LOGIN_PROVIDED,
        USER_ERROR_WRONG_PASSWORD_PROVIDED,
        USER_ERROR_WRONG_ENABLE_PROVIDED,

        // DiscountService
        DISCOUNT_ERROR_NULL_POINTER_EXCEPTION,
        DISCOUNT_ERROR_WRONG_ID_PROVIDED,
        DISCOUNT_ERROR_WRONG_CODE_PROVIDED,
        DISCOUNT_ERROR_WRONG_VALUE_PROVIDED,

        // FilmService
        FILM_ERROR_NULL_POINTER_EXCEPTION,
        FILM_ERROR_WRONG_ID_PROVIDED,
        FILM_ERROR_WRONG_NAME_PROVIDED,
        FILM_ERROR_WRONG_GENRE_PROVIDED,
        FILM_ERROR_WRONG_DURATION_PROVIDED,
        FILM_ERROR_WRONG_PRICE_PROVIDED,
        FILM_ERROR_WRONG_IMAGE_PROVIDED,

        // UserDiscountService
        USER_DISCOUNT_ERROR_NULL_POINTER_EXCEPTION,
        USER_DISCOUNT_ERROR_USER_NULL_POINTER_EXCEPTION,
        USER_DISCOUNT_ERROR_DISCOUNT_NULL_POINTER_EXCEPTION,
        USER_DISCOUNT_ERROR_WRONG_ID_PROVIDED,
        USER_DISCOUNT_ERROR_WRONG_USER_ID_PROVIDED,
        USER_DISCOUNT_ERROR_USER_ID_NOT_FOUND,
        USER_DISCOUNT_ERROR_USER_COMPARE_FALSE,
        USER_DISCOUNT_ERROR_WRONG_DISCOUNT_ID_PROVIDED,
        USER_DISCOUNT_ERROR_DISCOUNT_ID_NOT_FOUND,
        USER_DISCOUNT_ERROR_DISCOUNT_COMPARE_FALSE,
        USER_DISCOUNT_ERROR_WRONG_USED_PROVIDED,

        // OrderService
        ORDER_ERROR_NULL_POINTER_EXCEPTION,
        ORDER_ERROR_WRONG_ID_PROVIDED,
        ORDER_ERROR_BASKET_NULL_POINTER_EXCEPTION,
        ORDER_ERROR_BASKET_WRONG_ID_PROVIDED,
        ORDER_ERROR_USER_NULL_POINTER_EXCEPTION,
        ORDER_ERROR_USER_WRONG_ID_PROVIDED,
        ORDER_ERROR_USER_ID_NOT_FOUND,
        ORDER_ERROR_USER_COMPARE_FALSE,
        ORDER_ERROR_FILM_NULL_POINTER_EXCEPTION,
        ORDER_ERROR_FILM_WRONG_ID_PROVIDED,
        ORDER_ERROR_FILM_ID_NOT_FOUND,
        ORDER_ERROR_FILM_COMPARE_FALSE,
        ORDER_ERROR_DISCOUNT_WRONG_ID_PROVIDED,
        ORDER_ERROR_DISCOUNT_ID_NOT_FOUND,
        ORDER_ERROR_DISCOUNT_COMPARE_FALSE,

        // BasketService
        BASKET_ERROR_WRONG_ID_PROVIDED,
        BASKET_ERROR_USER_WRONG_ID_PROVIDED,

        // PaymentService need to update
        PAYMENT_ERROR_WRONG_ID_PROVIDED,
        PAYMENT_ERROR_USER_NULL_POINTER_EXCEPTION,
        PAYMENT_ERROR_USER_WRONG_ID_PROVIDED,
        PAYMENT_ERROR_BASKET_NULL_POINTER_EXCEPTION,
        PAYMENT_ERROR_BASKET_WRONG_ID_PROVIDED,
        PAYMENT_ERROR_BASKET_NOT_FOUND,
        PAYMENT_ERROR_BASKET_COMPARE_FALSE,
        PAYMENT_ERROR_FILM_WRONG_ID_PROVIDED,
        PAYMENT_ERROR_ORDER_NOT_FOUND,
        PAYMENT_ERROR_DISCOUNT_NOT_FOUND,
        PAYMENT_ERROR_DISCOUNT_ALREADY_USED
    }

    public ExceptionServiceImpl(){
        super();
    }

    public ExceptionServiceImpl(Errors error) {
        super(error.toString());
    }

    public ExceptionServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public ExceptionServiceImpl(DiscountMapper discountMapper){
        this.discountMapper = discountMapper;
    }

    public ExceptionServiceImpl(FilmMapper filmMapper){
        this.filmMapper = filmMapper;
    }

    public ExceptionServiceImpl(UserMapper userMapper, DiscountMapper discountMapper){
        this.userMapper = userMapper;
        this.discountMapper = discountMapper;
    }

    public ExceptionServiceImpl(UserMapper userMapper, FilmMapper filmMapper, DiscountMapper discountMapper){
        this.userMapper = userMapper;
        this.filmMapper = filmMapper;
        this.discountMapper = discountMapper;
    }

    public ExceptionServiceImpl(OrderMapper orderMapper){
        this.orderMapper = orderMapper;
    }

    public ExceptionServiceImpl(BasketMapper basketMapper, OrderMapper orderMapper, DiscountMapper discountMapper, UserDiscountMapper userDiscountMapper){
        this.basketMapper = basketMapper;
        this.orderMapper = orderMapper;
        this.discountMapper = discountMapper;
        this.userDiscountMapper = userDiscountMapper;
    }

    @Override
    public void checkUserRoleId(Long id){

        LOG.debug("checkUserRoleId: user_role_id = {}",
                id);
        if (id == null || id < 0){
            throw new ExceptionServiceImpl(Errors.USER_ROLE_ERROR_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkUserRoleLogin(String login) {
        LOG.debug("checkUserRoleLogin: user_role_login = {}",
                login);

        if (login == null){
            throw new ExceptionServiceImpl(Errors.USER_ROLE_ERROR_WRONG_LOGIN_PROVIDED);
        }
    }

    @Override
    public void checkUserRoleWithoutId(UserRoleDTO userRoleDTO) {
        LOG.debug("checkUserRoleWithoutId: {}",
                userRoleDTO);

        if (userRoleDTO == null) {
            throw new ExceptionServiceImpl(Errors.USER_ROLE_ERROR_NULL_POINTER_EXCEPTION);
        } else if (userRoleDTO.getLogin() == null) {
            throw new ExceptionServiceImpl(Errors.USER_ROLE_ERROR_WRONG_LOGIN_PROVIDED);
        } else if (userRoleDTO.getRole() == null) {
            throw new ExceptionServiceImpl(Errors.USER_ROLE_ERROR_WRONG_ROLE);
        }

        UserDTO userDTO = userMapper.selectUserByLogin(userRoleDTO.getLogin());
        if (userDTO == null) {
            throw new ExceptionServiceImpl(Errors.USER_ERROR_NULL_POINTER_EXCEPTION);
        }
    }

    @Override
    public void checkUserRole(UserRoleDTO userRoleDTO) {
        LOG.debug("checkUserRole: {}",
                userRoleDTO);

        if (userRoleDTO == null) {
            throw new ExceptionServiceImpl(Errors.USER_ROLE_ERROR_NULL_POINTER_EXCEPTION);
        } else if (userRoleDTO.getId() == null || userRoleDTO.getId() < 0){
            throw new ExceptionServiceImpl(Errors.USER_ROLE_ERROR_WRONG_ID_PROVIDED);
        } else if (userRoleDTO.getLogin() == null) {
            throw new ExceptionServiceImpl(Errors.USER_ROLE_ERROR_WRONG_LOGIN_PROVIDED);
        } else if (userRoleDTO.getRole() == null) {
            throw new ExceptionServiceImpl(Errors.USER_ROLE_ERROR_WRONG_ROLE);
        }

        UserDTO userDTO = userMapper.selectUserByLogin(userRoleDTO.getLogin());
        if (userDTO == null) {
            throw new ExceptionServiceImpl(Errors.USER_ERROR_NULL_POINTER_EXCEPTION);
        }
    }

    @Override
    public void checkUserId(Long id) {
        LOG.debug("checkUserId: user_id = {}",
                id);

        if (id == null || id < 0){
            throw new ExceptionServiceImpl(Errors.USER_ERROR_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkUserLogin(String login) {
        LOG.debug("checkUserLogin: user_login = {}",
                login);

        if (login == null){
            throw new ExceptionServiceImpl(Errors.USER_ERROR_WRONG_LOGIN_PROVIDED);
        }
    }

    @Override
    public void checkUserWithoutId(UserDTO userDTO) {
        LOG.debug("checkUserWithoutId: {}",
                userDTO);

        if (userDTO == null) {
            throw new ExceptionServiceImpl(Errors.USER_ERROR_NULL_POINTER_EXCEPTION);
        } else if (userDTO.getLogin() == null) {
            throw new ExceptionServiceImpl(Errors.USER_ERROR_WRONG_LOGIN_PROVIDED);
        } else if (userDTO.getPassword() == null){
            throw new ExceptionServiceImpl(Errors.USER_ERROR_WRONG_PASSWORD_PROVIDED);
        } else if (userDTO.getEnabled() == null || userDTO.getEnabled() < 0){
            throw new ExceptionServiceImpl(Errors.USER_ERROR_WRONG_ENABLE_PROVIDED);
        }
    }

    @Override
    public void checkUser(UserDTO userDTO) {
        LOG.debug("checkUser: {}",
                userDTO);

        if (userDTO == null) {
            throw new ExceptionServiceImpl(Errors.USER_ERROR_NULL_POINTER_EXCEPTION);
        } else if (userDTO.getId() == null || userDTO.getId() < 0){
            throw new ExceptionServiceImpl(Errors.USER_ERROR_WRONG_ID_PROVIDED);
        } else if (userDTO.getLogin() == null) {
            throw new ExceptionServiceImpl(Errors.USER_ERROR_WRONG_LOGIN_PROVIDED);
        } else if (userDTO.getPassword() == null){
            throw new ExceptionServiceImpl(Errors.USER_ERROR_WRONG_PASSWORD_PROVIDED);
        } else if (userDTO.getEnabled() == null || userDTO.getEnabled() < 0){
            throw new ExceptionServiceImpl(Errors.USER_ERROR_WRONG_ENABLE_PROVIDED);
        }
    }

    @Override
    public void checkDiscountId(Long id) {
        LOG.debug("checkDiscountId: discount_id = {}",
                id);

        if (id == null || id < 0){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkDiscountCode(String code) {
        LOG.debug("checkDiscountCode: discount_code = {}",
                code);

        if (code == null){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED);
        }
    }

    @Override
    public void checkDiscountWithoutId(DiscountDTO discountDTO) {
        LOG.debug("checkDiscountWithoutId: {}",
                discountDTO);

        if (discountDTO == null){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_NULL_POINTER_EXCEPTION);
        } else if (discountDTO.getCode() == null){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED);
        } else if (discountDTO.getValue() == null){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED);
        } else if (discountDTO.getValue() < 0.05F || discountDTO.getValue() > 0.75F){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED);
        }
    }

    @Override
    public void checkDiscount(DiscountDTO discountDTO) {
        LOG.debug("checkDiscount: {}",
                discountDTO);

        if (discountDTO == null){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_NULL_POINTER_EXCEPTION);
        } else if (discountDTO.getId() == null || discountDTO.getId() < 0){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_WRONG_ID_PROVIDED);
        } else if (discountDTO.getCode() == null){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_WRONG_CODE_PROVIDED);
        } else if (discountDTO.getValue() == null){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED);
        } else if (discountDTO.getValue() < 0.05F || discountDTO.getValue() > 0.75F){
            throw new ExceptionServiceImpl(Errors.DISCOUNT_ERROR_WRONG_VALUE_PROVIDED);
        }
    }

    @Override
    public void checkFilmId(Long id) {
        LOG.debug("checkFilmId: film_id = {}",
                id);

        if (id == null || id < 0){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkFilmName(String name) {
        LOG.debug("checkFilmName: film_name = {}",
                name);

        if (name == null){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_NAME_PROVIDED);
        }
    }

    @Override
    public void checkFilmImage(String image) {
        LOG.debug("checkFilmImage: film_image = {}",
                image);

        if (image == null || !(
                image.endsWith(".jpg")  ||
                image.endsWith(".jpeg") ||
                image.endsWith(".png")  ||
                image.endsWith(".bmp"))) {
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED);
        }
    }

    @Override
    public void checkFilmWithoutId(FilmDTO filmDTO) {
        LOG.debug("checkFilmWithoutId: {}",
                filmDTO);

        if (filmDTO == null){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_NULL_POINTER_EXCEPTION);
        } else if (filmDTO.getName() == null){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_NAME_PROVIDED);
        } else if (filmDTO.getGenre() == null){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_GENRE_PROVIDED);
        } else if (filmDTO.getPrice() == null || filmDTO.getPrice() < 0 || filmDTO.getPrice() > 10000){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_PRICE_PROVIDED);
        } else if (filmDTO.getDuration() == null || filmDTO.getDuration() < 0 || filmDTO.getDuration() > 300) {
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_DURATION_PROVIDED);
        } else if (filmDTO.getImage() == null){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED);
        } else {
            String image = filmDTO.getImage();
            if (image == null || !(
                    image.endsWith(".jpg")  ||
                    image.endsWith(".jpeg") ||
                    image.endsWith(".png")  ||
                    image.endsWith(".bmp"))) {
                throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED);
            }
        }
    }

    @Override
    public void checkFilm(FilmDTO filmDTO) {
        LOG.debug("checkFilm: {}",
                filmDTO);

        if (filmDTO == null){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_NULL_POINTER_EXCEPTION);
        } else if (filmDTO.getId() == null || filmDTO.getId() < 0){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_ID_PROVIDED);
        } else if (filmDTO.getName() == null){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_NAME_PROVIDED);
        } else if (filmDTO.getGenre() == null){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_GENRE_PROVIDED);
        } else if (filmDTO.getPrice() == null || filmDTO.getPrice() < 0 || filmDTO.getPrice() > 10000){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_PRICE_PROVIDED);
        } else if (filmDTO.getDuration() == null || filmDTO.getDuration() < 0 || filmDTO.getDuration() > 300) {
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_DURATION_PROVIDED);
        } else if (filmDTO.getImage() == null){
            throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED);
        } else {
            String image = filmDTO.getImage();
            if (image == null || !(
                    image.endsWith(".jpg")  ||
                            image.endsWith(".jpeg") ||
                            image.endsWith(".png")  ||
                            image.endsWith(".bmp"))) {
                throw new ExceptionServiceImpl(Errors.FILM_ERROR_WRONG_IMAGE_PROVIDED);
            }
        }
    }

    @Override
    public void checkUserDiscountId(Long id) {
        LOG.debug("checkUserDiscountId: user_discount_id = {}",
                id);

        if (id == null || id < 0){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkUserDiscountUserId(Long userId) {
        LOG.debug("checkUserDiscountUserId: user_discount_user_id",
                userId);

        if (userId == null || userId < 0){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_WRONG_USER_ID_PROVIDED);
        }
    }

    @Override
    public void checkUserDiscountDiscountId(Long discountId) {
        LOG.debug("checkUserDiscountDiscountId: user_discount_discount_id = {}",
                discountId);

        if (discountId == null || discountId < 0){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_WRONG_DISCOUNT_ID_PROVIDED);
        }
    }

    @Override
    public void checkUserDiscountWithoutId(UserDiscountDTO userDiscountDTO) {
        LOG.debug("checkUserDiscountWithoutId: {}",
                userDiscountDTO);

        if (userDiscountDTO == null){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_NULL_POINTER_EXCEPTION);
        }

        UserDTO userDTO = userDiscountDTO.getUserDTO();
        if (userDTO == null) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_USER_NULL_POINTER_EXCEPTION);
        }
        Long userId = userDTO.getId();
        if (userId == null || userId  < 0) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_WRONG_USER_ID_PROVIDED);
        }
        UserDTO userDTOSelect = userMapper.selectUserIdLogin(userDTO.getId());
        if (userDTOSelect == null) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_USER_ID_NOT_FOUND);
        } else if (!userDTO.equals(userDTOSelect)){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_USER_COMPARE_FALSE);
        }

        DiscountDTO discountDTO = userDiscountDTO.getDiscountDTO();
        if (discountDTO == null) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_DISCOUNT_NULL_POINTER_EXCEPTION);
        }
        Long discountId = discountDTO.getId();
        if (discountId == null || discountId < 0) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_WRONG_DISCOUNT_ID_PROVIDED);
        }
        DiscountDTO discountDTOSelect = discountMapper.selectDiscount(discountDTO.getId());
        if (discountDTOSelect == null) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_DISCOUNT_ID_NOT_FOUND);
        } else if (!discountDTO.equals(discountDTOSelect)){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_DISCOUNT_COMPARE_FALSE);
        }

        if (userDiscountDTO.isUsed() == null){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_WRONG_USED_PROVIDED);
        }
    }

    @Override
    public void checkUserDiscount(UserDiscountDTO userDiscountDTO) {
        LOG.debug("checkUserDiscount: {}",
                userDiscountDTO);

        if (userDiscountDTO == null){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_NULL_POINTER_EXCEPTION);
        }
        Long id = userDiscountDTO.getId();
        if (id == null || id < 0){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_WRONG_ID_PROVIDED);
        }
        UserDTO userDTO = userDiscountDTO.getUserDTO();
        if (userDTO == null) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_USER_NULL_POINTER_EXCEPTION);
        }
        Long userId = userDTO.getId();
        if (userId == null || userId  < 0) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_WRONG_USER_ID_PROVIDED);
        }
        UserDTO userDTOSelect = userMapper.selectUserIdLogin(userId);
        if (userDTOSelect == null) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_USER_ID_NOT_FOUND);
        } else if (!userDTO.equals(userDTOSelect)){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_USER_COMPARE_FALSE);
        }

        DiscountDTO discountDTO = userDiscountDTO.getDiscountDTO();
        if (discountDTO == null) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_DISCOUNT_NULL_POINTER_EXCEPTION);
        }

        Long discountId = discountDTO.getId();
        if (discountId == null || discountId < 0) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_WRONG_DISCOUNT_ID_PROVIDED);
        }
        DiscountDTO discountDTOSelect = discountMapper.selectDiscount(discountId);
        if (discountDTOSelect== null) {
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_DISCOUNT_ID_NOT_FOUND);
        } else if (!discountDTO.equals(discountDTOSelect)){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_DISCOUNT_COMPARE_FALSE);
        }

        if (userDiscountDTO.isUsed() == null){
            throw new ExceptionServiceImpl(Errors.USER_DISCOUNT_ERROR_WRONG_USED_PROVIDED);
        }
    }

    @Override
    public void checkOrderId(Long id) {
        LOG.debug("checkOrderId: order_id = {}",
                id);

        if (id == null || id < 0){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkOrderBasketId(Long basketId) {
        LOG.debug("checkOrderBasketId: order_basket_id = {}",
                basketId);

        if (basketId == null || basketId < 0){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_BASKET_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkOrderWithoutId(OrderDTO orderDTO) {
        LOG.debug("checkOrderWithoutId: {}",
                orderDTO);

        if (orderDTO == null){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_NULL_POINTER_EXCEPTION);
        }

        BasketDTO basketDTO = orderDTO.getBasketDTO();
        if (basketDTO == null){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_BASKET_NULL_POINTER_EXCEPTION);
        }
        UserDTO userDTO = basketDTO.getUserDTO();
        if (userDTO == null){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_USER_NULL_POINTER_EXCEPTION);
        }
        Long userId = userDTO.getId();
        if (userId == null || userId < 0){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_USER_WRONG_ID_PROVIDED);
        }
        UserDTO userDTOSelect = userMapper.selectUserIdLogin(userId);
        if (userDTOSelect == null){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_USER_ID_NOT_FOUND);
        } else if (!userDTO.equals(userDTOSelect)){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_USER_COMPARE_FALSE);
        }

        FilmDTO filmDTO = orderDTO.getFilmDTO();
        if (filmDTO == null){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_FILM_NULL_POINTER_EXCEPTION);
        }
        Long filmId = filmDTO.getId();
        if (filmId == null || filmId < 0){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_FILM_WRONG_ID_PROVIDED);
        }
        FilmDTO filmDTOSelect = filmMapper.selectFilm(filmId);
        if (filmDTOSelect == null){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_FILM_ID_NOT_FOUND);
        } else if (!filmDTO.equals(filmDTOSelect)){
            throw new ExceptionServiceImpl(Errors.ORDER_ERROR_FILM_COMPARE_FALSE);
        }

        DiscountDTO discountDTO = orderDTO.getDiscountDTO();
        if (discountDTO != null){
            Long discountId = discountDTO.getId();
            if (discountId == null || discountId < 0){
                throw new ExceptionServiceImpl(Errors.ORDER_ERROR_DISCOUNT_WRONG_ID_PROVIDED);
            }
            DiscountDTO discountDTOSelect = discountMapper.selectDiscount(discountId);
            if (discountDTOSelect == null){
                throw new ExceptionServiceImpl(Errors.ORDER_ERROR_DISCOUNT_ID_NOT_FOUND);
            } else if (!discountDTO.equals(discountDTOSelect)){
                throw new ExceptionServiceImpl(Errors.ORDER_ERROR_DISCOUNT_COMPARE_FALSE);
            }
        }
    }

    @Override
    public void checkBasketId(Long id) {
        LOG.debug("checkBasketId: basket_id = {}",
                id);

        if (id == null || id < 0){
            throw new ExceptionServiceImpl(Errors.BASKET_ERROR_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkBasketUserId(Long userId) {
        LOG.debug("checkBasketUserId: basket_user_id = {}",
                userId);

        if (userId == null || userId < 0){
            throw new ExceptionServiceImpl(Errors.BASKET_ERROR_USER_WRONG_ID_PROVIDED);
        }
    }

//    @Override
//    public void checkBasketWithoutId(BasketDTO basketDTO) {
//        LOG.debug("checkBasketWithout: {}",
//                basketDTO);
//
//        if (basketDTO == null){
//            throw new ExceptionServiceImpl(Errors.BASKET_ERROR_NULL_POINTER_EXCEPTION);
//        }
//        UserDTO userDTO = basketDTO.getUserDTO();
//        if (userDTO == null){
//            throw new ExceptionServiceImpl(Errors.BASKET_ERROR_USER_NULL_POINTER_EXCEPTION);
//        }
//        Long userId = userDTO.getId();
//        if (userId == null || userId < 0){
//            throw new ExceptionServiceImpl(Errors.BASKET_ERROR_USER_WRONG_ID_PROVIDED);
//        }
//        List<OrderDTO> orderDTOS  = orderMapper.selectOrderByUser(userId);
//        if (orderDTOS == null){
//            throw new ExceptionServiceImpl(Errors.BASKET_ERROR_USER_ORDERS_NOT_FOUND_EXCEPTION);
//        }
//    }

    @Override
    public void checkPaymentId(Long id) {
        LOG.debug("checkPaymentId: id = {}",
                id);

        if (id == null || id < 0){
            throw new ExceptionServiceImpl(Errors.PAYMENT_ERROR_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkPaymentUserId(Long userId) {
        LOG.debug("checkPaymentUserId: payment_user_id: {}",
                userId);

        if (userId == null || userId < 0){
            throw new ExceptionServiceImpl(Errors.PAYMENT_ERROR_USER_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkPaymentFilmId(Long filmId) {
        LOG.debug("checkPaymentFilmId: payment_film_id = {}",
                filmId);

        if (filmId == null || filmId < 0){
            throw new ExceptionServiceImpl(Errors.PAYMENT_ERROR_FILM_WRONG_ID_PROVIDED);
        }
    }

    @Override
    public void checkPaymentWithoutId(BasketDTO basketDTO) {
        LOG.debug("checkPaymentWithoutId: {}",
                basketDTO);

        if (basketDTO == null){
            throw new ExceptionServiceImpl(Errors.PAYMENT_ERROR_BASKET_NULL_POINTER_EXCEPTION);
        }
        Long basketId = basketDTO.getId();
        if (basketId == null || basketId < 0){
            throw new ExceptionServiceImpl(Errors.PAYMENT_ERROR_BASKET_WRONG_ID_PROVIDED);
        }
        BasketDTO basketDTOSelect = basketMapper.selectBasket(basketDTO.getId());
        if (basketDTOSelect == null){
            throw new ExceptionServiceImpl(Errors.PAYMENT_ERROR_BASKET_NOT_FOUND);
        } else if (!basketDTO.equals(basketDTOSelect)){
            throw new ExceptionServiceImpl(Errors.PAYMENT_ERROR_BASKET_COMPARE_FALSE);
        }
        List<OrderDTO> orderDTOS = orderMapper.selectOrdersByBasketIsMark(basketDTO.getId());
        if (orderDTOS == null || orderDTOS.size() == 0){
            throw new ExceptionServiceImpl(Errors.PAYMENT_ERROR_ORDER_NOT_FOUND);
        }

        // Check all mark field in orderDTOs
        for (OrderDTO orderDTO : orderDTOS) {

            // Check is discount validate
            DiscountDTO discountDTO = discountMapper.selectDiscount(orderDTO.getDiscountDTO().getId());
            if (discountDTO == null){
                throw new ExceptionServiceImpl(Errors.PAYMENT_ERROR_DISCOUNT_NOT_FOUND);
            } else {

                // Check is discount free
                UserDiscountDTO userDiscountDTO = userDiscountMapper.selectUserDiscountByDiscount(orderDTO.getDiscountDTO().getId());
                if (userDiscountDTO != null && userDiscountDTO.isUsed()) {
                    throw new ExceptionServiceImpl(Errors.PAYMENT_ERROR_DISCOUNT_ALREADY_USED);
                }
            }
        }
    }
}