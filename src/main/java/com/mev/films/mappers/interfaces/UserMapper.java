package com.mev.films.mappers.interfaces;

import com.mev.films.model.UserDTO;

import java.util.List;

public interface UserMapper {
    List<UserDTO> selectUsers();
    UserDTO selectUser(Long id);
    UserDTO selectUserIdLogin(Long id);
    List<UserDTO> selectUsersIdLogin();
    List<UserDTO> selectUsersSortByLogin();
    UserDTO selectUserByLogin(String login);
    void insertUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void deleteUserByLogin(String login);
}