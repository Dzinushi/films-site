package com.mev.films.service.interfaces;

import com.mev.films.model.*;

public interface ExceptionService {

    // UserRoleService
    void checkUserRoleId(Long id);
    void checkUserRolesNumberFrom(Long number, Long from);
    void checkUserRoleLogin(String login);
    void checkUserRole(UserRoleDTO userRoleDTO);

    // UserService
    void checkUserId(Long id);
    void checkUserNumberFrom(Long number, Long from);
    void checkUserLogin(String login);
    void checkUserInfoWithoutId(UserInfoDTO userInfoDTO);
    void checkUser(UserDTO userDTO);

    // DiscountService
    void checkDiscountId(Long id);
    void checkDiscountNumberFrom(Long number, Long from);
    void checkDiscountCode(String code);
    void checkDiscountWithoutId(DiscountDTO discountDTO);
    void checkDiscount(DiscountDTO discountDTO);

    // FilmService
    void checkFilmId(Long id);
    void checkFilmNumberFrom(Long number, Long from);
    void checkFilmName(String name);
    void checkFilmImage(String image);
    void checkFilmWithoutId(FilmDTO filmDTO);
    void checkFilm(FilmDTO filmDTO);

    // UserDiscountService
    void checkUserDiscountId(Long id);
    void checkUserDiscountNumberFrom(Long number, Long from);
    void checkUserDiscountUserId(Long userId);
    void checkUserDiscountDiscountId(Long discountId);
    void checkUserDiscountWithoutId(UserDiscountDTO userDiscountDTO);
    void checkUserDiscount(UserDiscountDTO userDiscountDTO);

    // OrderService
    void checkOrderId(Long id);
    void checkOrderNumberFrom(Long number, Long from);
    void checkOrderBasketId(Long userId);
    void checkOrderWithoutId(OrderDTO orderDTO);

    // BasketService
    void checkBasketId(Long id);
    void checkBasketNumberFrom(Long number, Long from);
    void checkBasketUserId(Long userId);
//    void checkBasketWithoutId(BasketDTO basketDTO);

    // PaymentService
    void checkPaymentId(Long id);
    void checkPaymentNumberFrom(Long number, Long from);
    void checkPaymentUserId(Long userId);
    void checkPaymentFilmId(Long filmId);
    void checkPaymentWithoutId(BasketDTO basketDTO);
}
