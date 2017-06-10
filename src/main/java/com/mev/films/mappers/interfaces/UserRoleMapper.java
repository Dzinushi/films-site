package com.mev.films.mappers.interfaces;


import com.mev.films.model.UserRoleDTO;

import java.util.List;

public interface UserRoleMapper {
    List<UserRoleDTO> selectUserRoles();
    List<UserRoleDTO> selectUserRolesByLogin(String login);
    Long insertUserRole(UserRoleDTO userRoleDTO);
    Long updateUserRole(UserRoleDTO userRoleDTO);
    Long deleteUserRoleByLoginRole(String login, String role);
}
