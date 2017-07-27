package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.DiscountMapper;
import com.mev.films.mappers.interfaces.UserMapper;
import com.mev.films.model.DiscountDTO;
import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;
import com.mev.films.service.interfaces.ExceptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl extends RuntimeException implements ExceptionService {

    @Autowired private UserMapper userMapper;
    @Autowired private DiscountMapper discountMapper;

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
        DISCOUNT_ERROR_WRONG_VALUE_PROVIDED

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


}