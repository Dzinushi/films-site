package com.mev.films.service.implement;

import com.mev.films.mappers.interfaces.UserMapper;
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

    private Logger LOG = LogManager.getLogger();

    public enum Errors{

        // UserRoles
        USER_ROLE_ERROR_NULL_POINTER_EXCEPTION,
        USER_ROLE_ERROR_WRONG_ID_PROVIDED,
        USER_ROLE_ERROR_WRONG_LOGIN_PROVIDED,
        USER_ROLE_ERROR_WRONG_ROLE,

        // Users
        USER_ERROR_NULL_POINTER_EXCEPTION
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
        LOG.debug("checkUserRoleLogin: login = {}",
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


}
