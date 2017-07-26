package com.mev.films.service.interfaces;

import com.mev.films.model.UserRoleDTO;

import java.util.List;

public interface UserRoleService {
    List<UserRoleDTO> getUserRoles();
    UserRoleDTO getUserRole(Long id);
    UserRoleDTO getUserRoleByLogin(String login);
    void addUserRole(UserRoleDTO userRoleDTO);
    void updateUserRole(UserRoleDTO userRoleDTO);
}
