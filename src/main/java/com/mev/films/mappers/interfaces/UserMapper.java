package com.mev.films.mappers.interfaces;

import com.mev.films.model.UserDTO;
import com.mev.films.model.UserRoleDTO;

import java.util.List;

public interface UserMapper {
    List<UserDTO> selectUsers();
    UserDTO selectUser(Long id);
    List<UserDTO> selectUsersIdLogin();
    List<UserDTO> selectUsersSortByLogin();
    UserDTO selectUserByLogin(String login);
    void insertUser(UserDTO userDTO, UserRoleDTO userRoleDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    void deleteUserByLogin(String login);
}