package com.mev.films.service.interfaces;

import com.mev.films.model.UserRoleDTO;

public interface ExceptionService {

    // UserRoleService
    void checkUserRoleId(Long id);
    void checkUserRoleLogin(String login);
    void checkUserRoleWithoutId(UserRoleDTO userRoleDTO);
    void checkUserRole(UserRoleDTO userRoleDTO);
}
