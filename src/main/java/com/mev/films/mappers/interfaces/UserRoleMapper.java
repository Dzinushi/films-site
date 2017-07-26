package com.mev.films.mappers.interfaces;


import com.mev.films.model.UserRoleDTO;

import java.util.List;

public interface UserRoleMapper {
    List<UserRoleDTO> selectUserRoles();
    UserRoleDTO selectUserRole(Long id);
    UserRoleDTO selectUserRoleByLogin(String login);
    void insertUserRole(UserRoleDTO userRoleDTO);
    void updateUserRole(UserRoleDTO userRoleDTO);
}
