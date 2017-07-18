package com.mev.films.service.interfaces;

import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO getUser(Long id);
    List<UserDTO> getUsersSortByLogin();
    UserDTO getUserByLogin(String login);
    List<UserRoleDTO> getUserRoles();
    UserRoleDTO getUserRole(Long id);
    UserRoleDTO getUserRoleByLogin(String login);
    void addUser(UserDTO userDTO, UserRoleDTO userRoleDTO);
    void updateUser(UserDTO userDTO);
    void updateUserRole(UserRoleDTO userRoleDTO);
    void deleteUser(Long id);
    void deleteUserByLogin(String login);
}