package com.mev.films.service.interfaces;

import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUser(String login);
    List<UserRoleDTO> getUserRoles();
    List<UserRoleDTO> getUserRole(String login);
    Long addUser(UserDTO userDTO, UserRoleDTO userRoleDTO);
    Long addUserRole(UserRoleDTO userRoleDTO);
    Long updateUser(UserDTO userDTO, UserRoleDTO userRoleDTO);
    Long updateUserRole(UserRoleDTO userRoleDTO);
    Long deleteUser(String login);
    Long deleteUserRole(String login, String role);
}