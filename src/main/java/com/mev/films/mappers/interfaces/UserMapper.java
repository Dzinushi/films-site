package com.mev.films.mappers.interfaces;

import com.mev.films.model.UserDTO;

import java.util.List;

public interface UserMapper {
    List<UserDTO> selectUsers();
    UserDTO selectUserByName(String userName);
    Long insertUser(UserDTO userDTO);
    Long updateUser(UserDTO userDTO);
    Long deleteUserByLogin(String userName);
}