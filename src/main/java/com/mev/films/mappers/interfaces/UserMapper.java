package com.mev.films.mappers.interfaces;

import com.mev.films.model.UserDTO;

import java.util.List;

public interface UserMapper {
    List<UserDTO> selectUsers();
    List<UserDTO> selectUsersSortByLogin();
    UserDTO selectUserByLogin(String login);
    void insertUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    void deleteUserByLogin(String login);
}