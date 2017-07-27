package com.mev.films.service.interfaces;

import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;

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
}
