package com.mev.films.mappers.interfaces;

import com.mev.films.model.UserDTO;

import java.util.List;

public interface UserMapper {
    List<UserDTO> selectUsers();
    UserDTO selectUserByLogin(String login);
    Long insertUser(UserDTO userDTO);
    Long updateUser(UserDTO userDTO);
    Long deleteUserByLogin(String login);
}