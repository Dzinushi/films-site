package com.mev.films.service.interfaces;

import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUser(String login);
    List<UserRoleDTO> getUserRoles();
    List<UserRoleDTO> getUserRole(String login);
    void addUser(UserDTO userDTO, UserRoleDTO userRoleDTO);
    void addUserRole(UserRoleDTO userRoleDTO);
    void updateUser(UserDTO userDTO);
    void updateUserRole(UserRoleDTO userRoleDTO);
    void deleteUser(Long id);
    void deleteUserByLogin(String login);
}