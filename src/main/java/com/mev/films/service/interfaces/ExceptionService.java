package com.mev.films.service.interfaces;

import com.mev.films.model.*;

public interface ExceptionService {

    // UserRoleService
    void checkUserRoleId(Long id);
    void checkUserRoleLogin(String login);
    void checkUserRoleWithoutId(UserRoleDTO userRoleDTO);
    void checkUserRole(UserRoleDTO userRoleDTO);

    // UserService
    void checkUserId(Long id);
    void checkUserLogin(String login);
    void checkUserWithoutId(UserDTO userDTO);
    void checkUser(UserDTO userDTO);

    // DiscountService
    void checkDiscountId(Long id);
    void checkDiscountCode(String code);
    void checkDiscountWithoutId(DiscountDTO discountDTO);
    void checkDiscount(DiscountDTO discountDTO);

    // FilmService
    void checkFilmId(Long id);
    void checkFilmName(String name);
    void checkFilmImage(String image);
    void checkFilmWithoutId(FilmDTO filmDTO);
    void checkFilm(FilmDTO filmDTO);

    // UserDiscountService
    void checkUserDiscountId(Long id);
    void checkUserDiscountUserId(Long userId);
    void checkUserDiscountDiscountId(Long discountId);
    void checkUserDiscountWithoutId(UserDiscountDTO userDiscountDTO);
    void checkUserDiscount(UserDiscountDTO userDiscountDTO);

    // OrderService
    void checkOrderId(Long id);
    void checkOrderBasketId(Long userId);
    void checkOrderWithoutId(OrderDTO orderDTO);

    // BasketService
    void checkBasketId(Long id);
    void checkBasketUserId(Long userId);
//    void checkBasketWithoutId(BasketDTO basketDTO);

    // PaymentService
    void checkPaymentId(Long id);
    void checkPaymentUserId(Long userId);
    void checkPaymentFilmId(Long filmId);
    void checkPaymentWithoutId(BasketDTO basketDTO);
}
